/**
 * 
 */
package org.shardbatis.spring.orm;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

/**
 * @author sean.he
 * 
 */
public abstract class SqlMapClientWithShardingDaoSupport extends
		SqlMapClientDaoSupport {
	public SqlMapClientWithShardingTemplate getSqlMapClientWithShardingTemplate() {
		return (SqlMapClientWithShardingTemplate) getSqlMapClientTemplate();
	}
}
