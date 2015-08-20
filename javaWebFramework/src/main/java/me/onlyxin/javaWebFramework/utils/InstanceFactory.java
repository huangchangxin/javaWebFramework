package me.onlyxin.javaWebFramework.utils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import me.onlyxin.javaWebFramework.classScanner.ClassScanner;
import me.onlyxin.javaWebFramework.classScanner.DefaultClassScanner;
import me.onlyxin.javaWebFramework.configuration.ConfigHelper;
import me.onlyxin.javaWebFramework.dao.DataAccessor;
import me.onlyxin.javaWebFramework.dao.DataSourceFactory;
import me.onlyxin.javaWebFramework.dao.DefaultDataAccessor;
import me.onlyxin.javaWebFramework.dao.DefaultDataSourceFactory;
import me.onlyxin.javaWebFramework.mvc.DefaultHandlerExceptionResolver;
import me.onlyxin.javaWebFramework.mvc.DefaultHandlerInvoker;
import me.onlyxin.javaWebFramework.mvc.DefaultHandlerMapping;
import me.onlyxin.javaWebFramework.mvc.DefaultViewResolver;
import me.onlyxin.javaWebFramework.mvc.HandlerExceptionResolver;
import me.onlyxin.javaWebFramework.mvc.HandlerInvoker;
import me.onlyxin.javaWebFramework.mvc.HandlerMapping;
import me.onlyxin.javaWebFramework.mvc.ViewResolver;

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
	//处理器映射器
	private static final String HANDLER_MAPPING = "handler_mapping";
	//处理器适配器
	private static final String HANDLER_INVOKER = "handler_invoker";
	//处理器适配器
	private static final String VIEW_RESOLVER = "view_resolver";
	//异常处理器
	private static final String HANDLER_EXCEPTION_RESOLVER = "handler_exception_resolver";
	//异常处理器
	private static final String DATASOURCE_FACTORY = "datasource_factory";
	//数据访问器
	private static final String DATA_ACCESSOR = "data_accessor";
	

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
	
	public static HandlerMapping getHandlerMapping() {
		return getInstance(HANDLER_MAPPING, DefaultHandlerMapping.class);
	}
	
	public static HandlerInvoker getHandlerInvoker() {
		return getInstance(HANDLER_INVOKER, DefaultHandlerInvoker.class);
	}

	public static ViewResolver getViewResolver() {
		// TODO Auto-generated method stub
		return getInstance(VIEW_RESOLVER, DefaultViewResolver.class);
	}

	public static HandlerExceptionResolver getHandlerExceptionResolver() {
		// TODO Auto-generated method stub
		return getInstance(HANDLER_EXCEPTION_RESOLVER, DefaultHandlerExceptionResolver.class);
	}
	
	public static DataSourceFactory getDataSourceFactory() {
		return getInstance(DATASOURCE_FACTORY, DefaultDataSourceFactory.class);
	}
	
	public static DataAccessor getDataAccessor() {
		return getInstance(DATA_ACCESSOR, DefaultDataAccessor.class);
	}
}
