/**
 * 
 */
package com.ibatis.sqlmap.engine.sharding;

/**
 * @author sean.he
 * 
 */
public class ShardingException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6176007233504122699L;

	public ShardingException() {
		super();
	}

	public ShardingException(String msg) {
		super(msg);
	}

	public ShardingException(String msg, Throwable t) {
		super(msg, t);
	}

	public ShardingException(Throwable t) {
		super(t);
	}

}
