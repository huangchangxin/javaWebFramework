package me.onlyxin.javaWebFramework.orm;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.onlyxin.javaWebFramework.ioc.ClassHelper;
import me.onlyxin.javaWebFramework.utils.ArrayUtil;
import me.onlyxin.javaWebFramework.utils.StringUtil;

public class EntityHelper {

	//类名与表名之间的映射
	private static final Map<Class<?>, String> entityClassTableNameMap = new HashMap<Class<?>, String>();
	//字段名与列名单映射
	private static final Map<Class<?>, Map<String, String>> entityClassFieldMapMap = new HashMap<Class<?>, Map<String, String>>();
	
	static {
		List<Class<?>> entityClassList = ClassHelper.getClassListByAnnotation(Entity.class);
		for (Class<?> entityClass : entityClassList) {
			initEntityTableName(entityClass);
			initEntityFiledMapMap(entityClass);
		}
	}

	private static void initEntityTableName(Class<?> entityClass) {
		// TODO Auto-generated method stub
		String tableName;
		if (entityClass.isAnnotationPresent(Table.class)) {
			tableName = entityClass.getAnnotation(Table.class).value();
		} else {
			tableName = StringUtil.camelhumpToUnderline(entityClass.getSimpleName());
		}
		entityClassTableNameMap.put(entityClass, tableName);
	}

	private static void initEntityFiledMapMap(Class<?> entityClass) {
		// TODO Auto-generated method stub
		Field[] declaredFields = entityClass.getDeclaredFields();
		if (ArrayUtil.isNotEmpty(declaredFields)) {
			Map<String,String> filedMap = new HashMap<String, String>();
			for (Field field : declaredFields) {
				String fileName = field.getName();
				String columnName;
				if (field.isAnnotationPresent(Column.class)) {
					columnName = field.getAnnotation(Column.class).value();
				} else {
					columnName = StringUtil.camelhumpToUnderline(fileName);
				}
				filedMap.put(fileName, columnName);
			}
			entityClassFieldMapMap.put(entityClass, filedMap);
		}
	}
	
	public static String getTableName(Class<?> entityClass) {
		return entityClassTableNameMap.get(entityClass);
	}
	
	public static String getColumnName(Class<?> entityClass, String fieldName) {
		String columnName = entityClassFieldMapMap.get(entityClass).get(fieldName);
		return StringUtil.isNotEmpty(columnName) ? columnName : fieldName;
	}
	
}
