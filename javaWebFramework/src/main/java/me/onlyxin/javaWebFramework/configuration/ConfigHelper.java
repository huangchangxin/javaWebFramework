package me.onlyxin.javaWebFramework.configuration;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.onlyxin.javaWebFramework.utils.PropsUtil;

public class ConfigHelper {

	//打印日志
	private static final Logger logger = LoggerFactory.getLogger(PropsUtil.class);
	
	//主配置文件
	private static final Properties configuration = PropsUtil.loadProps(Constant.CONFIG_PATH);
	
	//获取主配置文件字符型属性值
	public static String getString(String key) {
		return PropsUtil.getString(configuration, key);
	}
}
