package me.onlyxin.javaWebFramework.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClassUtil {

	// 打印日志
	private static final Logger logger = LoggerFactory.getLogger(ClassUtil.class);
	
	public static ClassLoader getClassLoader() {
		return Thread.currentThread().getContextClassLoader();
	}

	public static Class<?> loadClass(String className, boolean isInitialized) {
		// TODO Auto-generated method stub
		Class<?> clazz = null;
		try {
			clazz = Class.forName(className, isInitialized, getClassLoader());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			logger.error("类加载出错");
			throw new RuntimeException(e);
		}
		return clazz;
	}
	
	public static boolean isInt(Class<?> type) {
		return type.equals(int.class) || type.equals(Integer.class);
	}
	
	public static boolean isLong(Class<?> type) {
		return type.equals(long.class) || type.equals(Long.class);
	}
	
	public static boolean isDouble(Class<?> type) {
		return type.equals(double.class) || type.equals(Double.class);
	}
	
	public static boolean isString(Class<?> type) {
		return type.equals(String.class);
	}
}
