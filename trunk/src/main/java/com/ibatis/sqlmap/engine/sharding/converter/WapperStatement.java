package com.ibatis.sqlmap.engine.sharding.converter;

import java.util.ArrayList;
import java.util.List;

import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.StatementVisitor;

public class WapperStatement implements Statement {
	
	private Statement realStatement;
	
	private List<Table> tables=new ArrayList<Table>();
	
	public WapperStatement(){
		
	}
	public WapperStatement(Statement realStatement,List<Table> tables){
		this.realStatement=realStatement;
		this.tables=tables;
	}

	public void accept(StatementVisitor statementVisitor) {
		realStatement.accept(statementVisitor);
	}
	
	public void addTable(Table table){
		tables.add(table);
	}
	public Statement getRealStatement() {
		return realStatement;
	}
	public void setRealStatement(Statement realStatement) {
		this.realStatement = realStatement;
	}
	public List<Table> getTables() {
		return tables;
	}
	public void setTables(List<Table> tables) {
		this.tables = tables;
	}


}
