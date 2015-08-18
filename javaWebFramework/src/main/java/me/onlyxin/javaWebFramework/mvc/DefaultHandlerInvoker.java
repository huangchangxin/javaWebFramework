package me.onlyxin.javaWebFramework.mvc;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.onlyxin.javaWebFramework.ioc.BeanHelper;
import me.onlyxin.javaWebFramework.utils.ArrayUtil;
import me.onlyxin.javaWebFramework.utils.ClassUtil;
import me.onlyxin.javaWebFramework.utils.CodeUtil;
import me.onlyxin.javaWebFramework.utils.InstanceFactory;
import me.onlyxin.javaWebFramework.utils.MapUtil;
import me.onlyxin.javaWebFramework.utils.StreamUtil;
import me.onlyxin.javaWebFramework.utils.StringUtil;

public class DefaultHandlerInvoker implements HandlerInvoker {

	private static final Logger logger = LoggerFactory.getLogger(DefaultHandlerInvoker.class);
	private ViewResolver viewResolver = InstanceFactory.getViewResolver();
	public void invokeHandler(HttpServletRequest request,
			HttpServletResponse response, Handler handler) throws Exception {
		// TODO Auto-generated method stub
		Class<?> handlerClass = handler.getHandlerClass();
		Method handlerMethod = handler.getHandlerMethod();
		Handler handlerInstance = BeanHelper.getBean(handlerClass);
		List<Object> handlerMethodParamList = createHandlerMethodParamList(request, handlerInstance);
		checkParamList(handlerMethod, handlerMethodParamList);
		Object actionMethodResult = invokeHandlerMethod(handlerInstance, handlerMethod, handlerMethodParamList);
		viewResolver.resolveView(request, response, actionMethodResult);
	}
	private Object invokeHandlerMethod(Handler handlerInstance,
			Method handlerMethod, List<Object> handlerMethodParamList) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		// TODO Auto-generated method stub
		handlerMethod.setAccessible(true);
		return handlerMethod.invoke(handlerInstance, handlerMethodParamList.toArray());
	}
	private void checkParamList(Method handlerMethod,
			List<Object> handlerMethodParamList) {
		// TODO Auto-generated method stub
		Class<?>[] handlerMethodParameterTypes = handlerMethod.getParameterTypes();
		if (handlerMethodParameterTypes.length != handlerMethodParamList.size()) {
			logger.error("传入参数与目标参数不匹配");
			throw new RuntimeException("传入参数与目标参数不匹配");
		}
	}
	private List<Object> createHandlerMethodParamList(
			HttpServletRequest request, Handler handlerInstance) {
		// TODO Auto-generated method stub
		List<Object> handlerMethodParamList = new ArrayList<Object>();
		Method handlerMethod = handlerInstance.getHandlerMethod();
		Class<?>[] parameterTypes = handlerMethod.getParameterTypes();
		handlerMethodParamList.addAll(createPathParamList(handlerInstance.getRequestPathMatcher(), parameterTypes));
		Map<String, Object> createRequestParamMap = createRequestParamMap(request);
		if (MapUtil.isNotEmpty(createRequestParamMap)) {
			handlerMethodParamList.add(new Params(createRequestParamMap));
		}
		return handlerMethodParamList;
	}
	private Map<String, Object> createRequestParamMap(HttpServletRequest request) {
		// TODO Auto-generated method stub
		Map<String,Object> requestParamMap = new LinkedHashMap<String, Object>();
		try {
			String method = request.getMethod();
			
			if (method.equalsIgnoreCase("put") || method.equalsIgnoreCase("delete")) {
				String queryString = CodeUtil.decodeURL(StreamUtil.getString(request.getInputStream()));
				if (StringUtil.isNotEmpty(queryString)) {
					String[] qsArray = StringUtil.splitString(queryString, "&");
					if (ArrayUtil.isNotEmpty(qsArray)) {
						for (String qs : qsArray) {
							String[] array = StringUtil.splitString(qs, "=");
							if (ArrayUtil.isNotEmpty(array)) {
								String paramName = array[0];
								String paramValue = array[1];
								if (requestParamMap.containsKey(paramName)) {
									paramValue = requestParamMap.get(paramName) + " " + paramValue;
								}
								requestParamMap.put(paramName, paramValue);
							}
						}
					}
				} else {
					Enumeration<String> parameterNames = request.getParameterNames();
					while (parameterNames.hasMoreElements()) {
						String parameterName = parameterNames.nextElement();
						String[] parameterValues = request.getParameterValues(parameterName);
						if (ArrayUtil.isNotEmpty(parameterValues)) {
							if (parameterValues.length == 1) {
								requestParamMap.put(parameterName, parameterValues[0]);
							} else {
								StringBuffer paramValue = new StringBuffer("");
								for (int i = 0; i < parameterValues.length; i++) {
									paramValue.append(parameterValues[i] + " ");
								}
								paramValue.substring(0, paramValue.length()-1);
								requestParamMap.put(parameterName, paramValue);
							}
						}
					}
				}
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("获取请求参数出错", e);
			throw new RuntimeException(e);
		}
		return requestParamMap;
	}
	private Collection<? extends Object> createPathParamList(
			Matcher requestPathMatcher, Class<?>[] parameterTypes) {
		// TODO Auto-generated method stub
		List<Object> handlerMethodParamList = new ArrayList<Object>();
		for (int i = 0; i <= requestPathMatcher.groupCount(); i++) {
			String param = requestPathMatcher.group(i);
			Class<?> paramType = parameterTypes[i-1];
			if (ClassUtil.isInt(paramType)) {
				handlerMethodParamList.add(Integer.parseInt(String.valueOf(paramType)));
			} else if (ClassUtil.isLong(paramType)) {
				handlerMethodParamList.add(Long.parseLong(String.valueOf(paramType)));
			} else if (ClassUtil.isDouble(paramType)) {
				handlerMethodParamList.add(Double.parseDouble(String.valueOf(paramType)));
			} else if (ClassUtil.isString(paramType)) {
				handlerMethodParamList.add(paramType);
			}
		}
		return handlerMethodParamList;
	}

}
