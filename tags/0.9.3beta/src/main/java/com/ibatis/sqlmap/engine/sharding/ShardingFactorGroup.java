/**
 * 
 */
package com.ibatis.sqlmap.engine.sharding;


/**
 * @author sean.he
 *
 */
public class ShardingFactorGroup {
	private String tableName;
	private Object param;
	private ShardingStrategy shardingStrategy;
	
	public ShardingFactorGroup(){
		
	}
	
	public ShardingFactorGroup(String tableName,Object param,ShardingStrategy shardingStrategy){
		this.tableName=tableName;
		this.param=param;
		this.shardingStrategy=shardingStrategy;
	}
	/**
	 * 
	 * @return lower case table name.
	 */
	public String getTableName() {
		if(this.tableName!=null){
			return this.tableName.toLowerCase();
		}
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public Object getParam() {
		return param;
	}

	public void setParam(Object param) {
		this.param = param;
	}

	public ShardingStrategy getShardingStrategy() {
		return shardingStrategy;
	}

	public void setShardingStrategy(ShardingStrategy shardingStrategy) {
		this.shardingStrategy = shardingStrategy;
	}
	
}
