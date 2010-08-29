/**
 * 
 */
package com.ibatis.sqlmap.engine.sharding.converter;

import net.sf.jsqlparser.statement.Statement;

import com.ibatis.sqlmap.engine.sharding.ShardingFactorGroup;

/**
 * @author sean.he
 *
 */
public interface SqlConverter {
	String convert(Statement statement,ShardingFactorGroup... groups);
}
