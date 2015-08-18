package me.onlyxin.javaWebFramework.configuration;

public interface Constant {
	//编码
	public static final String UTF_8 = "UTF-8";
	//主配置文件
	public static final String CONFIG_PATH = "main.properties";
	
	public static final String JSP_PATH = ConfigHelper.getString("jsp_path", "/WEB-INF/jsp/");
	
	public static final String HOME_PAGE = ConfigHelper.getString("home_page", "/index.html");
}
