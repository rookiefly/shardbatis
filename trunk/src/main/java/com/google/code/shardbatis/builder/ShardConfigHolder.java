/**
 * 
 */
package com.google.code.shardbatis.builder;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.google.code.shardbatis.strategy.ShardStrategy;

/**
 * @author sean.he
 * 
 */
public class ShardConfigHolder {
	private static final ShardConfigHolder instance = new ShardConfigHolder();

	public static ShardConfigHolder getInstance() {
		return instance;
	}

	private Map<String, ShardStrategy> strategyRegister = new HashMap<String, ShardStrategy>();

	private Set<String> ignoreSet;
	private Set<String> parseSet;

	private ShardConfigHolder() {
	}

	/**
	 * 注册分表策略
	 * 
	 * @param table
	 * @param strategy
	 */
	public void register(String table, ShardStrategy strategy) {
		this.strategyRegister.put(table.toLowerCase(), strategy);
	}

	/**
	 * 查找对应表的分表策略
	 * 
	 * @param table
	 * @return
	 */
	public ShardStrategy getStrategy(String table) {
		return strategyRegister.get(table.toLowerCase());
	}

	/**
	 * 增加ignore id配置
	 * 
	 * @param id
	 */
	public synchronized void addIgnoreId(String id) {
		if (ignoreSet == null) {
			ignoreSet = new HashSet<String>();
		}
		ignoreSet.add(id);
	}

	/**
	 * 增加parse id配置
	 * 
	 * @param id
	 */
	public synchronized void addParseId(String id) {
		if (parseSet == null) {
			parseSet = new HashSet<String>();
		}
		parseSet.add(id);
	}

	/**
	 * 判断是否配置过parse id<br>
	 * 如果配置过parse id,shardbatis只对parse id范围内的sql进行解析和修改
	 * 
	 * @return
	 */
	public boolean isConfigParseId() {
		return parseSet != null;
	}

	/**
	 * 判断参数ID是否在配置的parse id范围内
	 * 
	 * @param id
	 * @return
	 */
	public boolean isParseId(String id) {
		return parseSet != null && parseSet.contains(id);
	}

	/**
	 * 判断参数ID是否在配置的ignore id范围内
	 * 
	 * @param id
	 * @return
	 */
	public boolean isIgnoreId(String id) {
		return ignoreSet != null && ignoreSet.contains(id);
	}
}
