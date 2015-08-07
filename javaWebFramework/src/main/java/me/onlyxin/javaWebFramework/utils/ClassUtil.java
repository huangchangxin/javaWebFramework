package me.onlyxin.javaWebFramework.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClassUtil {

	// 打印日志
	private static final Logger logger = LoggerFactory.getLogger(ClassUtil.class);
	
	public static ClassLoader getClassLoader() {
		return Thread.currentThread().getContextClassLoader();
	}
}
