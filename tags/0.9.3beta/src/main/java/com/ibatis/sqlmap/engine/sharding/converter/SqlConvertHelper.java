/**
 * 
 */
package com.ibatis.sqlmap.engine.sharding.converter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.ibatis.sqlmap.engine.sharding.ShardingFactorGroup;
import com.ibatis.sqlmap.engine.sharding.ShardingStrategy;
import com.ibatis.sqlmap.engine.sharding.parser.SqlSegment;
import com.ibatis.sqlmap.engine.sharding.parser.TableParser;
import com.ibatis.sqlmap.engine.sharding.parser.WrappedSql;

/**
 * @author sean.he
 * 
 */
public class SqlConvertHelper {
	private static ConcurrentHashMap<String, WrappedSql> cache = new ConcurrentHashMap<String, WrappedSql>();
	private static TableParser parser = new TableParser();
	
	private static WrappedSql wpsql;//TODO FOR TEST

	public static String convert(String sql, ShardingFactorGroup... groups) {
//		long start=System.currentTimeMillis();
//		WrappedSql wrapped = cache.get(sql);
		WrappedSql wrapped =wpsql;
		if (wrapped == null) {
			wrapped = parser.parse(sql);
//			cache.putIfAbsent(sql, wrapped);//TODO FOR TEST
			wpsql=wrapped;//TODO FOR TEST
		}
		Map<String, ShardingFactorGroup> map = listToMap(groups);

		List<SqlSegment> segments = wrapped.getSegments();
		StringBuilder builder = new StringBuilder();
		for (SqlSegment each : segments) {
			if (each.isTableName()) {
				ShardingFactorGroup group = map.get(each.getSegment()
						.toLowerCase());//notice:key is lowercase
				String newName = null;
				if (group != null) {
					ShardingStrategy strategy = group.getShardingStrategy();
					if (strategy != null) {
						newName = strategy.getTargetTableName(
								each.getSegment(), group.getParam());

					}
				}
				if (newName == null || newName.length() == 0) {
					newName = each.getSegment();
				}
				builder.append(newName);
			} else {
				builder.append(each.getSegment());
			}
		}
//		System.out.println(System.currentTimeMillis()-start);
		return builder.toString();
	}

	private static Map<String, ShardingFactorGroup> listToMap(
			ShardingFactorGroup... groups) {
		Map<String, ShardingFactorGroup> map = new HashMap<String, ShardingFactorGroup>();
		if (groups != null) {
			for (ShardingFactorGroup group : groups) {
				map.put(group.getTableName(), group);
			}
		}
		return map;
	}
}
