package me.onlyxin.javaWebFramework.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import me.onlyxin.javaWebFramework.utils.PropsUtil;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayHandler;
import org.apache.commons.dbutils.handlers.ArrayListHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.BeanMapHandler;
import org.apache.commons.dbutils.handlers.ColumnListHandler;
import org.apache.commons.dbutils.handlers.KeyedHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultDataAccessor implements DataAccessor {

	//打印日志
	private static final Logger logger = LoggerFactory.getLogger(DefaultDataAccessor.class);
	
	private final QueryRunner queryRunner;
	
	public DefaultDataAccessor() {
		DataSource ds = DaoHelper.getDataSource();
		queryRunner = new QueryRunner(ds);
	}
	public <T> T queryEntity(Class<T> entiryClass, String sql, Object... params) {
		// TODO Auto-generated method stub
		try {
			return queryRunner.query(sql, new BeanHandler<T>(entiryClass), params);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.error("查询单个实体出错", e);
			throw new RuntimeException(e);
		}
	}

	public <T> List<T> queryEntityList(Class<T> entiryClass, String sql,
			Object... params) {
		// TODO Auto-generated method stub
		try {
			return queryRunner.query(sql, new BeanListHandler<T>(entiryClass), params);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.error("查询单个实体出错", e);
			throw new RuntimeException(e);
		}
	}

	public <K, V> Map<K, V> queryEntityMap(Class<V> entiryClass, String sql,
			Object... params) {
		// TODO Auto-generated method stub
		try {
			return queryRunner.query(sql, new BeanMapHandler<K, V>(entiryClass), params);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.error("查询单个实体出错", e);
			throw new RuntimeException(e);
		}
	}

	public Object[] queryArray(String sql, Object... params) {
		// TODO Auto-generated method stub
		try {
			return queryRunner.query(sql, new ArrayHandler(), params);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.error("查询单个实体出错", e);
			throw new RuntimeException(e);
		}
	}

	public List<Object[]> queryArrayList(String sql, Object... params) {
		// TODO Auto-generated method stub
		try {
			return queryRunner.query(sql, new ArrayListHandler(), params);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.error("查询单个实体出错", e);
			throw new RuntimeException(e);
		}
	}

	public Map<String, Object> queryMap(String sql, Object... params) {
		// TODO Auto-generated method stub
		try {
			return queryRunner.query(sql, new MapHandler(), params);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.error("查询单个实体出错", e);
			throw new RuntimeException(e);
		}
	}

	public List<Map<String, Object>> queryMapList(String sql, Object... params) {
		// TODO Auto-generated method stub
		try {
			return queryRunner.query(sql, new MapListHandler(), params);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.error("查询单个实体出错", e);
			throw new RuntimeException(e);
		}
	}

	public <T> T queryColumn(String sql, Object... params) {
		// TODO Auto-generated method stub
		try {
			return queryRunner.query(sql, new ScalarHandler<T>(), params);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.error("查询单个实体出错", e);
			throw new RuntimeException(e);
		}
	}

	public <T> List<T> queryColumnList(String sql, Object... params) {
		// TODO Auto-generated method stub
		try {
			return queryRunner.query(sql, new ColumnListHandler<T>(), params);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.error("查询单个实体出错", e);
			throw new RuntimeException(e);
		}
	}

	public <T> Map<T, Map<String, Object>> queryColumnMap(String column,
			String sql, Object... params) {
		// TODO Auto-generated method stub
		try {
			return queryRunner.query(sql, new KeyedHandler<T>(column), params);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.error("查询单个实体出错", e);
			throw new RuntimeException(e);
		}
	}
	

	public int update(String sql, Object... params) {
		// TODO Auto-generated method stub
		try {
			// TODO Auto-generated method stub
			Connection conn = DaoHelper.getConnection();
			return queryRunner.update(conn, sql, params);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("查询单个实体出错", e);
			throw new RuntimeException(e);
		}
	}
	public long queryCount(String sql, Object... params) {
		// TODO Auto-generated method stub
		try {
			return queryRunner.query(sql, new ScalarHandler<Long>("count(*)"), params);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.error("查询单个实体出错", e);
			throw new RuntimeException(e);
		}
	}

}
