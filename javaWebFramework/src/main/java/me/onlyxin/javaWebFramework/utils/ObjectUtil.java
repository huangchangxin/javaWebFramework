package me.onlyxin.javaWebFramework.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
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
	public static Object getFieldValue(Object obj, String fieldName) {
		Object propertyValue = null;
		try {
			if (PropertyUtils.isReadable(obj, fieldName)) {
				propertyValue = PropertyUtils.getProperty(obj, fieldName);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException("获取成员变量值出错");
		}
		return propertyValue;
	}
	public static Map<String, Object> getFieldMap(Object obj) {
		Map<String,Object> fieldMap = new LinkedHashMap<String, Object>();
		Field[] declaredFields = obj.getClass().getDeclaredFields();
		for (Field field : declaredFields) {
			if (Modifier.isStatic(field.getModifiers())) {
				continue;
			}
			String fieldName = field.getName();
			Object fieldValue = ObjectUtil.getFieldValue(obj, fieldName);
			fieldMap.put(fieldName, fieldValue);
		}
		return fieldMap;
	}
}
