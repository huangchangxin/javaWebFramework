package me.onlyxin.javaWebFramework.ioc;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import me.onlyxin.javaWebFramework.utils.InstanceFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//ioc容器
public class BeanHelper {

	// 打印日志
	private static final Logger logger = LoggerFactory.getLogger(BeanHelper.class);
		
	//缓存实例
	private static final Map<Class<?>, Object> beanMap = new ConcurrentHashMap<Class<?>, Object>();
	
	static {
		List<Class<?>> classList = ClassHelper.getClassList();
		for (Class<?> clazz : classList) {
			if (clazz.isAnnotationPresent(Component.class)) {
				try {
					Object instance = clazz.newInstance();
					beanMap.put(clazz, instance);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					logger.error("实例化对象出错");
					throw new RuntimeException(e);
				} 
				
			}
		}
	}
	//根据类名获取实例
	@SuppressWarnings("unchecked")
	public static <T> T getInstance(Class<T> clazzKey) {
		if (!beanMap.containsKey(clazzKey)) {
			throw new RuntimeException("类名有错！！");
		}
		return (T) beanMap.get(clazzKey);
	}
	
	public static Map<Class<?>, Object> getBeanMap() {
		return beanMap;
	}
}
