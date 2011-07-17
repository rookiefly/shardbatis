/**
 * 
 */
package com.ibatis.sqlmap.client;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.ibatis.sqlmap.client.event.RowHandler;
import com.ibatis.sqlmap.engine.sharding.ShardingFactorGroup;

/**
 * @author sean.he
 * 
 */
public interface SqlMapShardingExt extends SqlMapExecutor{
	/**
	 * 
	 * @param id
	 * @param parameterObject
	 * @param groups
	 * @return
	 * @throws SQLException
	 */
	Object insertWithSharding(String id, Object parameterObject,
			ShardingFactorGroup... groups) throws SQLException;

	/**
	 * 
	 * @param id
	 * @param groups
	 * @return
	 * @throws SQLException
	 */
	Object insertWithSharding(String id, ShardingFactorGroup... groups) throws SQLException;

	/**
	 * 
	 * @param id
	 * @param parameterObject
	 * @param groups
	 * @return
	 * @throws SQLException
	 */
	int updateWithSharding(String id, Object parameterObject, ShardingFactorGroup... groups)
			throws SQLException;

	/**
	 * 
	 * @param id
	 * @param groups
	 * @return
	 * @throws SQLException
	 */
	int updateWithSharding(String id, ShardingFactorGroup... groups) throws SQLException;

	/**
	 * 
	 * @param id
	 * @param parameterObject
	 * @param groups
	 * @return
	 * @throws SQLException
	 */
	int deleteWithSharding(String id, Object parameterObject, ShardingFactorGroup... groups)
			throws SQLException;

	/**
	 * 
	 * @param id
	 * @param groups
	 * @return
	 * @throws SQLException
	 */
	int deleteWithSharding(String id, ShardingFactorGroup... groups) throws SQLException;

	/**
	 * 
	 * @param id
	 * @param parameterObject
	 * @param groups
	 * @return
	 * @throws SQLException
	 */
	Object queryForObjectWithSharding(String id, Object parameterObject,
			ShardingFactorGroup... groups) throws SQLException;

	/**
	 * 
	 * @param id
	 * @param groups
	 * @return
	 * @throws SQLException
	 */
	Object queryForObjectWithSharding(String id, ShardingFactorGroup... groups)
			throws SQLException;

	/**
	 * 
	 * @param id
	 * @param parameterObject
	 * @param resultObject
	 * @param groups
	 * @return
	 * @throws SQLException
	 */
	Object queryForObjectWithSharding(String id, Object parameterObject,
			Object resultObject, ShardingFactorGroup... groups)
			throws SQLException;

	/**
	 * 
	 * @param id
	 * @param parameterObject
	 * @param groups
	 * @return
	 * @throws SQLException
	 */
	List queryForListWithSharding(String id, Object parameterObject,
			ShardingFactorGroup... groups) throws SQLException;

	/**
	 * 
	 * @param id
	 * @param groups
	 * @return
	 * @throws SQLException
	 */
	List queryForListWithSharding(String id, ShardingFactorGroup... groups)
			throws SQLException;

	/**
	 * 
	 * @param id
	 * @param parameterObject
	 * @param skip
	 * @param max
	 * @param groups
	 * @return
	 * @throws SQLException
	 */
	List queryForListWithSharding(String id, Object parameterObject, int skip, int max,
			ShardingFactorGroup... groups) throws SQLException;

	/**
	 * 
	 * @param id
	 * @param skip
	 * @param max
	 * @param groups
	 * @return
	 * @throws SQLException
	 */
	List queryForListWithSharding(String id, int skip, int max,
			ShardingFactorGroup... groups) throws SQLException;

	/**
	 * 
	 * @param id
	 * @param parameterObject
	 * @param rowHandler
	 * @param groups
	 * @throws SQLException
	 */
	void queryWithRowHandlerWithSharding(String id, Object parameterObject,
			RowHandler rowHandler, ShardingFactorGroup... groups)
			throws SQLException;

	/**
	 * 
	 * @param id
	 * @param rowHandler
	 * @param groups
	 * @throws SQLException
	 */
	void queryWithRowHandlerWithSharding(String id, RowHandler rowHandler,
			ShardingFactorGroup... groups) throws SQLException;

	/**
	 * 
	 * @param id
	 * @param parameterObject
	 * @param keyProp
	 * @param groups
	 * @return
	 * @throws SQLException
	 */
	Map queryForMapWithSharding(String id, Object parameterObject, String keyProp,
			ShardingFactorGroup... groups) throws SQLException;

	/**
	 * 
	 * @param id
	 * @param parameterObject
	 * @param keyProp
	 * @param valueProp
	 * @param groups
	 * @return
	 * @throws SQLException
	 */
	Map queryForMapWithSharding(String id, Object parameterObject, String keyProp,
			String valueProp, ShardingFactorGroup... groups)
			throws SQLException;

}
