/**
 * 
 */
package com.google.code.shardbatis.converter;

import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.util.deparser.StatementDeParser;

import com.google.code.shardbatis.builder.ShardConfigHolder;
import com.google.code.shardbatis.strategy.ShardStrategy;

/**
 * @author sean.he
 * 
 */
public abstract class AbstractSqlConverter implements SqlConverter {

	public String convert(Statement statement, Object params, String mapperId) {
		return doDeParse(doConvert(statement, params, mapperId));
	}
	
	/**
	 * 将Statement反解析为sql
	 * @param statement
	 * @return
	 */
	protected String doDeParse(Statement statement) {
		StatementDeParser deParser = new StatementDeParser(new StringBuffer());
		statement.accept(deParser);
		return deParser.getBuffer().toString();
	}

	/**
	 * 从ShardConfigFactory中查找ShardStrategy并对表名进行修改<br>
	 * 如果没有相应的ShardStrategy则对表名不做修改
	 * 
	 * @param tableName
	 * @param params
	 * @param mapperId
	 * @return
	 */
	protected String convertTableName(String tableName, Object params,
			String mapperId) {
		ShardConfigHolder configFactory = ShardConfigHolder.getInstance();
		ShardStrategy strategy = configFactory.getStrategy(tableName);
		if (strategy == null) {
			return tableName;
		}
		return strategy.getTargetTableName(tableName, params, mapperId);
	}
	
	/**
	 * 修改statement代表的sql语句
	 * @param statement
	 * @param params
	 * @param mapperId
	 * @return
	 */
	protected abstract Statement doConvert(Statement statement, Object params,
			String mapperId);
}
