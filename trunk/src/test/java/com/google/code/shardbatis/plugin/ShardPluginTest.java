package com.google.code.shardbatis.plugin;

import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.code.shardbatis.test.domain.AppTestDO;
import com.google.code.shardbatis.test.mapper.AppTestMapper;

/**
 * 运行测试前先启H2 TCP Server <br/>
 * 并执行mvn initialize -Pinitdb 进行数据初始化
 * 
 * @author sean.he
 * 
 */
public class ShardPluginTest {

	static SqlSessionFactory sqlSessionFactory;

	@BeforeClass
	public static void build() throws Exception {
		String resource = "MapperConfig.xml";
		Reader reader = Resources.getResourceAsReader(resource);
		sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
	}

	@Test
	public void test_1() {
		AppTestDO testDO = new AppTestDO();
		testDO.setCnt("just a test");
		SqlSession session = sqlSessionFactory.openSession();
		try {
			AppTestMapper mapper = session.getMapper(AppTestMapper.class);
			Integer id = mapper.insert(testDO);
			System.out.println(id);
			Assert.assertNotNull(id);
			session.commit();
		} finally {
			session.close();
		}
	}
	
	@Test
	public void test_2(){
		AppTestDO testDO = new AppTestDO();
		testDO.setCnt("just a test222");
		SqlSession session = sqlSessionFactory.openSession();
		try {
			AppTestMapper mapper = session.getMapper(AppTestMapper.class);
			Integer id = mapper.insert2(testDO);
			System.out.println(id);
			Assert.assertNotNull(id);
			session.commit();
		} finally {
			session.close();
		}
	}
	
//	@Test
//	public void test_3(){
//		AppTestDO testDO = new AppTestDO();
//		testDO.setCnt("just a test222");
//		SqlSession session = sqlSessionFactory.openSession();
//		try {
//			AppTestMapper mapper = session.getMapper(AppTestMapper.class);
//			Integer id = mapper.insert3(testDO);
//			System.out.println(id);
//			Assert.assertNotNull(id);
//			session.commit();
//		} finally {
//			session.close();
//		}
//	}
	
	@Test
	public void test_4(){
		AppTestDO testDO = new AppTestDO();
		testDO.setCnt("just a test222");
		SqlSession session = sqlSessionFactory.openSession();
		try {
			AppTestMapper mapper = session.getMapper(AppTestMapper.class);
			Integer id = mapper.insertNoShard(testDO);
			System.out.println(id);
			Assert.assertNotNull(id);
			session.commit();
		} finally {
			session.close();
		}
	}
}
