package me.onlyxin.javaWebFramework.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ObjectUtil {

	// 打印日志
	private static final Logger logger = LoggerFactory.getLogger(InstanceFactory.class);
	
	@SuppressWarnings("unchecked")
	public static <T> T newInstance(String className) {
		T instance;
		try {
			Class<?> clazz = Class.forName(className);
			instance =  (T) clazz.newInstance();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("实例化对象出错");
			throw new RuntimeException(e);
		}
		return instance;
	}
}
