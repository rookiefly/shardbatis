/**
 * 
 */
package com.google.code.shardbatis.plugin;

import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Ignore;
import org.junit.Test;

import com.google.code.shardbatis.test.mapper.AppTestMapper;

/**
 * @author sean.he
 *
 */
public class PerformanceTest {
	
	/**
	 * -server -XX:-PrintCompilation -Xmx512m -Xms512m -Xmn256m
	 * @throws Exception
	 */
	@Ignore
	@Test
	public void testWithPlugin() throws Exception{
		String resource = "MapperConfig.xml";
		Reader reader = Resources.getResourceAsReader(resource);
		final SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
		
		//预热
		for (int i = 0; i < 10000; i++) {
			SqlSession session = sqlSessionFactory.openSession();
			try {
				AppTestMapper mapper = session.getMapper(AppTestMapper.class);
				mapper.getList(null);
				session.commit();
			} finally {
				session.close();
			}
		}
		//测试
		class Worker implements Runnable{
			public void run() {
				long start=System.currentTimeMillis();
				for (int i = 0; i < 10000; i++) {
					SqlSession session = sqlSessionFactory.openSession();
					try {
						AppTestMapper mapper = session.getMapper(AppTestMapper.class);
						mapper.getList(null);
						session.commit();
					} finally {
						session.close();
					}
				}
				long end=System.currentTimeMillis();
				System.out.println(Thread.currentThread().getId()+" :"+(end-start));
			}
		}
		for(int i=0;i<20;i++){
			Worker worker=new Worker();
			Thread tt=new Thread(worker);
			tt.start();
		}
		
		Thread.sleep(1000*60*20L);
	}
	
	/**
	 * -server -XX:-PrintCompilation -Xmx512m -Xms512m -Xmn256m
	 * @throws Exception
	 */
//	@Ignore
	@Test
	public void testNoPlugin() throws Exception{
		String resource = "MapperConfig_3.xml";
		Reader reader = Resources.getResourceAsReader(resource);
		final SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
		
		//预热
		for (int i = 0; i < 10000; i++) {
			SqlSession session = sqlSessionFactory.openSession();
			try {
				AppTestMapper mapper = session.getMapper(AppTestMapper.class);
				mapper.getList(null);
				session.commit();
			} finally {
				session.close();
			}
		}
		//测试
		class Worker implements Runnable{
			public void run() {
				long start=System.currentTimeMillis();
				for (int i = 0; i < 10000; i++) {
					SqlSession session = sqlSessionFactory.openSession();
					try {
						AppTestMapper mapper = session.getMapper(AppTestMapper.class);
						mapper.getList(null);
						session.commit();
					} finally {
						session.close();
					}
				}
				long end=System.currentTimeMillis();
				System.out.println(Thread.currentThread().getId()+" :"+(end-start));
			}
		}
		for(int i=0;i<20;i++){
			Worker worker=new Worker();
			Thread tt=new Thread(worker);
			tt.start();
		}
		
		Thread.sleep(1000*60*20L);
	}
	
	
}
