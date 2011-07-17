package org.shardbatis.test.ibatis;

import java.io.IOException;
import java.io.Reader;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.shardbatis.test.AppTest;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;
import com.ibatis.sqlmap.engine.sharding.ShardingFactorGroup;

public class SqlMapClientTest {
	SqlMapClient sqlMapper;

	@Before
	public void init() {
		Reader reader;
		try {
			reader = Resources.getResourceAsReader("sql-map-config.xml");
			sqlMapper = SqlMapClientBuilder.buildSqlMapClient(reader);
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	@Test
	public void test() throws Exception {
		Map param = new HashMap();
		param.put("cnt", "ttt");
		Integer count = (Integer) sqlMapper.queryForObject(
				"AppTest.select_paging_count_by_map", param);
		Assert.assertEquals(count.toString(), "0");
		
		AppTest at1 = new AppTest();
		at1.setCnt("test_cnt");
		at1.setId(1);
		count = (Integer) sqlMapper.queryForObject(
				"AppTest.select_perf_native", at1);
	}

	@Test
	public void testSharding() throws Exception {
		Map param = new HashMap();
		param.put("cnt", "ttt");
		ShardingFactorGroup g = new ShardingFactorGroup();
		g.setTableName("App_Test");
		g.setParam(new Integer(123));
		Integer count = (Integer) sqlMapper.queryForObjectWithSharding(
				"AppTest.select_paging_count_by_map", param, g);
		Assert.assertEquals(count.toString(), "0");
	}

	@Test
	public void testTx() throws Exception {
		ShardingFactorGroup g = new ShardingFactorGroup();
		g.setTableName("App_Test");
		g.setParam(new Integer(123));

		ShardingFactorGroup g2 = new ShardingFactorGroup();
		g2.setTableName("App_Test");
		g2.setParam(new Integer(2));

		try {
			AppTest at1 = new AppTest();
			at1.setCnt("test_cnt");

			AppTest at2 = new AppTest();
			at2.setCnt("test_cnt2");
			sqlMapper.startTransaction();
			sqlMapper.insert("AppTest.insert_h2", at1);
			sqlMapper.insertWithSharding("AppTest.insert_h2", at2, g);
			sqlMapper.insertWithSharding("AppTest.insert_h2", at2, g2);
			sqlMapper.commitTransaction();
		} finally {
			sqlMapper.endTransaction();
		}
	}

	@Test
	public void testUpdate() throws SQLException {
		ShardingFactorGroup g = new ShardingFactorGroup();
		g.setTableName("App_Test");
		g.setParam(new Integer(123));

		String cnt = "testUpdate" + System.currentTimeMillis();
		AppTest at1 = new AppTest();
		at1.setCnt(cnt);
		Integer id = (Integer) sqlMapper.insertWithSharding(
				"AppTest.insert_h2", at1, g);

		AppTest parameterObject = new AppTest();
		parameterObject.setCnt(cnt);
		AppTest ret = (AppTest) sqlMapper.queryForObjectWithSharding(
				"AppTest.select_by_condition", parameterObject, g);
		Assert.assertEquals(ret.getId().toString(), id.toString());

		ret.setCnt("NEW_CONTENT");
		Integer count = sqlMapper.updateWithSharding("AppTest.update", ret, g);
		Assert.assertEquals(count.toString(), "1");

		count = (Integer) sqlMapper.queryForObjectWithSharding(
				"AppTest.select_paging_count", ret, g);
		Assert.assertEquals(count.toString(), "1");
	}

	@Test
	public void testDelete() throws SQLException {
		ShardingFactorGroup g = new ShardingFactorGroup();
		g.setTableName("App_Test");
		g.setParam(new Integer(123));

		String cnt = "testDelete" + System.currentTimeMillis();
		AppTest at1 = new AppTest();
		at1.setCnt(cnt);
		Integer id = (Integer) sqlMapper.insertWithSharding(
				"AppTest.insert_h2", at1, g);

		AppTest parameterObject = new AppTest();
		parameterObject.setCnt(cnt);
		AppTest ret = (AppTest) sqlMapper.queryForObjectWithSharding(
				"AppTest.select_by_condition", parameterObject, g);
		Assert.assertEquals(ret.getId().toString(), id.toString());

		Integer row = sqlMapper.deleteWithSharding("AppTest.delete", ret, g);
		Assert.assertEquals(row.toString(), "1");
	}

	@Test
	public void testBatch() throws SQLException {
		ShardingFactorGroup g = new ShardingFactorGroup();
		g.setTableName("App_Test");
		g.setParam(new Integer(123));

		ShardingFactorGroup g2 = new ShardingFactorGroup();
		g2.setTableName("App_Test");
		g2.setParam(new Integer(2));
		// clean table app_test_1,app_test_0,app_test
		sqlMapper.deleteWithSharding("AppTest.delete", new AppTest(), g);
		sqlMapper.deleteWithSharding("AppTest.delete", new AppTest(), g2);
		sqlMapper.delete("AppTest.delete", new AppTest());

		AppTest at1 = new AppTest();
		at1.setCnt("testBatch");

		AppTest at2 = new AppTest();
		at2.setCnt("testBatch");

		sqlMapper.startBatch();

		sqlMapper.insert("AppTest.insert_h2", at1);
		sqlMapper.insertWithSharding("AppTest.insert_h2", at2, g);
		sqlMapper.insertWithSharding("AppTest.insert_h2", at2, g2);
		sqlMapper.insertWithSharding("AppTest.insert_h2", at2, g);

		sqlMapper.executeBatch();

		Integer count = (Integer) sqlMapper.queryForObjectWithSharding(
				"AppTest.select_paging_count", new AppTest(), g);
		Assert.assertEquals("2", count.toString());
		count = (Integer) sqlMapper.queryForObjectWithSharding(
				"AppTest.select_paging_count", new AppTest(), g2);
		Assert.assertEquals("1", count.toString());
		count = (Integer) sqlMapper.queryForObject(
				"AppTest.select_paging_count", new AppTest());
		Assert.assertEquals("1", count.toString());
	}
	
	@Test
	public void testShardingWithConfig() throws SQLException{
		AppTest param=new AppTest();
		param.setTestId(2);
		param.setCnt("testShardingWithConfig");
		Integer id=(Integer)sqlMapper.insert("AppTest.insert_h2_native", param);
		Integer count=(Integer)sqlMapper.queryForObject("AppTest.select_count_native",param);
		Assert.assertEquals("1", count.toString());
		
		param.setCnt("newCnt");
		count=(Integer)sqlMapper.update("AppTest.update_native", param);
		Assert.assertEquals("1", count.toString());
		
		param=new AppTest();
		param.setId(id);
		param.setTestId(2);
		count=(Integer)sqlMapper.delete("AppTest.delete_native", param);
		Assert.assertEquals("1", count.toString());
	}
	@Test
	public void testApiCoverConfig() throws SQLException{
		ShardingFactorGroup g = new ShardingFactorGroup();
		g.setTableName("App_Test");
		g.setParam(new Integer(123));
		
		AppTest param=new AppTest();
		param.setTestId(2);
		param.setCnt("testApiCoverConfig");
		
		Integer id=(Integer)sqlMapper.insertWithSharding("AppTest.insert_h2_native", param,g);
		
		param=new AppTest();
		param.setId(id);
		Integer count = (Integer) sqlMapper.queryForObjectWithSharding(
				"AppTest.select_paging_count", param, g);
		Assert.assertEquals("1", count.toString());
		
		param=new AppTest();
		param.setId(id);
		param.setTestId(1);
		count = (Integer) sqlMapper.queryForObjectWithSharding(
				"AppTest.select_count_native", param);
		Assert.assertEquals("1", count.toString());
	}
}
