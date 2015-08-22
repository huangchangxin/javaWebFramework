package me.onlyxin.javaWebFramework.orm;

import java.util.Collection;
import java.util.Map;
import java.util.Properties;

import me.onlyxin.javaWebFramework.configuration.Constant;
import me.onlyxin.javaWebFramework.utils.CollectionUtil;
import me.onlyxin.javaWebFramework.utils.MapUtil;
import me.onlyxin.javaWebFramework.utils.PropsUtil;
import me.onlyxin.javaWebFramework.utils.StringUtil;
//生成sql,协助实现orm
public class SqlHelper {

	private static final Properties sqlProps = PropsUtil.loadProps(Constant.SQL_PROPS);
	
	public static String getSql(String key) {
		String sql;
		if (sqlProps.containsKey(key)) {
			sql = sqlProps.getProperty(key);
		} else {
			throw new RuntimeException("找不到对应的sql");
		}
		return sql;
	}
	//select语句
	public static String generateSelectSql(Class<?> entityClass, String condition, String sort) {
		StringBuilder sql = new StringBuilder("select * from ").append(getTable(entityClass));
		sql.append(generateWhere(condition));
		sql.append(generateOrder(sort));
		return sql.toString();
	}
	//insert语句
	public static String generateInsertSql(Class<?> entityClass, Collection<String> fileNames) {
		StringBuilder sql = new StringBuilder("insert into ").append(getTable(entityClass));
		if (CollectionUtil.isNotEmpty(fileNames)) {
			int i = 0;
			StringBuilder columns = new StringBuilder(" ");
			StringBuilder values = new StringBuilder(" values ");
			for (String fieldName : fileNames) {
				String columnName = EntityHelper.getColumnName(entityClass, fieldName);
				if (i == 0) {
					columns.append("(").append(columnName);
					values.append("(?");
				} else {
					columns.append(", ").append(columnName);
					values.append(", ?");
				}
				if (i == fileNames.size() - 1) {
					columns.append(")");
					values.append(")");
				}
				i++;
			}
			sql.append(columns).append(values);
		}
		return sql.toString();
	}
	//delete语句
	public static String generateDeleteSql(Class<?> entityClass, String condition) {
		StringBuilder sql = new StringBuilder("delete from ").append(getTable(entityClass));
		sql.append(generateWhere(condition));
		return sql.toString();
	}
	//生成update语句
	public static String generateUpadateSql(Class<?> entityClass, Map<String, Object> filedMap, String condition) {
		StringBuilder sql = new StringBuilder("update ").append(getTable(entityClass));
		if (MapUtil.isNotEmpty(filedMap)) {
			sql.append(" set ");
			int i = 0;
			for (Map.Entry<String, Object> fieldEntry : filedMap.entrySet()) {
				String fieldName = fieldEntry.getKey();
				String columnName = EntityHelper.getColumnName(entityClass, fieldName);
				if (i == 0) {
					sql.append(columnName).append(" = ?");
				} else {
					sql.append(", ").append(columnName).append(" = ?");
				}
				i++;
			}
		}
		sql.append(generateWhere(condition));
		return sql.toString();
	}
	//delete语句
	public static String generateOrder(String sort) {
		// TODO Auto-generated method stub
		String order = "";
		if (StringUtil.isNotEmpty(sort)) {
			order += " order by " + sort;
		}
		return sort;
	}
	//select count(*)
	public static String generateSelectSqlForCount(Class<?> entityClass, String condition) {
		StringBuilder sql = new StringBuilder("select count(*) from ").append(getTable(entityClass));
        sql.append(generateWhere(condition));
        return sql.toString();
	}

	private static Object generateWhere(String condition) {
		// TODO Auto-generated method stub
		String where = "";
		if (StringUtil.isNotEmpty(condition)) {
			where += " where " + condition;
		}
		return where;
	}

	private static String getTable(Class<?> entityClass) {
		return EntityHelper.getTableName(entityClass);
	}
}
