package me.onlyxin.javaWebFramework.dao;

import javax.sql.DataSource;

import me.onlyxin.javaWebFramework.configuration.ConfigHelper;

import org.apache.commons.dbcp2.BasicDataSource;
//默认数据源
public class DefaultDataSourceFactory implements DataSourceFactory {

	private final String driver = ConfigHelper.getString("jdbc_driver");
	private final String url = ConfigHelper.getString("jdbc_url");
	private final String username = ConfigHelper.getString("jdbc_username");
	private final String password = ConfigHelper.getString("jdbc_password");
	
	public DataSource getDataSource() {
		// TODO Auto-generated method stub
		BasicDataSource ds = new BasicDataSource();
		ds.setDriverClassName(driver);
		ds.setUrl(url);
		ds.setUsername(username);
		ds.setPassword(password);
		return ds;
	}

}
