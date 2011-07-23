/**
 * 
 */
package com.google.code.shardbatis.converter;

import net.sf.jsqlparser.statement.Statement;

/**
 * sql转换修改接口
 * @author sean.he
 *
 */
public interface SqlConverter {
	/**
	 * 对sql进行修改
	 * @param statement
	 * @param params mybatis执行某个statement时使用的参数
	 * @param mapperId mybatis配置的statement id
	 * @return
	 */
	String convert(Statement statement,Object params,String mapperId);
}
