package me.onlyxin.javaWebFramework.utils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import me.onlyxin.javaWebFramework.classScanner.ClassScanner;
import me.onlyxin.javaWebFramework.classScanner.DefaultClassScanner;
import me.onlyxin.javaWebFramework.configuration.ConfigHelper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//实例工厂
public class InstanceFactory {
	
	// 打印日志
	private static final Logger logger = LoggerFactory.getLogger(InstanceFactory.class);
	
	//缓存实例
	private static final Map<String, Object> cache = new ConcurrentHashMap<String, Object>();
	
	//类扫描器
	private static final String CLASS_SCANNER = "class_scanner";

	//创建实例
	@SuppressWarnings("unchecked")
	public static <T> T getInstance(String cacheKey, Class<T> defaultImplClass) {
		if (cache.containsKey(cacheKey)) {
			return (T) cache.get(cacheKey);
		}
		String implClassName = ConfigHelper.getString(cacheKey);
		if (StringUtil.isEmpty(implClassName)) {
			implClassName = defaultImplClass.getName();
		}
		T newInstance = ObjectUtil.newInstance(implClassName);
		if (newInstance != null) {
			cache.put(cacheKey, newInstance);
		}
		return newInstance;
	}
	
	public static ClassScanner getClassScanner() {
		return getInstance(CLASS_SCANNER, DefaultClassScanner.class);
	}
	
}
