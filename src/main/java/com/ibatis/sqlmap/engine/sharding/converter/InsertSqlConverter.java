/**
 * 
 */
package com.ibatis.sqlmap.engine.sharding.converter;

import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.insert.Insert;

import com.ibatis.sqlmap.engine.sharding.ShardingFactorGroup;

/**
 * @author sean.he
 *
 */
public class InsertSqlConverter extends AbstractSqlConverter {

	@Override
	protected Statement doConvert(Statement statement,
			ShardingFactorGroup... groups) {
		if (!(statement instanceof Insert)) {
			throw new IllegalArgumentException("The argument statement must is instance of Insert.");
		}
		Insert insert=(Insert)statement;

		String name=insert.getTable().getName();
		insert.getTable().setName(this.convertTableName(name, groups));
		return insert;
	}

}
