/**
 * 
 */
package com.google.code.shardbatis.strategy;

/**
 * 分表策略接口
 * @author sean.he
 *
 */
public interface ShardStrategy {
	/**
	 * 得到实际表名
	 * @param baseTableName 逻辑表名,一般是没有前缀或者是后缀的表名
	 * @param params mybatis执行某个statement时使用的参数
	 * @param mapperId mybatis配置的statement id
	 * @return
	 */
	String getTargetTableName(String baseTableName,Object params,String mapperId);
}
