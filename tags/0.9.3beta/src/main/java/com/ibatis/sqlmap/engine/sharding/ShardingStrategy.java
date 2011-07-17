/**
 * 
 */
package com.ibatis.sqlmap.engine.sharding;

/**
 * @author sean.he
 *
 */
public interface ShardingStrategy {
	/**
	 * 计算得到新的表名
	 * @param baseTableName 逻辑表名
	 * @param params 为sharding逻辑提供必要参数
	 * @return 
	 */
	public String getTargetTableName(String baseTableName,Object params);
}
