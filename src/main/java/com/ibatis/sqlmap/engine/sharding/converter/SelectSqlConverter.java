/**
 * 
 */
package com.ibatis.sqlmap.engine.sharding.converter;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import net.sf.jsqlparser.expression.AllComparisonExpression;
import net.sf.jsqlparser.expression.AnyComparisonExpression;
import net.sf.jsqlparser.expression.BinaryExpression;
import net.sf.jsqlparser.expression.CaseExpression;
import net.sf.jsqlparser.expression.DateValue;
import net.sf.jsqlparser.expression.DoubleValue;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.ExpressionVisitor;
import net.sf.jsqlparser.expression.Function;
import net.sf.jsqlparser.expression.InverseExpression;
import net.sf.jsqlparser.expression.JdbcParameter;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.NullValue;
import net.sf.jsqlparser.expression.Parenthesis;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.expression.TimeValue;
import net.sf.jsqlparser.expression.TimestampValue;
import net.sf.jsqlparser.expression.WhenClause;
import net.sf.jsqlparser.expression.operators.arithmetic.Addition;
import net.sf.jsqlparser.expression.operators.arithmetic.Division;
import net.sf.jsqlparser.expression.operators.arithmetic.Multiplication;
import net.sf.jsqlparser.expression.operators.arithmetic.Subtraction;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.conditional.OrExpression;
import net.sf.jsqlparser.expression.operators.relational.Between;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.expression.operators.relational.ExistsExpression;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.expression.operators.relational.GreaterThan;
import net.sf.jsqlparser.expression.operators.relational.GreaterThanEquals;
import net.sf.jsqlparser.expression.operators.relational.InExpression;
import net.sf.jsqlparser.expression.operators.relational.IsNullExpression;
import net.sf.jsqlparser.expression.operators.relational.ItemsListVisitor;
import net.sf.jsqlparser.expression.operators.relational.LikeExpression;
import net.sf.jsqlparser.expression.operators.relational.MinorThan;
import net.sf.jsqlparser.expression.operators.relational.MinorThanEquals;
import net.sf.jsqlparser.expression.operators.relational.NotEqualsTo;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.FromItemVisitor;
import net.sf.jsqlparser.statement.select.Join;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectVisitor;
import net.sf.jsqlparser.statement.select.SubJoin;
import net.sf.jsqlparser.statement.select.SubSelect;
import net.sf.jsqlparser.statement.select.Union;

import com.ibatis.sqlmap.engine.sharding.ShardingFactorGroup;
import com.ibatis.sqlmap.engine.sharding.ShardingStrategy;

/**
 * @author sean.he
 * 
 */
public class SelectSqlConverter implements SqlConverter {

	public String convert(Statement statement, ShardingFactorGroup... groups) {
		return doConvert(statement,groups).toString();
	}
	
	protected Statement doConvert(Statement statement,
			ShardingFactorGroup... groups) {
		if (!(statement instanceof Select)) {
			throw new IllegalArgumentException("The argument statement must is instance of Select.");
		}
		TableNameModifier modifier=new TableNameModifier(groups);
		((Select) statement).getSelectBody().accept(modifier);
		return statement;
	}

	private class TableNameModifier implements SelectVisitor, FromItemVisitor,
			ExpressionVisitor, ItemsListVisitor {
		private Map<String, ShardingFactorGroup> groupMap;
		
		private TableNameModifier(ShardingFactorGroup... groups) {
			groupMap = new HashMap<String, ShardingFactorGroup>();
			if (groups == null || groups.length == 0) {
				return;
			}
			for(ShardingFactorGroup each:groups){
				groupMap.put(each.getTableName(), each);
			}
		}

		@SuppressWarnings("unchecked")
		public void visit(PlainSelect plainSelect) {
			plainSelect.getFromItem().accept(this);

			if (plainSelect.getJoins() != null) {
				for (Iterator joinsIt = plainSelect.getJoins().iterator(); joinsIt
						.hasNext();) {
					Join join = (Join) joinsIt.next();
					join.getRightItem().accept(this);
				}
			}
			if (plainSelect.getWhere() != null)
				plainSelect.getWhere().accept(this);

		}

		@SuppressWarnings("unchecked")
		public void visit(Union union) {
			for (Iterator iter = union.getPlainSelects().iterator(); iter
					.hasNext();) {
				PlainSelect plainSelect = (PlainSelect) iter.next();
				visit(plainSelect);
			}
		}

		public void visit(Table tableName) {
			String table = tableName.getName();
			// convert table name
			ShardingFactorGroup group = this.groupMap.get(table.toLowerCase());
			if (group != null) {
				ShardingStrategy strategy = group.getShardingStrategy();
				if (strategy != null) {
					String newName = strategy.getTargetTableName(table, group
							.getParam());
					if (newName != null && newName.length() > 0) {
						tableName.setName(newName);
					}
				}
			}
		}

		public void visit(SubSelect subSelect) {
			subSelect.getSelectBody().accept(this);
		}

		public void visit(Addition addition) {
			visitBinaryExpression(addition);
		}

		public void visit(AndExpression andExpression) {
			visitBinaryExpression(andExpression);
		}

		public void visit(Between between) {
			between.getLeftExpression().accept(this);
			between.getBetweenExpressionStart().accept(this);
			between.getBetweenExpressionEnd().accept(this);
		}

		public void visit(Column tableColumn) {
		}

		public void visit(Division division) {
			visitBinaryExpression(division);
		}

		public void visit(DoubleValue doubleValue) {
		}

		public void visit(EqualsTo equalsTo) {
			visitBinaryExpression(equalsTo);
		}

		public void visit(Function function) {
		}

		public void visit(GreaterThan greaterThan) {
			visitBinaryExpression(greaterThan);
		}

		public void visit(GreaterThanEquals greaterThanEquals) {
			visitBinaryExpression(greaterThanEquals);
		}

		public void visit(InExpression inExpression) {
			inExpression.getLeftExpression().accept(this);
			inExpression.getItemsList().accept(this);
		}

		public void visit(InverseExpression inverseExpression) {
			inverseExpression.getExpression().accept(this);
		}

		public void visit(IsNullExpression isNullExpression) {
		}

		public void visit(JdbcParameter jdbcParameter) {
		}

		public void visit(LikeExpression likeExpression) {
			visitBinaryExpression(likeExpression);
		}

		public void visit(ExistsExpression existsExpression) {
			existsExpression.getRightExpression().accept(this);
		}

		public void visit(LongValue longValue) {
		}

		public void visit(MinorThan minorThan) {
			visitBinaryExpression(minorThan);
		}

		public void visit(MinorThanEquals minorThanEquals) {
			visitBinaryExpression(minorThanEquals);
		}

		public void visit(Multiplication multiplication) {
			visitBinaryExpression(multiplication);
		}

		public void visit(NotEqualsTo notEqualsTo) {
			visitBinaryExpression(notEqualsTo);
		}

		public void visit(NullValue nullValue) {
		}

		public void visit(OrExpression orExpression) {
			visitBinaryExpression(orExpression);
		}

		public void visit(Parenthesis parenthesis) {
			parenthesis.getExpression().accept(this);
		}

		public void visit(StringValue stringValue) {
		}

		public void visit(Subtraction subtraction) {
			visitBinaryExpression(subtraction);
		}

		public void visitBinaryExpression(BinaryExpression binaryExpression) {
			binaryExpression.getLeftExpression().accept(this);
			binaryExpression.getRightExpression().accept(this);
		}

		@SuppressWarnings("unchecked")
		public void visit(ExpressionList expressionList) {
			for (Iterator iter = expressionList.getExpressions().iterator(); iter
					.hasNext();) {
				Expression expression = (Expression) iter.next();
				expression.accept(this);
			}

		}

		public void visit(DateValue dateValue) {
		}

		public void visit(TimestampValue timestampValue) {
		}

		public void visit(TimeValue timeValue) {
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * net.sf.jsqlparser.expression.ExpressionVisitor#visit(net.sf.jsqlparser
		 * .expression.CaseExpression)
		 */
		public void visit(CaseExpression caseExpression) {
			// TODO Auto-generated method stub

		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * net.sf.jsqlparser.expression.ExpressionVisitor#visit(net.sf.jsqlparser
		 * .expression.WhenClause)
		 */
		public void visit(WhenClause whenClause) {
			// TODO Auto-generated method stub

		}

		public void visit(AllComparisonExpression allComparisonExpression) {
			allComparisonExpression.GetSubSelect().getSelectBody().accept(this);
		}

		public void visit(AnyComparisonExpression anyComparisonExpression) {
			anyComparisonExpression.GetSubSelect().getSelectBody().accept(this);
		}

		public void visit(SubJoin subjoin) {
			subjoin.getLeft().accept(this);
			subjoin.getJoin().getRightItem().accept(this);
		}
	}


}
