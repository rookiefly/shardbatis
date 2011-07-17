/**
 * 
 */
package com.ibatis.sqlmap.engine.sharding.converter;

import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.update.Update;

import com.ibatis.sqlmap.engine.sharding.ShardingFactorGroup;

/**
 * @author sean.he
 *
 */
public class UpdateSqlConverter extends AbstractSqlConverter {

	@Override
	protected Statement doConvert(Statement statement,
			ShardingFactorGroup... groups) {
		if (!(statement instanceof Update)) {
			throw new IllegalArgumentException("The argument statement must is instance of Update.");
		}
		Update update=(Update)statement;
		String name=update.getTable().getName();
		update.getTable().setName(this.convertTableName(name, groups));
		return update;
	}

}
