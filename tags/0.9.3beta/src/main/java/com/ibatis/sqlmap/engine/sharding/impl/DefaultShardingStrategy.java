package com.ibatis.sqlmap.engine.sharding.impl;

import com.ibatis.sqlmap.engine.sharding.ShardingStrategy;

/**
 * 
 * @author sean.he
 * 
 */
public class DefaultShardingStrategy implements ShardingStrategy {

	public String getTargetTableName(String baseTableName, Object params) {
		int idx;
		if (params instanceof Integer) {
			idx = (Integer) params % 2;
		} else if (params instanceof Long) {
			Long l = (Long) params % 2L;
			idx = l.intValue();
		} else {
			return baseTableName;
		}
		return new StringBuilder(baseTableName).append("_").append(idx)
				.toString();
	}

}
