/**
 * 
 */
package org.shardbatis.spring.orm;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.JdbcUpdateAffectedIncorrectNumberOfRowsException;
import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.ibatis.sqlmap.client.SqlMapExecutor;
import com.ibatis.sqlmap.client.SqlMapShardingExt;
import com.ibatis.sqlmap.client.event.RowHandler;
import com.ibatis.sqlmap.engine.sharding.ShardingFactorGroup;

/**
 * @author sean.he
 * 
 */
public class SqlMapClientWithShardingTemplate extends SqlMapClientTemplate
		implements SqlMapClientWithShardingOperations {

	public int deleteWithSharding(String statementName,
			ShardingFactorGroup... groups) throws DataAccessException {
		return deleteWithSharding(statementName, null, groups);
	}

	public int deleteWithSharding(final String statementName,
			final Object parameterObject, final ShardingFactorGroup... groups)
			throws DataAccessException {
		return execute(new SqlMapClientCallback<Integer>() {
			public Integer doInSqlMapClient(SqlMapExecutor executor)
					throws SQLException {
				SqlMapShardingExt shard = (SqlMapShardingExt) executor;
				return shard.deleteWithSharding(statementName, parameterObject,
						groups);
			}
		});
	}

	public void deleteWithSharding(String statementName,
			Object parameterObject, int requiredRowsAffected,
			ShardingFactorGroup... groups) throws DataAccessException {
		int actualRowsAffected = this.deleteWithSharding(statementName,
				parameterObject, groups);
		if (actualRowsAffected != requiredRowsAffected) {
			throw new JdbcUpdateAffectedIncorrectNumberOfRowsException(
					statementName, requiredRowsAffected, actualRowsAffected);
		}

	}

	public Object insertWithSharding(String statementName,
			ShardingFactorGroup... groups) throws DataAccessException {
		return insertWithSharding(statementName, null, groups);
	}

	public Object insertWithSharding(final String statementName,
			final Object parameterObject, final ShardingFactorGroup... groups)
			throws DataAccessException {
		return execute(new SqlMapClientCallback<Object>() {
			public Object doInSqlMapClient(SqlMapExecutor executor)
					throws SQLException {
				SqlMapShardingExt shard = (SqlMapShardingExt) executor;
				return shard.insertWithSharding(statementName, parameterObject,
						groups);
			}
		});
	}

	public List queryForListWithSharding(String statementName,
			ShardingFactorGroup... groups) throws DataAccessException {
		return queryForListWithSharding(statementName, null, groups);
	}

	public List queryForListWithSharding(final String statementName,
			final Object parameterObject, final ShardingFactorGroup... groups)
			throws DataAccessException {
		return execute(new SqlMapClientCallback<List>() {
			public List doInSqlMapClient(SqlMapExecutor executor)
					throws SQLException {
				SqlMapShardingExt shard = (SqlMapShardingExt) executor;
				return shard.queryForListWithSharding(statementName,
						parameterObject, groups);
			}
		});
	}

	public List queryForListWithSharding(String statementName, int skipResults,
			int maxResults, ShardingFactorGroup... groups)
			throws DataAccessException {
		return queryForListWithSharding(statementName, null, skipResults,
				maxResults, groups);
	}

	public List queryForListWithSharding(final String statementName,
			final Object parameterObject, final int skipResults,
			final int maxResults, final ShardingFactorGroup... groups)
			throws DataAccessException {
		return execute(new SqlMapClientCallback<List>() {
			public List doInSqlMapClient(SqlMapExecutor executor)
					throws SQLException {
				SqlMapShardingExt shard = (SqlMapShardingExt) executor;
				return shard.queryForListWithSharding(statementName,
						parameterObject, skipResults, maxResults, groups);
			}
		});
	}

	public Map queryForMapWithSharding(String statementName,
			Object parameterObject, String keyProperty,
			ShardingFactorGroup... groups) throws DataAccessException {
		return queryForMapWithSharding(statementName, parameterObject,
				keyProperty, null, groups);
	}

	public Map queryForMapWithSharding(final String statementName,
			final Object parameterObject, final String keyProperty,
			final String valueProperty, final ShardingFactorGroup... groups)
			throws DataAccessException {
		return execute(new SqlMapClientCallback<Map>() {
			public Map doInSqlMapClient(SqlMapExecutor executor)
					throws SQLException {
				SqlMapShardingExt shard = (SqlMapShardingExt) executor;
				return shard.queryForMapWithSharding(statementName,
						parameterObject, keyProperty, valueProperty, groups);
			}
		});
	}

	public Object queryForObjectWithSharding(String statementName,
			ShardingFactorGroup... groups) throws DataAccessException {
		return queryForObjectWithSharding(statementName, null, groups);
	}

	public Object queryForObjectWithSharding(String statementName,
			Object parameterObject, ShardingFactorGroup... groups)
			throws DataAccessException {
		return queryForObjectWithSharding(statementName, parameterObject, null,
				groups);
	}

	public Object queryForObjectWithSharding(final String statementName,
			final Object parameterObject, final Object resultObject,
			final ShardingFactorGroup... groups) throws DataAccessException {
		return execute(new SqlMapClientCallback<Object>() {
			public Object doInSqlMapClient(SqlMapExecutor executor)
					throws SQLException {
				SqlMapShardingExt shard = (SqlMapShardingExt) executor;
				return shard.queryForObjectWithSharding(statementName,
						parameterObject, resultObject, groups);
			}
		});
	}

	public void queryWithRowHandlerWithSharding(String statementName,
			RowHandler rowHandler, ShardingFactorGroup... groups)
			throws DataAccessException {
		queryWithRowHandlerWithSharding(statementName, null, rowHandler, groups);

	}

	public void queryWithRowHandlerWithSharding(final String statementName,
			final Object parameterObject, final RowHandler rowHandler,
			final ShardingFactorGroup... groups) throws DataAccessException {
		execute(new SqlMapClientCallback<Object>() {
			public Object doInSqlMapClient(SqlMapExecutor executor)
					throws SQLException {
				SqlMapShardingExt shard = (SqlMapShardingExt) executor;
				shard.queryWithRowHandlerWithSharding(statementName,
						parameterObject, rowHandler, groups);
				return null;
			}
		});

	}

	public int updateWithSharding(String statementName,
			ShardingFactorGroup... groups) throws DataAccessException {
		return updateWithSharding(statementName, null, groups);
	}

	public int updateWithSharding(final String statementName,
			final Object parameterObject, final ShardingFactorGroup... groups)
			throws DataAccessException {
		return execute(new SqlMapClientCallback<Integer>() {
			public Integer doInSqlMapClient(SqlMapExecutor executor)
					throws SQLException {
				SqlMapShardingExt shard = (SqlMapShardingExt) executor;
				return shard.updateWithSharding(statementName, parameterObject,
						groups);
			}
		});
	}

	public void updateWithSharding(String statementName,
			Object parameterObject, int requiredRowsAffected,
			ShardingFactorGroup... groups) throws DataAccessException {
		int actualRowsAffected = updateWithSharding(statementName,
				parameterObject, groups);
		if (actualRowsAffected != requiredRowsAffected) {
			throw new JdbcUpdateAffectedIncorrectNumberOfRowsException(
					statementName, requiredRowsAffected, actualRowsAffected);
		}
	}

}
