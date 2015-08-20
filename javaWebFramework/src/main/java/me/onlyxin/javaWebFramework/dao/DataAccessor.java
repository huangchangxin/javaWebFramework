package me.onlyxin.javaWebFramework.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
//数据访问器
public interface DataAccessor {

	<T> T queryEntity(Class<T> entiryClass, String sql, Object... params);
	
	<T> List<T> queryEntityList(Class<T> entiryClass, String sql, Object... params);
	
	<K, V> Map<K, V> queryEntityMap(Class<V> entiryClass, String sql, Object... params);
	
	Object[] queryArray(String sql, Object... params);
	
	List<Object[]> queryArrayList(String sql, Object... params);
	
	Map<String, Object> queryMap(String sql, Object... params);
	
	List<Map<String, Object>> queryMapList(String sql, Object... params);
	
	<T> T queryColumn(String sql, Object... params);
	
	<T> List<T> queryColumnList(String sql, Object... params);
	
	<T> Map<T, Map<String, Object>> queryColumnMap(String column, String sql, Object... params);
	
	long queryCount(String sql, Object... params);
	
	int update(String sql, Object... params);
	
}
