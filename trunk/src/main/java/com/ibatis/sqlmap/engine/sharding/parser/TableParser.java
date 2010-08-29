/**
 * 
 */
package com.ibatis.sqlmap.engine.sharding.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author sean.he
 *
 */
public class TableParser {
	
	private static final char DOUBLE_QUOTE='\"';
	private static final char SINGLE_QUOTE='\'';
	
	private static final String TABLE_LEFT_FLAG="@{";
	private static final String TABLE_RIGHT_FLAG="}";
	private static final String TABLE_PATTERN= "@\\{[a-zA-Z_0-9\\$]+\\}";
	private static final Pattern PATTERN=Pattern.compile(TABLE_PATTERN, Pattern.CASE_INSENSITIVE);
	
	public WrappedSql parse(String sql){
		boolean betweenSingleQuote=false;
		boolean betweenDoubleQuote=false;
		
		WrappedSql wrapped=new WrappedSql();
		List<String> originalSegs=split(sql);
		for(String seg:originalSegs){
			if(!isTableSegment(seg)){
				char[] array=seg.toCharArray();
				for(char c:array){
					if(c==SINGLE_QUOTE){
						betweenSingleQuote=(!betweenSingleQuote);
					}else if(c==DOUBLE_QUOTE){
						betweenDoubleQuote=(!betweenDoubleQuote);
					}
				}
				SqlSegment segment=new SqlSegment();
				segment.setSegment(seg);
				segment.setTableName(false);
				
				wrapped.addSegment(segment);
			}else{
				SqlSegment segment=new SqlSegment();
				
				if(betweenSingleQuote){									
					segment.setTableName(false);
					segment.setSegment(seg);
				}else if(betweenDoubleQuote){
					segment.setTableName(false);
					segment.setSegment(seg);
				}else{
					segment.setTableName(true);
					segment.setSegment(parseTableName(seg));
				}
				wrapped.addSegment(segment);
			}
		}
		return wrapped;
	}
	
	private boolean isTableSegment(String seg){
		Matcher matcher=PATTERN.matcher(seg);
		return matcher.matches();
	}
	
	private String parseTableName(String flagTable){
		int start=TABLE_LEFT_FLAG.length();
		int end=flagTable.length()-TABLE_RIGHT_FLAG.length();
		return flagTable.substring(start, end);
	}
	
	private List<String> split(String sql){
		if(sql==null){
			throw new IllegalArgumentException("Argument sql must not null.");
		}
		List<String> list=new ArrayList<String>();
		Matcher matcher=PATTERN.matcher(sql);
		
		int start=0;
		boolean find=matcher.find();
		while(find){
//			System.out.println(sql.substring(start, matcher.start()));
			list.add(sql.substring(start, matcher.start()));
			start=matcher.end();
			
//			System.out.println(matcher.group());
			list.add(matcher.group());
//			System.out.println("start:"+matcher.start()+" end:"+matcher.end());
			find=matcher.find();
		}
		if(start<sql.length()){
			list.add(sql.substring(start, sql.length()));
		}
		return list;
	}
	
	public static void main(String[] args){
		TableParser parser=new TableParser();
		String str="select count(*) from @{app_test},@{app_test2}  where DELETED = 'N' and id in ";
		str+="(select id from @{xxx} where col1=? and col1=?)";
		
//		System.out.println(parser.split(str));
//		
//		System.out.println("=====================================");
//		str="SELECT EMPLOYEEIDNO FROM mytable WHERE SALARY > 40000 AND POSITION = 'Staff'";
//		
//		System.out.println(parser.split(str));
//		
//		Matcher matcher=PATTERN.matcher("@{app_test2}");
//		System.out.println(matcher.matches());
		str="select count(*) as \"@{col}\" from @{app_test},@{app_test2}  where DELETED = 'N' and id in ";
		str+="(select id from @{xxx} where col1=? and col1='vvvvv@{value} value')";
		WrappedSql ws=parser.parse(str);
		System.out.println(ws.getSegments());
		System.out.println(ws);
		
		str="update @{app_test} set col1='sfsdfsfsf@{fdad}'";
		ws=parser.parse(str);
		System.out.println(ws.getSegments());
		System.out.println(ws);
	}
}
