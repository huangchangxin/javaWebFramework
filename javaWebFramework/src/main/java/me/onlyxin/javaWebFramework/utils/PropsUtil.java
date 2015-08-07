package me.onlyxin.javaWebFramework.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PropsUtil {
	//打印日志
	private static final Logger logger = LoggerFactory.getLogger(PropsUtil.class);
	
	//加载属性文件
	public static Properties loadProps(String propsPath) {
		if (StringUtil.isEmpty(propsPath)) {
			logger.error("属性文件路径不能为空");
			throw new IllegalArgumentException();
		}
		Properties properties = new Properties();
		InputStream in = null;
		in = ClassUtil.getClassLoader().getResourceAsStream(propsPath);
		try {
			properties.load(in);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("属性文件加载出错");
			throw new RuntimeException(e);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					logger.error("释放资源出错");
					throw new RuntimeException(e);
				} finally {
					in = null;
				}
			}
		}
		return properties;
	}
	//获取字符型属性值
	public static String getString(Properties prop, String key) {
		String value = null;
		if (prop.containsKey(key)) {
			value = prop.getProperty(key);
		}
		return value;
	}
	
}
