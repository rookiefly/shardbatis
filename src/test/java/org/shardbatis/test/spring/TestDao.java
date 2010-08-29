/**
 * 
 */
package org.shardbatis.test.spring;

import org.shardbatis.spring.orm.SqlMapClientWithShardingDaoSupport;
import org.shardbatis.spring.orm.SqlMapClientWithShardingTemplate;

import com.ibatis.sqlmap.engine.sharding.ShardingFactorGroup;

/**
 * @author sean.he
 *
 */
public class TestDao extends SqlMapClientWithShardingDaoSupport {
	
	public <T> T get(Class<T> entityClass, Object param,
			String statementName,ShardingFactorGroup... groups) {
		SqlMapClientWithShardingTemplate template=this.getSqlMapClientWithShardingTemplate();
		return (T)template.queryForObjectWithSharding(statementName, param, groups);
	}
	
	public void remove(Object parameterObject,String statementName,ShardingFactorGroup... groups) {
		SqlMapClientWithShardingTemplate template=getSqlMapClientWithShardingTemplate();
		template.deleteWithSharding(statementName, parameterObject, groups);
	}
	
	public int insert(Object parameterObject,String statementName,ShardingFactorGroup... groups) {
		SqlMapClientWithShardingTemplate template=getSqlMapClientWithShardingTemplate();
		return (Integer) template.insertWithSharding(statementName, parameterObject, groups);
	}
}
