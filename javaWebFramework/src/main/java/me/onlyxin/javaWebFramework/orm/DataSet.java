package me.onlyxin.javaWebFramework.orm;

import java.util.List;
import java.util.Map;

import me.onlyxin.javaWebFramework.dao.DaoHelper;
import me.onlyxin.javaWebFramework.utils.MapUtil;
import me.onlyxin.javaWebFramework.utils.ObjectUtil;
//提供与实体相关的数据库操作
public class DataSet {

	public static <T> T select(Class<T> entityClass, String condition, Object... params) {
		String sql = SqlHelper.generateSelectSql(entityClass, condition, "");
		return DaoHelper.queryEntity(entityClass, sql, params);
	}
	
	public static <T> List<T> selectList(Class<T> entityClass) {
		return selectListWithConditionAndSort(entityClass, "", "");
	}
	
	public static <T> List<T> selectListWithConditionAndSort(Class<T> entityClass, String condition, String sort, Object... params) {
		String sql = SqlHelper.generateSelectSql(entityClass, condition, sort);
		return DaoHelper.queryEntityList(entityClass, sql, params);
	}
	//查询（带条件）
	public static <T> List<T> selectListWithCondition(Class<T> entityClass, String condition, Object... params) {
		return selectListWithConditionAndSort(entityClass, condition, "", params);
	}
	//查询（带排序）
	public static <T> List<T> selectListWithSort(Class<T> entityClass, String sort) {
		return selectListWithConditionAndSort(entityClass, "", sort, "");
	}
	//查询记录数
	public static long selectCount(Class<?> entityClass, String condition, Object... params) {
		String sql = SqlHelper.generateSelectSqlForCount(entityClass, condition);
		return DaoHelper.queryCount(sql, params);
	}
	//根据列名查询单条记录
	public static <T> T selectColumn(Class<?> entityClass, String columnName, String condition, Object... params) {
		String sql = SqlHelper.generateSelectSql(entityClass, condition, "");
		sql = sql.replace("*", columnName);
		return DaoHelper.queryColumn(sql, params);
	}
	//根据列名查询多条记录
	public static <T> List<T> selectColumnList(Class<?> entityClass, String columnName, String condition, String sort, Object... params) {
		String sql = SqlHelper.generateSelectSql(entityClass, condition, sort);
		sql = sql.replace("*", columnName);
		return DaoHelper.queryColumnList(sql, params);
	}
	//插入属性集合
	public static boolean insert(Class<?> entityClass, Map<String, Object> filedMap) {
		if (MapUtil.isEmpty(filedMap)) {
			return true;
		}
		String sql = SqlHelper.generateInsertSql(entityClass, filedMap.keySet());
		int updateRows = DaoHelper.update(sql, filedMap.values());
		return updateRows > 0;
	}
	//插入一个实体
	public static boolean insert(Object entity) {
		Class<? extends Object> entityClass = entity.getClass();
		Map<String, Object> fieldMap = ObjectUtil.getFieldMap(entity);
		return insert(entityClass, fieldMap);
	}
	public static boolean upadate(Class<?> entityClass, Map<String, Object> filedMap, String condition, Object... params) {
		if (MapUtil.isEmpty(filedMap)) {
			return true;
		}
		String sql = SqlHelper.generateUpadateSql(entityClass, filedMap, condition);
		return DaoHelper.update(sql, params) > 0;
	}
	//根据主键更新记录
	public static boolean upadate(Object entityObject, String pkName) {
		Class<? extends Object> entityClass = entityObject.getClass();
		Map<String, Object> fieldMap = ObjectUtil.getFieldMap(entityObject);
		String condition = pkName + " = ?";
		Object[] params = {ObjectUtil.getFieldValue(entityObject, pkName)};
		return upadate(entityClass, fieldMap, condition, params);
	}
	
	public static boolean delete(Class<?> entityClass, String condition, Object... params) {
		String sql = SqlHelper.generateDeleteSql(entityClass, condition);
		return DaoHelper.update(sql, params) > 0;
	}
	//根据主键删除记录
	public static boolean delete(Object entityObject, String pkName) {
		Class<? extends Object> entityClass = entityObject.getClass();
		Map<String, Object> fieldMap = ObjectUtil.getFieldMap(entityObject);
		String condition = pkName + " = ?";
		Object[] params = {ObjectUtil.getFieldValue(entityObject, pkName)};
		return upadate(entityClass, fieldMap, condition, params);
	}
}
