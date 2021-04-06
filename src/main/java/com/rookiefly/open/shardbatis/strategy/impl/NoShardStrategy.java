package com.rookiefly.open.shardbatis.strategy.impl;

import com.rookiefly.open.shardbatis.strategy.ShardStrategy;

/**
 * 不进行分表的策略，供测试用
 *
 * @author sean.he
 */
public class NoShardStrategy implements ShardStrategy {

    /* (non-Javadoc)
     * @see com.google.code.shardbatis.strategy.ShardStrategy#getTargetTableName(java.lang.String, java.lang.Object, java.lang.String)
     */
    @Override
    public String getTargetTableName(String baseTableName, Object params,
                                     String mapperId) {
        return baseTableName;
    }

}
