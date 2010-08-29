/**
 * 
 */
package com.ibatis.sqlmap.engine.sharding;

/**
 * @author sean.he
 *
 */
public interface ShardingStrategy {
	public String getTargetTableName(String baseTableName,Object params);
}
