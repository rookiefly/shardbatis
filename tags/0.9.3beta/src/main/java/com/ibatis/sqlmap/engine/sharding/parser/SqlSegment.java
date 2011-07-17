/**
 * 
 */
package com.ibatis.sqlmap.engine.sharding.parser;

import java.io.Serializable;

/**
 * @author sean.he
 *
 */
public class SqlSegment implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6303862631640777588L;
	
	private String segment;
	private boolean isTableName;
	public String getSegment() {
		return segment;
	}
	public void setSegment(String segment) {
		this.segment = segment;
	}
	public boolean isTableName() {
		return isTableName;
	}
	public void setTableName(boolean isTableName) {
		this.isTableName = isTableName;
	}
	
	public String toString(){
		return "{segment:"+segment+" ,isTableName:"+isTableName+"}";
	}
}
