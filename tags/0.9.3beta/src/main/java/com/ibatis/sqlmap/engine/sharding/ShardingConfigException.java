/**
 * 
 */
package com.ibatis.sqlmap.engine.sharding;

/**
 * @author sean.he
 *
 */
public class ShardingConfigException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5048657028475547289L;
	
	public ShardingConfigException(){
		super();
	}
	
	public ShardingConfigException(Throwable t){
		super(t);
	}
	
	public ShardingConfigException(String msg,Throwable t){
		super(msg,t);
	}
	
	public ShardingConfigException(String msg){
		super(msg);
	}

}
