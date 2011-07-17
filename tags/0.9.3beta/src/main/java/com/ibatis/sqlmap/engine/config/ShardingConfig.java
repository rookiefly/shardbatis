/**
 * 
 */
package com.ibatis.sqlmap.engine.config;

import com.ibatis.sqlmap.engine.sharding.ShardingStrategy;

/**
 * @author sean.he
 *
 */
public class ShardingConfig {
	private String tableName;
	private ShardingStrategy strategy;
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public ShardingStrategy getStrategy() {
		return strategy;
	}
	public void setStrategy(ShardingStrategy strategy) {
		this.strategy = strategy;
	}
	
}
