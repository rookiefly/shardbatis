/**
 * 
 */
package org.shardbatis.test.spring;

import org.junit.Assert;
import org.junit.Test;
import org.shardbatis.test.AppTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import com.ibatis.sqlmap.engine.sharding.ShardingFactorGroup;

/**
 * @author sean.he
 *
 */
@ContextConfiguration(locations = { "/applicationContext-test.xml" })
public class SqlMapClientTemplateTest extends
		AbstractTransactionalJUnit4SpringContextTests {
	@Autowired
	TestDao testDao;
	
	@Test
	public void testQuery(){
		AppTest param = new AppTest();
		ShardingFactorGroup g = new ShardingFactorGroup();
		g.setTableName("App_Test");
		g.setParam(new Integer(123));
		
		ShardingFactorGroup g2 = new ShardingFactorGroup();
		g2.setTableName("App_Test");
		g2.setParam(new Integer(2));
		
		testDao.remove(new AppTest() , "AppTest.delete", g);
		testDao.remove(new AppTest() , "AppTest.delete", g2);
		
		AppTest ret=testDao.get(AppTest.class, param, "AppTest.select_by_condition", g);
		Assert.assertNull(ret);
		ret=testDao.get(AppTest.class, param, "AppTest.select_by_condition", g2);
		Assert.assertNull(ret);
		
		String cnt = "testQuery" + System.currentTimeMillis();
		AppTest at1 = new AppTest();
		at1.setCnt(cnt);
		Integer id=testDao.insert(at1, "AppTest.insert_h2", g);
		ret=testDao.get(AppTest.class, at1, "AppTest.select_by_condition", g);
		Assert.assertEquals(ret.getId().toString(),id.toString());
	}
}
