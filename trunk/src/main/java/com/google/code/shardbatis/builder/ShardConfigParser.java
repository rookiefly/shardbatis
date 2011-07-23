/**
 * 
 */
package com.google.code.shardbatis.builder;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import com.google.code.shardbatis.strategy.ShardStrategy;

/**
 * @author sean.he
 * 
 */
public class ShardConfigParser {
	private static final Log log = LogFactory.getLog(ShardConfigParser.class);

	private static final String SHARD_CONFIG_DTD = "com/google/code/shardbatis/builder/shardbatis-config.dtd";
	private static final Map<String, String> DOC_TYPE_MAP = new HashMap<String, String>();
	static {
		DOC_TYPE_MAP.put(
				"http://shardbatis.googlecode.com/dtd/shardbatis-config.dtd"
						.toLowerCase(), SHARD_CONFIG_DTD);
		DOC_TYPE_MAP.put("-//shardbatis.googlecode.com//DTD Shardbatis 2.0//EN"
				.toLowerCase(), SHARD_CONFIG_DTD);
	}
	
	/**
	 * 解析xml配置文件并构建ShardConfigFactory
	 * @param input
	 * @return
	 * @throws Exception
	 */
	public ShardConfigHolder parse(InputStream input) throws Exception {
		final ShardConfigHolder configHolder = ShardConfigHolder
				.getInstance();

		SAXParserFactory spf = SAXParserFactory.newInstance();
		spf.setValidating(true);
		spf.setNamespaceAware(true);
		SAXParser parser = spf.newSAXParser();
		XMLReader reader = parser.getXMLReader();
		// 解析XML实现
		DefaultHandler handler = new DefaultHandler() {
			private String parentElement;
			public void startElement(String uri, String localName,
					String qName, Attributes attributes) throws SAXException {
				
				if ("strategy".equals(qName)) {// 解析<strategy/>节点
					// 解析<strategy tableName="xxx"/>
					String table = attributes.getValue("tableName");
					// 解析<strategy strategyClass="xxx"/>
					String className = attributes.getValue("strategyClass");
					try {
						Class<?> clazz = Class.forName(className);
						ShardStrategy strategy = (ShardStrategy) clazz
								.newInstance();
						configHolder.register(table, strategy);
					} catch (ClassNotFoundException e) {
						throw new SAXException(e);
					} catch (InstantiationException e) {
						throw new SAXException(e);
					} catch (IllegalAccessException e) {
						throw new SAXException(e);
					}

				}
				
				if("ignoreList".equals(qName)||"parseList".equals(qName)){
					parentElement=qName;
				}
				

			}
			public void characters (char ch[], int start, int length)throws SAXException{
				if("ignoreList".equals(parentElement)){
					configHolder.addIgnoreId(new String(ch, start, length).trim());
				}else if("parseList".equals(parentElement)){
					configHolder.addParseId(new String(ch, start, length).trim());
				}
				
			}
			public void error(SAXParseException e) throws SAXException {
				throw e;
			}

			public InputSource resolveEntity(String publicId, String systemId)
					throws IOException, SAXException {
				if (publicId != null)
					publicId = publicId.toLowerCase();
				if (systemId != null)
					systemId = systemId.toLowerCase();

				InputSource source = null;
				try {
					String path = DOC_TYPE_MAP.get(publicId);
					source = getInputSource(path, source);
					if (source == null) {
						path = DOC_TYPE_MAP.get(systemId);
						source = getInputSource(path, source);
					}
				} catch (Exception e) {
					throw new SAXException(e.toString());
				}
				return source;
			}
		};
		
		reader.setContentHandler(handler);
		reader.setEntityResolver(handler);
		reader.setErrorHandler(handler);
		reader.parse(new InputSource(input));

		return configHolder;
	}

	private InputSource getInputSource(String path, InputSource source) {
		if (path != null) {
			InputStream in = null;
			try {
				in = Resources.getResourceAsStream(path);
				source = new InputSource(in);
			} catch (IOException e) {
				log.warn(e.getMessage());
			}
		}
		return source;
	}

}
