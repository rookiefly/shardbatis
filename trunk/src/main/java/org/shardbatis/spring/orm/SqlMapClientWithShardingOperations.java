/**
 * 
 */
package org.shardbatis.spring.orm;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.SqlMapClientOperations;

import com.ibatis.sqlmap.client.event.RowHandler;
import com.ibatis.sqlmap.engine.sharding.ShardingFactorGroup;

/**
 * @author sean.he
 * 
 */
public interface SqlMapClientWithShardingOperations extends
		SqlMapClientOperations {

	Object queryForObjectWithSharding(String statementName, ShardingFactorGroup... groups)
			throws DataAccessException;

	Object queryForObjectWithSharding(String statementName, Object parameterObject,
			ShardingFactorGroup... groups) throws DataAccessException;

	Object queryForObjectWithSharding(String statementName, Object parameterObject,
			Object resultObject, ShardingFactorGroup... groups)
			throws DataAccessException;

	List queryForListWithSharding(String statementName, ShardingFactorGroup... groups)
			throws DataAccessException;

	List queryForListWithSharding(String statementName, Object parameterObject,
			ShardingFactorGroup... groups) throws DataAccessException;

	List queryForListWithSharding(String statementName, int skipResults, int maxResults,
			ShardingFactorGroup... groups) throws DataAccessException;

	List queryForListWithSharding(String statementName, Object parameterObject,
			int skipResults, int maxResults, ShardingFactorGroup... groups)
			throws DataAccessException;

	void queryWithRowHandlerWithSharding(String statementName, RowHandler rowHandler,
			ShardingFactorGroup... groups) throws DataAccessException;

	void queryWithRowHandlerWithSharding(String statementName, Object parameterObject,
			RowHandler rowHandler, ShardingFactorGroup... groups)
			throws DataAccessException;

	Map queryForMapWithSharding(String statementName, Object parameterObject,
			String keyProperty, ShardingFactorGroup... groups)
			throws DataAccessException;

	Map queryForMapWithSharding(String statementName, Object parameterObject,
			String keyProperty, String valueProperty,
			ShardingFactorGroup... groups) throws DataAccessException;

	Object insertWithSharding(String statementName, ShardingFactorGroup... groups)
			throws DataAccessException;

	Object insertWithSharding(String statementName, Object parameterObject,
			ShardingFactorGroup... groups) throws DataAccessException;

	int updateWithSharding(String statementName, ShardingFactorGroup... groups)
			throws DataAccessException;

	int updateWithSharding(String statementName, Object parameterObject,
			ShardingFactorGroup... groups) throws DataAccessException;

	void updateWithSharding(String statementName, Object parameterObject,
			int requiredRowsAffected, ShardingFactorGroup... groups)
			throws DataAccessException;

	int deleteWithSharding(String statementName, ShardingFactorGroup... groups)
			throws DataAccessException;

	int deleteWithSharding(String statementName, Object parameterObject,
			ShardingFactorGroup... groups) throws DataAccessException;

	void deleteWithSharding(String statementName, Object parameterObject,
			int requiredRowsAffected, ShardingFactorGroup... groups)
			throws DataAccessException;
}
