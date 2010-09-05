/**
 * 
 */
package com.ibatis.sqlmap.engine.sharding;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

import com.ibatis.sqlmap.engine.config.ShardingConfig;

/**
 * @author sean.he
 * 
 */
public class ShardingFactorConfig extends ShardingFactorGroup {
	private String paramExpr;
	private String strategyClass;

	public String getParamExpr() {
		return paramExpr;
	}

	public void setParamExpr(String paramExpr) {
		this.paramExpr = paramExpr;
	}

	public String getStrategyClass() {
		return strategyClass;
	}

	public void setStrategyClass(String strategyClass) {
		this.strategyClass = strategyClass;
	}
	/**
	 * transform ShardingFactorConfig's property to ShardingFactorGroup's property
	 * @param sc
	 * @param parameterObject
	 * @return
	 */
	public ShardingFactorGroup transform(ShardingConfig sc,
			Object parameterObject) {
		if (paramExpr == null) {
			return null;
		}
		String[] keys = paramExpr.split("\\.");
		Object input = parameterObject;
		for (String key : keys) {
			if (input instanceof Map) {
				input = ((Map) input).get(key);
			} else {
				String first = key.substring(0, 1);
				StringBuilder sb = new StringBuilder("get");
				sb.append(first.toUpperCase());
				sb.append(key.substring(1));
				String methodName = sb.toString();
				Method method;
				try {
					method = input.getClass().getMethod(methodName);
					input = method.invoke(input);
				} catch (SecurityException e) {
					throw new ShardingConfigException(e);
				} catch (NoSuchMethodException e) {
					throw new ShardingConfigException(e);
				} catch (IllegalArgumentException e) {
					throw new ShardingConfigException(e);
				} catch (IllegalAccessException e) {
					throw new ShardingConfigException(e);
				} catch (InvocationTargetException e) {
					throw new ShardingConfigException(e);
				}

			}
		}
		setParam(input);
		if (this.strategyClass == null
				|| this.strategyClass.trim().length() == 0) {
			if (sc != null) {
				this.setShardingStrategy(sc.getStrategy());
			}
		} else {
			try {
				Class strategy=Class.forName(strategyClass);
				ShardingStrategy ss=(ShardingStrategy)strategy.newInstance();
				this.setShardingStrategy(ss);
			} catch (ClassNotFoundException e) {
				throw new ShardingConfigException(e);
			} catch (InstantiationException e) {
				throw new ShardingConfigException(e);
			} catch (IllegalAccessException e) {
				throw new ShardingConfigException(e);
			}
			
		}
		return this;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append("paramExpr=");
		sb.append(this.paramExpr);
		sb.append(",strategyClass=");
		sb.append(this.strategyClass);
		sb.append(",tableName=");
		sb.append(getTableName());
		sb.append("}");

		return sb.toString();
	}

}
