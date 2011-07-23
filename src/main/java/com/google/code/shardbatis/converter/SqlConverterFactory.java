/**
 * 
 */
package com.google.code.shardbatis.converter;

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

import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;

import com.google.code.shardbatis.ShardException;

/**
 * 管理各种CRUD语句的Converter
 * @author sean.he
 * 
 */
public class SqlConverterFactory {
	private static final Log log = LogFactory.getLog(SqlConverterFactory.class);

	private static SqlConverterFactory factory;
	static {
		factory = new SqlConverterFactory();
	}

	public static SqlConverterFactory getInstance() {
		return factory;
	}

	private Map<String, SqlConverter> converterMap;
	private CCJSqlParserManager pm;

	private SqlConverterFactory() {
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
	
	/**
	 * 修改sql语句
	 * @param sql
	 * @param params
	 * @param mapperId
	 * @return 修改后的sql
	 * @throws ShardException 解析sql失败会抛出ShardException
	 */
	public String convert(String sql, Object params, String mapperId)
			throws ShardException {
		Statement statement = null;
		try {
			statement = pm.parse(new StringReader(sql));
		} catch (JSQLParserException e) {
			log.error(e.getMessage(), e);
			throw new ShardException(e);
		}

		SqlConverter converter = this.converterMap.get(statement.getClass()
				.getName());

		if (converter != null) {
			return converter.convert(statement, params, mapperId);
		}
		return sql;
	}
}
