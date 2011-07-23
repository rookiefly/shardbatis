package com.google.code.shardbatis.converter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.apache.ibatis.io.Resources;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.code.shardbatis.ShardException;
import com.google.code.shardbatis.builder.ShardConfigParser;

public class SqlConverterFactoryTest {

	@BeforeClass
	public static void parseConfig() {
		ShardConfigParser parser = new ShardConfigParser();
		InputStream input = null;
		try {
			input = Resources.getResourceAsStream("test_config.xml");
			parser.parse(input);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Test
	public void test_1() throws IOException, ShardException {
		SqlConverterFactory cf = SqlConverterFactory.getInstance();
		BufferedReader reader = new BufferedReader(Resources
				.getResourceAsReader("sql_src.txt"));
		String instring = null;

		List<String> converted = new ArrayList<String>();
		while ((instring = reader.readLine()) != null) {
			if (instring != null) {
				String sql = cf.convert(instring, null, null);
				converted.add(sql);
			}
		}

		List<String> expectList = new ArrayList<String>();
		reader = new BufferedReader(Resources
				.getResourceAsReader("sql_ret.txt"));
		instring = null;
		while ((instring = reader.readLine()) != null) {
			if (instring != null) {
				expectList.add(instring);
			}
		}

		for (int i = 0; i < converted.size(); i++) {
			String sql = converted.get(i);
			String expect = expectList.get(i);
			Assert.assertEquals(expect, sql);
		}
	}
}
