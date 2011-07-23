package com.google.code.shardbatis.builder;

import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.junit.Assert;
import org.junit.Test;
import org.xml.sax.SAXException;

import com.google.code.shardbatis.strategy.ShardStrategy;


public class ShardConfigParserTest {
	@Test
	public void testParse_1() throws Exception{
		ShardConfigParser parser=new ShardConfigParser();
		InputStream input= Resources.getResourceAsStream("test_config.xml");
		ShardConfigHolder factory=parser.parse(input);
		
		ShardStrategy strategy=factory.getStrategy("test_table1");
		Assert.assertNotNull(strategy);
		
		strategy=factory.getStrategy("test_table2");
		Assert.assertNotNull(strategy);
		
		boolean configed=factory.isConfigParseId();
		Assert.assertFalse(configed);
		
		boolean ret=factory.isIgnoreId("ignoreId1");
		Assert.assertFalse(ret);
		
		ret=factory.isParseId("parseId");
		Assert.assertFalse(ret);
	}
	
	@Test
	public void testParse_2() throws Exception{
		ShardConfigParser parser=new ShardConfigParser();
		InputStream input= Resources.getResourceAsStream("test_config_2.xml");
		ShardConfigHolder factory=parser.parse(input);
		
		ShardStrategy strategy=factory.getStrategy("test_table1");
		Assert.assertNotNull(strategy);
		
		strategy=factory.getStrategy("test_table2");
		Assert.assertNotNull(strategy);
		
		boolean configed=factory.isConfigParseId();
		Assert.assertTrue(configed);
		
		boolean ret=factory.isIgnoreId("ignoreId1");
		Assert.assertTrue(ret);
		
		ret=factory.isIgnoreId("ignoreId2");
		Assert.assertTrue(ret);
		
		ret=factory.isIgnoreId("<testid");
		Assert.assertTrue(ret);
		
		ret=factory.isIgnoreId("xxx");
		Assert.assertFalse(ret);
		
		ret=factory.isParseId("parseId");
		Assert.assertTrue(ret);
		
		ret=factory.isParseId("parseid>2");
		Assert.assertTrue(ret);
		
		ret=factory.isParseId("xxxxxxx");
		Assert.assertFalse(ret);
	}
	
	@Test(expected=SAXException.class)
	public void testParseFail()throws Exception{
		ShardConfigParser parser=new ShardConfigParser();
		InputStream input= Resources.getResourceAsStream("error_config.xml");
		parser.parse(input);
		
		Assert.fail();
	}
}
