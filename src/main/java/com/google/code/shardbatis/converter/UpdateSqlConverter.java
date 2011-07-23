/**
 * 
 */
package com.google.code.shardbatis.converter;

import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.update.Update;

/**
 * @author sean.he
 * 
 */
public class UpdateSqlConverter extends AbstractSqlConverter {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.google.code.shardbatis.converter.AbstractSqlConverter#doConvert(net
	 * .sf.jsqlparser.statement.Statement, java.lang.Object, java.lang.String)
	 */
	@Override
	protected Statement doConvert(Statement statement, Object params,
			String mapperId) {
		if (!(statement instanceof Update)) {
			throw new IllegalArgumentException(
					"The argument statement must is instance of Update.");
		}
		Update update = (Update) statement;
		String name = update.getTable().getName();
		update.getTable()
				.setName(this.convertTableName(name, params, mapperId));
		return update;
	}

}
