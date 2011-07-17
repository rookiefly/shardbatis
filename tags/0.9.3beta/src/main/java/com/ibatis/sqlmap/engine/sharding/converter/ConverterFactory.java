/**
 * 
 */
package com.ibatis.sqlmap.engine.sharding.converter;

import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.update.Update;

import com.ibatis.common.logging.Log;
import com.ibatis.common.logging.LogFactory;
import com.ibatis.sqlmap.engine.sharding.ShardingException;
import com.ibatis.sqlmap.engine.sharding.ShardingFactorGroup;

/**
 * @author sean.he
 * 
 */
public class ConverterFactory {
	private static final Log log = LogFactory.getLog(ConverterFactory.class);

	private static ConverterFactory factory;
	static {
		factory = new ConverterFactory();
	}

	public static ConverterFactory getInstance() {
		return factory;
	}

	private Map<String, SqlConverter> converterMap;
	private CCJSqlParserManager pm;

	private ConverterFactory() {
		converterMap = new HashMap<String, SqlConverter>();
		pm = new CCJSqlParserManager();
		register();
	}

	private void register() {
		converterMap.put(Select.class.getName(), new SelectSqlConverter());
		converterMap.put(Insert.class.getName(), new InsertSqlConverter());
		converterMap.put(Update.class.getName(), new UpdateSqlConverter());
		converterMap.put(Delete.class.getName(), new DeleteSqlConverter());
	}

	public String convert(String sql, ShardingFactorGroup... groups)
			throws ShardingException {
		Statement statement = null;
		try {
			statement = pm.parse(new StringReader(sql));
		} catch (JSQLParserException e) {
			log.error(e.getMessage(), e);
			throw new ShardingException(e);
		}

		SqlConverter converter = this.converterMap.get(statement.getClass()
				.getName());

		if (converter != null) {
			return converter.convert(statement, groups);
		}
		return sql;
	}

}
