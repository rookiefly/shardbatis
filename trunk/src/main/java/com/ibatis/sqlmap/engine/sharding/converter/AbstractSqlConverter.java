/**
 * 
 */
package com.ibatis.sqlmap.engine.sharding.converter;

import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.util.deparser.StatementDeParser;

import com.ibatis.sqlmap.engine.sharding.ShardingFactorGroup;
import com.ibatis.sqlmap.engine.sharding.ShardingStrategy;

/**
 * @author sean.he
 *
 */
public abstract class AbstractSqlConverter implements SqlConverter {

	/* (non-Javadoc)
	 * @see com.ibatis.sqlmap.engine.sharding.converter.SqlConverter#convert(net.sf.jsqlparser.statement.Statement, com.ibatis.sqlmap.engine.sharding.ShardingFactorGroup[])
	 */
	public String convert(Statement statement, ShardingFactorGroup... groups) {
		return doDeParse(doConvert(statement,groups));
	}
	
	protected String doDeParse(Statement statement){
		StatementDeParser deParser=new StatementDeParser(new StringBuffer());
		statement.accept(deParser);
		return deParser.getBuffer().toString();
	}
	
	protected String convertTableName(String tableName, ShardingFactorGroup... groups){
		if (groups == null || groups.length == 0||tableName==null) {
			return tableName;
		}
		for(ShardingFactorGroup each:groups){
			if(tableName.equalsIgnoreCase(each.getTableName())){
				ShardingStrategy strategy=each.getShardingStrategy();
				String newName = strategy.getTargetTableName(tableName, each.getParam());
				if (newName != null && newName.length() > 0) {
					return newName;
				}
			}
		}
		return tableName;
	}
	
	protected abstract Statement doConvert(Statement statement, ShardingFactorGroup... groups);
	
	
}
