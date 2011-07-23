/**
 * 
 */
package com.google.code.shardbatis.converter;

import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.delete.Delete;

/**
 * @author sean.he
 * 
 */
public class DeleteSqlConverter extends AbstractSqlConverter {

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
		if (!(statement instanceof Delete)) {
			throw new IllegalArgumentException(
					"The argument statement must is instance of Delete.");
		}
		Delete delete = (Delete) statement;

		String name = delete.getTable().getName();
		delete.getTable()
				.setName(this.convertTableName(name, params, mapperId));
		return delete;
	}

}
