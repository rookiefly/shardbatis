/**
 * 
 */
package com.ibatis.sqlmap.engine.sharding;

import java.sql.SQLException;

/**
 * @author sean.he
 *
 */
public interface ShardingCallback<T> {
	T doWithSharding() throws SQLException;
}
