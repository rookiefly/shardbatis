/**
 * 
 */
package com.ibatis.sqlmap.engine.sharding.parser;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author sean.he
 *
 */
public class WrappedSql implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4930002495383513154L;

	private List<SqlSegment> segments=new ArrayList<SqlSegment>();
	
	private List<SqlSegment> tables=new ArrayList<SqlSegment>();
	
	public void addSegment(SqlSegment segment){
		if(segment==null){
			throw new IllegalArgumentException("Argument segment must not null.");
		}
		this.segments.add(segment);
		if(segment.isTableName()){
			tables.add(segment);
		}
	}
	
	public List<SqlSegment> getTables(){
		return this.tables;
	}
	
	public String toString(){
		StringBuilder builder=new StringBuilder();
		for(SqlSegment seg:segments){
			builder.append(seg.getSegment());
		}
		return builder.toString();
	}

	public List<SqlSegment> getSegments() {
		return segments;
	}
}
