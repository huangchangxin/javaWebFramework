package me.onlyxin.javaWebFramework.dao;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import me.onlyxin.javaWebFramework.utils.InstanceFactory;
import me.onlyxin.javaWebFramework.utils.PropsUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DaoHelper {

	//打印日志
	private static final Logger logger = LoggerFactory.getLogger(DaoHelper.class);
	
	private static final ThreadLocal<Connection> connContainer = new ThreadLocal<Connection>();
	
	private static final DataSourceFactory dataSourceFactory = InstanceFactory.getDataSourceFactory();
	
	private static final DataAccessor dataAccessor = InstanceFactory.getDataAccessor();
	
	public static DataSource getDataSource() {
		return dataSourceFactory.getDataSource();
	}
	public static Connection getConnection() {
		Connection conn;
		try {
			conn = connContainer.get();
			if (conn == null) {
				conn = getDataSource().getConnection();
				if (conn != null) {
					connContainer.set(conn);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("获取数据库连接出错", e);
			throw new RuntimeException(e);
		}
		return conn;
	}
	
	public static <T> T queryEntity(Class<T> entityClass, String sql, Object... params) {
		return dataAccessor.queryEntity(entityClass, sql, params);
	}
	
	public <T> List<T> queryEntityList(Class<T> entityClass, String sql,
			Object... params) {
		return dataAccessor.queryEntityList(entityClass, sql, params);
	}
	
	public <K, V> Map<K, V> queryEntityMap(Class<V> entiryClass, String sql,
			Object... params) {
		return dataAccessor.queryEntityMap(entiryClass, sql, params);
	}
	
	public Object[] queryArray(String sql, Object... params) {
		return dataAccessor.queryArray(sql, params);
	}
	
	public List<Object[]> queryArrayList(String sql, Object... params) {
		return dataAccessor.queryArrayList(sql, params);
	}
	
	public Map<String, Object> queryMap(String sql, Object... params) {
		return dataAccessor.queryMap(sql, params);
	}
	
	public List<Map<String, Object>> queryMapList(String sql, Object... params) {
		return dataAccessor.queryMapList(sql, params);
	}
	public <T> T queryColumn(String sql, Object... params) {
		return dataAccessor.queryColumn(sql, params);
	}
	public <T> List<T> queryColumnList(String sql, Object... params) {
		return dataAccessor.queryColumnList(sql, params);
	}
	
	public <T> Map<T, Map<String, Object>> queryColumnMap(String column,
			String sql, Object... params) {
		return dataAccessor.queryColumnMap(column, sql, params);
	}
	
	public int update(String sql, Object... params) {
		return dataAccessor.update(sql, params);
	}
	
	public long queryCount(String sql, Object... params) {
		return dataAccessor.queryCount(sql, params);
	}
}
