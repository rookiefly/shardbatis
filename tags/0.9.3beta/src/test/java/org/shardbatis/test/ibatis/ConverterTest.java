package org.shardbatis.test.ibatis;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import junit.framework.Assert;

import com.ibatis.sqlmap.engine.config.ShardingConfig;
import com.ibatis.sqlmap.engine.sharding.ShardingException;
import com.ibatis.sqlmap.engine.sharding.ShardingFactorConfig;
import com.ibatis.sqlmap.engine.sharding.ShardingFactorGroup;
import com.ibatis.sqlmap.engine.sharding.ShardingStrategy;
import com.ibatis.sqlmap.engine.sharding.converter.ConverterFactory;
import com.ibatis.sqlmap.engine.sharding.converter.SqlConvertHelper;
import com.ibatis.sqlmap.engine.sharding.impl.DefaultShardingStrategy;

public class ConverterTest {
	@Test
	public void testDelete() throws ShardingException{
		ShardingStrategy strategy=new DefaultShardingStrategy();
		ConverterFactory factory=ConverterFactory.getInstance();
		ShardingFactorGroup group1=new ShardingFactorGroup();
		group1.setTableName("antiqueowners");
		group1.setParam(new Integer(1));
		group1.setShardingStrategy(strategy);
		
		ShardingFactorGroup group2=new ShardingFactorGroup();
		group2.setTableName("ANTIQUES");
		group2.setParam(new Integer(2));
		group2.setShardingStrategy(strategy);
		
		String sql="DELETE FROM ANTIQUES WHERE ITEM = ? AND BUYERID = ? AND SELLERID = ?";
		String expect="DELETE FROM ANTIQUES_0 WHERE ITEM = ? AND BUYERID = ? AND SELLERID = ?";
		String ret=factory.convert(sql, group1,group2);
		Assert.assertEquals(ret, expect);
		
		sql="DELETE FROM ANTIQUES WHERE ITEM IN (SELECT ID FROM ANTIQUEOWNERS)";
		//Notice: sub select sql not support shardding
		ret=factory.convert(sql, group1,group2);
		System.out.println(ret);
		expect="DELETE FROM ANTIQUES_0 WHERE ITEM IN (SELECT ID FROM ANTIQUEOWNERS)";
		Assert.assertEquals(ret, expect);
		
		//test config
		ShardingConfig shardingConfig=new ShardingConfig();
		shardingConfig.setStrategy(strategy);
		shardingConfig.setTableName("ANTIQUES");
		
		ShardingFactorConfig config=new ShardingFactorConfig();
		config.setParamExpr("");
		config.setTableName("ANTIQUES");
//		config.setStrategyClass("com.ibatis.sqlmap.engine.sharding.impl.DefaultShardingStrategy");
		Object param=new Object();
		ShardingFactorGroup group=config.transform(shardingConfig, param);
		
		sql="DELETE FROM ANTIQUES WHERE ITEM = ? AND BUYERID = ? AND SELLERID = ?";
		expect="DELETE FROM ANTIQUES WHERE ITEM = ? AND BUYERID = ? AND SELLERID = ?";
		ret=factory.convert(sql, group);
		Assert.assertEquals(ret, expect);
		
		config.setParamExpr("#parameter#");
		config.setTableName("ANTIQUES");
		group=config.transform(shardingConfig, new Integer(2));
		
		expect="DELETE FROM ANTIQUES_0 WHERE ITEM = ? AND BUYERID = ? AND SELLERID = ?";
		ret=factory.convert(sql, group);
		Assert.assertEquals(ret, expect);
		
		config.setParamExpr(null);
		config.setTableName("ANTIQUES");
		group=config.transform(shardingConfig, new Integer(2));
		
		expect="DELETE FROM ANTIQUES WHERE ITEM = ? AND BUYERID = ? AND SELLERID = ?";
		ret=factory.convert(sql, group);
		Assert.assertEquals(ret, expect);
		
	}
	@Test
	public void testUpdate() throws ShardingException{
		ShardingStrategy strategy=new DefaultShardingStrategy();
		ConverterFactory factory=ConverterFactory.getInstance();
		ShardingFactorGroup group1=new ShardingFactorGroup();
		group1.setTableName("antiqueowners");
		group1.setParam(new Integer(1));
		group1.setShardingStrategy(strategy);
		
		ShardingFactorGroup group2=new ShardingFactorGroup();
		group2.setTableName("ANTIQUES");
		group2.setParam(new Integer(2));
		group2.setShardingStrategy(strategy);
		
		String sql="UPDATE ANTIQUES SET PRICE = ? WHERE ITEM = ?";
		String expect="UPDATE ANTIQUES_0 SET PRICE=? WHERE ITEM = ?";
		String ret=factory.convert(sql, group1,group2);
		Assert.assertEquals(ret, expect);
	}
	@Test
	public void testInsert() throws ShardingException{
		ShardingStrategy strategy=new DefaultShardingStrategy();
		ConverterFactory factory=ConverterFactory.getInstance();
		
		String sql="INSERT INTO ANTIQUES (BUYERID, SELLERID, ITEM) VALUES (?, ?, 'Ottoman')";
		ShardingFactorGroup group1=new ShardingFactorGroup();
		group1.setTableName("antiqueowners");
		group1.setParam(new Integer(1));
		group1.setShardingStrategy(strategy);
		
		ShardingFactorGroup group2=new ShardingFactorGroup();
		group2.setTableName("ANTIQUES");
		group2.setParam(new Integer(2));
		group2.setShardingStrategy(strategy);
		String ret=factory.convert(sql, group1,group2);
		String expect="INSERT INTO ANTIQUES_0(BUYERID, SELLERID, ITEM) VALUES (?, ?, 'Ottoman')";
		Assert.assertEquals(ret, expect);
		
		sql="INSERT INTO ANTIQUES VALUES (?, ?, ?, ?)";
		expect="INSERT INTO ANTIQUES_0 VALUES (?, ?, ?, ?)";
		ret=factory.convert(sql, group1,group2);
		Assert.assertEquals(ret, expect);
	}
	
	@Test
	public void testSelectWithConfig() throws ShardingException{
		ShardingStrategy strategy=new DefaultShardingStrategy();
		ConverterFactory factory=ConverterFactory.getInstance();
		//test config
		ShardingConfig shardingConfig=new ShardingConfig();
		shardingConfig.setStrategy(strategy);
		shardingConfig.setTableName("ANTIQUES");
		
		ShardingFactorConfig config=new ShardingFactorConfig();
		config.setParamExpr("");
		config.setTableName("ANTIQUES");
		
		String sql="SELECT a.OWNERLASTNAME, a.OWNERFIRSTNAME "
			+"FROM ANTIQUEOWNERS as a, ANTIQUES as b "
			+"WHERE b.BUYERID = a.OWNERID AND b.ITEM = 'Chair'";
		String expect="SELECT a.OWNERLASTNAME, a.OWNERFIRSTNAME "
			+"FROM ANTIQUEOWNERS AS a, ANTIQUES AS b "
			+"WHERE b.BUYERID = a.OWNERID AND b.ITEM = 'Chair'";
		
		ShardingFactorGroup group=config.transform(shardingConfig, new Integer(2));
		String ret=factory.convert(sql, group);
		Assert.assertEquals(expect, ret);
		
		config=new ShardingFactorConfig();
		config.setParamExpr(null);
		config.setTableName("ANTIQUES");
		group=config.transform(shardingConfig, new Integer(2));
		ret=factory.convert(sql, group);
		Assert.assertEquals(expect, ret);
		
		config=new ShardingFactorConfig();
		config.setParamExpr("#parameter#");
		config.setTableName("ANTIQUES");
		group=config.transform(shardingConfig, new Integer(2));
		ret=factory.convert(sql, group);
		
		expect="SELECT a.OWNERLASTNAME, a.OWNERFIRSTNAME "
			+"FROM ANTIQUEOWNERS AS a, ANTIQUES_0 AS b "
			+"WHERE b.BUYERID = a.OWNERID AND b.ITEM = 'Chair'";
		Assert.assertEquals(expect, ret);
		
		Map<String,Object> param=new HashMap<String,Object>();
		param.put("key", new Integer(123456));
		
		config=new ShardingFactorConfig();
		config.setParamExpr("key");
		config.setTableName("ANTIQUES");
		group=config.transform(shardingConfig, param);
		ret=factory.convert(sql, group);
		expect="SELECT a.OWNERLASTNAME, a.OWNERFIRSTNAME "
			+"FROM ANTIQUEOWNERS AS a, ANTIQUES_0 AS b "
			+"WHERE b.BUYERID = a.OWNERID AND b.ITEM = 'Chair'";
		Assert.assertEquals(expect, ret);
		
		config=new ShardingFactorConfig();
		config.setParamExpr("key");
		config.setTableName("ANTIQUES");
		group=config.transform(shardingConfig, param);
		
		config=new ShardingFactorConfig();
		config.setParamExpr(null);
		config.setTableName("ANTIQUEOWNERS");
		ShardingFactorGroup group1=config.transform(shardingConfig, param);

		ret=factory.convert(sql, group,group1);
		
		expect="SELECT a.OWNERLASTNAME, a.OWNERFIRSTNAME "
			+"FROM ANTIQUEOWNERS AS a, ANTIQUES_0 AS b "
			+"WHERE b.BUYERID = a.OWNERID AND b.ITEM = 'Chair'";
		Assert.assertEquals(expect, ret);
		
		param.put("key1", new Integer(7));
		config=new ShardingFactorConfig();
		config.setParamExpr("key1");
		config.setTableName("ANTIQUEOWNERS");
		group1=config.transform(shardingConfig, param);

		ret=factory.convert(sql, group,group1);
		
		expect="SELECT a.OWNERLASTNAME, a.OWNERFIRSTNAME "
			+"FROM ANTIQUEOWNERS_1 AS a, ANTIQUES_0 AS b "
			+"WHERE b.BUYERID = a.OWNERID AND b.ITEM = 'Chair'";
		Assert.assertEquals(expect, ret);
		
	}
	
	@Test
	public void testSelect() throws ShardingException{
		ShardingStrategy strategy=new DefaultShardingStrategy();
		
		String sql="SELECT FirstName, LastName, Address, City, State FROM mytable";
		ConverterFactory factory=ConverterFactory.getInstance();
		ShardingFactorGroup group=new ShardingFactorGroup();
		group.setTableName("Mytable");
		group.setShardingStrategy(strategy);
		group.setParam(new Integer(1));
		String ret=factory.convert(sql, group);
		Assert.assertEquals("SELECT FirstName, LastName, Address, City, State FROM mytable_1", ret);
		
		sql="SELECT EMPLOYEEIDNO FROM mytable WHERE SALARY >= 50000";
		ret=factory.convert(sql, group);
		Assert.assertEquals("SELECT EMPLOYEEIDNO FROM mytable_1 WHERE SALARY >= 50000", ret);
		
		sql="SELECT EMPLOYEEIDNO FROM mytable WHERE SALARY > 40000 AND POSITION = 'Staff'";
		ret=factory.convert(sql, group);
		Assert.assertEquals("SELECT EMPLOYEEIDNO FROM mytable_1 WHERE SALARY > 40000 AND POSITION = 'Staff'", ret);
		
		sql="SELECT EMPLOYEEIDNO FROM mytable WHERE POSITION = ? AND SALARY > ? OR BENEFITS > 12000";
		ret=factory.convert(sql, group);
		Assert.assertEquals("SELECT EMPLOYEEIDNO FROM mytable_1 WHERE POSITION = ? AND SALARY > ? OR BENEFITS > 12000", ret);
		
		sql="SELECT EMPLOYEEIDNO FROM mytable WHERE POSITION = '?' AND (SALARY > ? OR BENEFIT > ?)";
		ret=factory.convert(sql, group);
		Assert.assertEquals("SELECT EMPLOYEEIDNO FROM mytable_1 WHERE POSITION = '?' AND (SALARY > ? OR BENEFIT > ?)", ret);
		
		sql="SELECT EMPLOYEEIDNO FROM MYTABLE WHERE POSITION IN (?, ?)";
		ret=factory.convert(sql, group);
		Assert.assertEquals("SELECT EMPLOYEEIDNO FROM MYTABLE_1 WHERE POSITION IN (?, ?)", ret);
		
		sql="SELECT OWNERLASTNAME, OWNERFIRSTNAME FROM ANTIQUEOWNERS, ANTIQUES WHERE BUYERID = OWNERID AND ITEM = 'Chair'";
		ret=factory.convert(sql, group);
		Assert.assertEquals("SELECT OWNERLASTNAME, OWNERFIRSTNAME FROM ANTIQUEOWNERS, ANTIQUES WHERE BUYERID = OWNERID AND ITEM = 'Chair'", ret);
		
		ShardingFactorGroup group1=new ShardingFactorGroup();
		group1.setTableName("antiqueowners");
		group1.setParam(new Integer(1));
		group1.setShardingStrategy(strategy);
		
		ShardingFactorGroup group2=new ShardingFactorGroup();
		group2.setTableName("ANTIQUES");
		group2.setParam(new Integer(2));
		group2.setShardingStrategy(strategy);
		ret=factory.convert(sql, group,group1,group2);
		Assert.assertEquals("SELECT OWNERLASTNAME, OWNERFIRSTNAME FROM ANTIQUEOWNERS_1, ANTIQUES_0 WHERE BUYERID = OWNERID AND ITEM = 'Chair'", ret);
		
		// NOTICE:join table must set alias of table name
		sql="SELECT ANTIQUEOWNERS.OWNERLASTNAME, ANTIQUEOWNERS.OWNERFIRSTNAME "
			+"FROM ANTIQUEOWNERS, ANTIQUES "
			+"WHERE ANTIQUES.BUYERID = ANTIQUEOWNERS.OWNERID AND ANTIQUES.ITEM = 'Chair'";
		ret=factory.convert(sql, group,group1,group2);
		//System.out.println(ret);
		String expect="SELECT ANTIQUEOWNERS.OWNERLASTNAME, ANTIQUEOWNERS.OWNERFIRSTNAME "
			+"FROM ANTIQUEOWNERS_1, ANTIQUES_0 "
			+"WHERE ANTIQUES_0.BUYERID = ANTIQUEOWNERS_1.OWNERID AND ANTIQUES_0.ITEM = 'Chair'";
		Assert.assertFalse(expect.equals(ret));
		
		sql="SELECT a.OWNERLASTNAME, a.OWNERFIRSTNAME "
			+"FROM ANTIQUEOWNERS as a, ANTIQUES as b "
			+"WHERE b.BUYERID = a.OWNERID AND b.ITEM = 'Chair'";
		expect="SELECT a.OWNERLASTNAME, a.OWNERFIRSTNAME "
			+"FROM ANTIQUEOWNERS_1 AS a, ANTIQUES_0 AS b "
			+"WHERE b.BUYERID = a.OWNERID AND b.ITEM = 'Chair'";
		ret=factory.convert(sql, group,group1,group2);
		//System.out.println(ret);
		Assert.assertEquals(expect, ret);
		
		sql="SELECT a.OWNERLASTNAME, a.OWNERFIRSTNAME "
			+"FROM ANTIQUEOWNERS a, ANTIQUES b "
			+"WHERE b.BUYERID = a.OWNERID AND b.ITEM = 'Chair'";
		ret=factory.convert(sql, group,group1,group2);
		//System.out.println(ret);
		Assert.assertEquals(expect, ret);
		
		sql="SELECT OWN.OWNERLASTNAME \"Last Name\", ORD.ITEMDESIRED \"Item Ordered\" "
			+"FROM ORDERS ORD, ANTIQUEOWNERS OWN "
			+"WHERE ORD.OWNERID = OWN.OWNERID "
			+"AND ORD.ITEMDESIRED IN "
			+"(SELECT ITEM FROM ANTIQUES)";
		
		expect="SELECT OWN.OWNERLASTNAME AS \"Last Name\", ORD.ITEMDESIRED AS \"Item Ordered\" "
			+"FROM ORDERS AS ORD, ANTIQUEOWNERS_1 AS OWN "
			+"WHERE ORD.OWNERID = OWN.OWNERID "
			+"AND ORD.ITEMDESIRED IN "
			+"(SELECT ITEM FROM ANTIQUES_0)";
		ret=factory.convert(sql, group,group1,group2);
		//System.out.println(ret);
		Assert.assertEquals(expect, ret);
		
		sql="SELECT OWN.OWNERLASTNAME as c1, ORD.ITEMDESIRED as c2 "
			+"FROM ORDERS ORD, ANTIQUEOWNERS OWN "
			+"WHERE ORD.OWNERID = OWN.OWNERID "
			+"AND ORD.ITEMDESIRED IN "
			+"(SELECT ITEM FROM ANTIQUES)";
		expect="SELECT OWN.OWNERLASTNAME AS c1, ORD.ITEMDESIRED AS c2 "
			+"FROM ORDERS AS ORD, ANTIQUEOWNERS_1 AS OWN "
			+"WHERE ORD.OWNERID = OWN.OWNERID "
			+"AND ORD.ITEMDESIRED IN "
			+"(SELECT ITEM FROM ANTIQUES_0)";
		ret=factory.convert(sql, group,group1,group2);
		//System.out.println(ret);
		Assert.assertEquals(expect, ret);
		
		sql="SELECT OWNERFIRSTNAME, OWNERLASTNAME FROM ANTIQUEOWNERS "
			+"WHERE EXISTS (SELECT * FROM ANTIQUES WHERE ITEM = ?) AND OWNERID IN (?,?,?)";
		expect="SELECT OWNERFIRSTNAME, OWNERLASTNAME FROM ANTIQUEOWNERS_1 "
			+"WHERE EXISTS (SELECT * FROM ANTIQUES_0 WHERE ITEM = ?) AND OWNERID IN (?, ?, ?)";
		ret=factory.convert(sql, group,group1,group2);
		//System.out.println(ret);
		Assert.assertEquals(expect, ret);
		
		sql="SELECT OWNERID, 'is in both Orders & Antiques' FROM ORDERS AS ORD, ANTIQUES AS AQS WHERE ORD.OWNERID = AQS.BUYERID "
			+"UNION "
			+"SELECT BUYERID, 'is in Antiques only' FROM ANTIQUES WHERE BUYERID NOT IN (SELECT OWNERID FROM ORDERS)";
				
		expect="(SELECT OWNERID, 'is in both Orders & Antiques' FROM ORDERS AS ORD, ANTIQUES_0 AS AQS WHERE ORD.OWNERID = AQS.BUYERID) "
			+"UNION "
			+"(SELECT BUYERID, 'is in Antiques only' FROM ANTIQUES_0 WHERE BUYERID NOT IN (SELECT OWNERID FROM ORDERS))";
		ret=factory.convert(sql, group,group1,group2);
		Assert.assertEquals(expect, ret);
		
		sql="SELECT BUYERID, MAX(PRICE) FROM ANTIQUES GROUP BY BUYERID HAVING PRICE > ?";
		expect="SELECT BUYERID, MAX(PRICE) FROM ANTIQUES_0 GROUP BY BUYERID HAVING PRICE > ?";
		ret=factory.convert(sql, group,group1,group2);
		Assert.assertEquals(expect, ret);
		
		sql="SELECT * FROM mytable as mytable0, mytable1 alias_tab1, ANTIQUES as alias_tab2, (SELECT * FROM ANTIQUEOWNERS) AS mytable4 WHERE mytable.col = ?";
		expect="SELECT * FROM mytable_1 AS mytable0, mytable1 AS alias_tab1, ANTIQUES_0 AS alias_tab2, (SELECT * FROM ANTIQUEOWNERS_1) AS mytable4 WHERE mytable.col = ?";
		ret=factory.convert(sql, group,group1,group2);
		//System.out.println(ret);
		Assert.assertEquals(expect, ret);
		
		sql="SELECT * FROM ANTIQUES tab1 LEFT outer JOIN ANTIQUEOWNERS tab2 ON tab1.id = tab2.id JOIN tab3";
		expect="SELECT * FROM ANTIQUES_0 AS tab1 LEFT OUTER JOIN ANTIQUEOWNERS_1 AS tab2 ON tab1.id = tab2.id JOIN tab3";
		ret=factory.convert(sql, group,group1,group2);
		//System.out.println(ret);
		Assert.assertEquals(expect, ret);
		
		sql="SELECT a.* FROM ANTIQUES a,ANTIQUEOWNERS b, mytable c where a.id=b.id and b.id=c.id";
		expect="SELECT a.* FROM ANTIQUES_0 AS a, ANTIQUEOWNERS_1 AS b, mytable_1 AS c WHERE a.id = b.id AND b.id = c.id";
		ret=factory.convert(sql, group,group1,group2);
		//System.out.println(ret);
		Assert.assertEquals(expect, ret);
		
		
	}
	
	@Test
	public void testWithFlag(){
		ShardingStrategy strategy=new DefaultShardingStrategy();
		
		ShardingFactorGroup group=new ShardingFactorGroup();
		group.setTableName("Mytable");
		group.setShardingStrategy(strategy);
		group.setParam(new Integer(1));
		
		ShardingFactorGroup group1=new ShardingFactorGroup();
		group1.setTableName("antiqueowners");
		group1.setParam(new Integer(1));
		group1.setShardingStrategy(strategy);
		
		ShardingFactorGroup group2=new ShardingFactorGroup();
		group2.setTableName("ANTIQUES");
		group2.setParam(new Integer(2));
		group2.setShardingStrategy(strategy);
		
		String sql="SELECT * FROM @{ANTIQUES} tab1 LEFT outer JOIN @{ANTIQUEOWNERS} tab2 ON tab1.id = tab2.id JOIN tab3";
		String ret=SqlConvertHelper.convert(sql, group,group1,group2);
		System.out.println(ret);
		
		sql="SELECT OWNERID, 'is in both Orders & Antiques' FROM @{ORDERS} AS ORD, @{ANTIQUES} AS AQS WHERE ORD.OWNERID = AQS.BUYERID "
			+"UNION "
			+"SELECT BUYERID, 'is in Antiques only' FROM @{ANTIQUES} WHERE BUYERID NOT IN (SELECT OWNERID FROM @{ORDERS})";
		ret=SqlConvertHelper.convert(sql, group,group1,group2);
		System.out.println(ret);
	}
}
