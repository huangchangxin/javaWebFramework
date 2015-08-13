package me.onlyxin.javaWebFramework.aop;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

import me.onlyxin.javaWebFramework.classScanner.ClassScanner;
import me.onlyxin.javaWebFramework.ioc.BeanHelper;
import me.onlyxin.javaWebFramework.ioc.ClassHelper;
import me.onlyxin.javaWebFramework.utils.ClassUtil;
import me.onlyxin.javaWebFramework.utils.InstanceFactory;
import me.onlyxin.javaWebFramework.utils.StringUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AopHelper {

	// 打印日志
	private static final Logger logger = LoggerFactory
			.getLogger(AopHelper.class);
	private static final ClassScanner classScanner = InstanceFactory
			.getClassScanner();
	static {
		try {
			Map<Class<?>, List<Class<?>>> proxyTargetMap = createProxyTargetMap();
			Map<Class<?>, List<Proxy>> targetProxyMap = createTargetProxyMap(proxyTargetMap);
			for (Map.Entry<Class<?>, List<Proxy>> targetEntry : targetProxyMap
					.entrySet()) {
				Class<?> targetClass = targetEntry.getKey();
				List<Proxy> proxyList = targetEntry.getValue();
				Object proxyInstance = ProxyHelper.createProxyInstance(targetClass,
						proxyList);
				BeanHelper.setBean(targetClass, proxyList);
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("初始化aop出错");
			throw new RuntimeException(e);
		}
	}
	//创建代理类与目标类的对应关系
	private static Map<Class<?>, List<Class<?>>> createProxyTargetMap() {
		// TODO Auto-generated method stub
		LinkedHashMap<Class<?>, List<Class<?>>> proxyTargetMap = new LinkedHashMap<Class<?>, List<Class<?>>>();
		//添加切面代理
		addAspectProxy(proxyTargetMap);
		return proxyTargetMap;
	}
	//创建目标类与代理类的对应关系
	private static Map<Class<?>, List<Proxy>> createTargetProxyMap(
			Map<Class<?>, List<Class<?>>> proxyTargetMap) throws Exception {
		// TODO Auto-generated method stub
		Map<Class<?>, List<Proxy>> targetProxyMap = new HashMap<Class<?>, List<Proxy>>();
		for (Map.Entry<Class<?>, List<Class<?>>> proxyTargetEntry : proxyTargetMap
				.entrySet()) {
			Class<?> proxyClass = proxyTargetEntry.getKey();
			List<Class<?>> targetClassList = proxyTargetEntry.getValue();
			for (Class<?> targetClass : targetClassList) {
				Proxy aspect = (Proxy) proxyClass.newInstance();
				if (targetProxyMap.containsKey(targetClass)) {
					targetProxyMap.get(targetClass).add(aspect);
				} else {
					List<Proxy> aspectList = new ArrayList<Proxy>();
					aspectList.add(aspect);
					targetProxyMap.put(targetClass, aspectList);
				}
			}
		}
		return targetProxyMap;
	}

	private static void addAspectProxy(
			LinkedHashMap<Class<?>, List<Class<?>>> proxyTargetMap) {
		// TODO Auto-generated method stub
		List<Class<?>> aspectProxyClassList = ClassHelper
				.getClassListBySuper(AspectProxy.class);
		sortAspectProxyClassList(aspectProxyClassList);
		for (Class<?> aspectProxyClass : aspectProxyClassList) {
			if (aspectProxyClass.isAnnotationPresent(Aspect.class)) {
				Aspect aspect = aspectProxyClass.getAnnotation(Aspect.class);
				List<Class<?>> targetClassList = createTargetClassList(aspect);
				proxyTargetMap.put(aspectProxyClass, targetClassList);
			}
		}
	}

	private static List<Class<?>> createTargetClassList(Aspect aspect) {
		// TODO Auto-generated method stub
		List<Class<?>> targetClassList = new ArrayList<Class<?>>();
		String pkg = aspect.pkg();
		String cls = aspect.cls();
		Class<? extends Annotation> annotation = aspect.annotation();
		if (StringUtil.isNotEmpty(pkg)) {
			if (StringUtil.isNotEmpty(cls)) {
				targetClassList
						.add(ClassUtil.loadClass(pkg + "." + cls, false));
			} else {
				if (annotation != null && !annotation.equals(Aspect.class)) {
					targetClassList.addAll(classScanner
							.getClassListByAnnotation(pkg, annotation));
				} else {
					targetClassList.addAll(classScanner.getClassList(pkg));
				}
			}
		} else {
			if (annotation != null && !annotation.equals(Aspect.class)) {
				targetClassList.addAll(ClassHelper
						.getClassListByAnnotation(annotation));
			}
		}
		return targetClassList;
	}

	private static void sortAspectProxyClassList(
			List<Class<?>> aspectProxyClassList) {
		// TODO Auto-generated method stub
		Collections.sort(aspectProxyClassList, new Comparator<Class<?>>() {

			public int compare(Class<?> aspect1, Class<?> aspect2) {
				// TODO Auto-generated method stub
				if (aspect1.isAnnotationPresent(AspectOrder.class)
						|| aspect2.isAnnotationPresent(AspectOrder.class)) {
					if (aspect1.isAnnotationPresent(AspectOrder.class)) {
						return getOrderValue(aspect1) - getOrderValue(aspect2);
					} else {
						return getOrderValue(aspect2) - getOrderValue(aspect1);
					}
				} else {
					return aspect1.hashCode() - aspect2.hashCode();
				}
			}

			private int getOrderValue(Class<?> aspect) {
				// TODO Auto-generated method stub
				return (aspect.getAnnotation(AspectOrder.class) != null ? aspect
						.getAnnotation(AspectOrder.class).value() : 0);
			}

		});
	}
}
