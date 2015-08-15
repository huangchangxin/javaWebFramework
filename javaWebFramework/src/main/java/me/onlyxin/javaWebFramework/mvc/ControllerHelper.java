package me.onlyxin.javaWebFramework.mvc;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import me.onlyxin.javaWebFramework.ioc.ClassHelper;
import me.onlyxin.javaWebFramework.utils.ArrayUtil;
import me.onlyxin.javaWebFramework.utils.CollectionUtil;
import me.onlyxin.javaWebFramework.utils.StringUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ControllerHelper {
	
	private static final Logger logger = LoggerFactory.getLogger(ControllerHelper.class);

	private static final Map<Requester, Handler> controllerMap = new LinkedHashMap<Requester, Handler>();
	
	static {
		List<Class<?>> ControllerClassList = ClassHelper.getClassListByAnnotation(Controller.class);
		if (CollectionUtil.isNotEmpty(ControllerClassList)) {
			Map<Requester, Handler> commonControllerMap = new HashMap<Requester, Handler>();
			Map<Requester, Handler> regexpControllerMap = new HashMap<Requester, Handler>();
			for (Class<?> controllerClass : ControllerClassList) {
				Method[] ControllerMethods = controllerClass.getDeclaredMethods();
				if (ArrayUtil.isNotEmpty(ControllerMethods)) {
					for (Method controllerMethod : ControllerMethods) {
						handleControllerMethod(controllerClass, controllerMethod, commonControllerMap, regexpControllerMap);
					}
				}
			}
		}
		
	}

	private static void handleControllerMethod(Class<?> controllerClass,
			Method controllerMethod,
			Map<Requester, Handler> commonControllerMap,
			Map<Requester, Handler> regexpControllerMap) {
		// TODO Auto-generated method stub
		if (controllerMethod.isAnnotationPresent(Request.Get.class)) {
			String requestPath = controllerMethod.getAnnotation(Request.Get.class).value();
			putControllerMap("GET", requestPath, controllerClass, controllerMethod, commonControllerMap, regexpControllerMap);
		} else if (controllerMethod.isAnnotationPresent(Request.Post.class)) {
			String requestPath = controllerMethod.getAnnotation(Request.Post.class).value();
			putControllerMap("POST", requestPath, controllerClass, controllerMethod, commonControllerMap, regexpControllerMap);
		} else if (controllerMethod.isAnnotationPresent(Request.Put.class)) {
			String requestPath = controllerMethod.getAnnotation(Request.Put.class).value();
			putControllerMap("PUT", requestPath, controllerClass, controllerMethod, commonControllerMap, regexpControllerMap);
		} else if (controllerMethod.isAnnotationPresent(Request.Post.class)) {
			String requestPath = controllerMethod.getAnnotation(Request.Delete.class).value();
			putControllerMap("DELETE", requestPath, controllerClass, controllerMethod, commonControllerMap, regexpControllerMap);
		}
	}

	private static void putControllerMap(String requestMethod, String requestPath,
			Class<?> controllerClass, Method controllerMethod,
			Map<Requester, Handler> commonControllerMap,
			Map<Requester, Handler> regexpControllerMap) {
		// TODO Auto-generated method stub
		if (requestPath.matches(".+\\{\\w+\\}.*")) {
			requestPath = StringUtil.replaceAll(requestPath, "\\{\\w+\\}", "(\\\\w+)");
			regexpControllerMap.put(new Requester(requestMethod, requestPath), new Handler(controllerClass, controllerMethod));
		} else {
			commonControllerMap.put(new Requester(requestMethod, requestPath), new Handler(controllerClass, controllerMethod));
		}
	}
	
	public static Map<Requester, Handler> getControllerMap() {
		return controllerMap;
	}
}
