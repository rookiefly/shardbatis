/**
 * 
 */
package com.ibatis.sqlmap.engine.sharding.converter;

import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.delete.Delete;

import com.ibatis.sqlmap.engine.sharding.ShardingFactorGroup;

/**
 * @author sean.he
 *
 */
public class DeleteSqlConverter extends AbstractSqlConverter {

	/* (non-Javadoc)
	 * @see com.ibatis.sqlmap.engine.sharding.converter.AbstractSqlConverter#doConvert(net.sf.jsqlparser.statement.Statement, com.ibatis.sqlmap.engine.sharding.ShardingFactorGroup[])
	 */
	@Override
	protected Statement doConvert(Statement statement,
			ShardingFactorGroup... groups) {
		if (!(statement instanceof Delete)) {
			throw new IllegalArgumentException("The argument statement must is instance of Delete.");
		}
		Delete delete = (Delete)statement;

		String name=delete.getTable().getName();
		delete.getTable().setName(this.convertTableName(name, groups));
		return delete;
	}

}
