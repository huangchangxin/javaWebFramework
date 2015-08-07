package me.onlyxin.javaWebFramework.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StringUtil {

	// 打印日志
	private static final Logger logger = LoggerFactory.getLogger(StringUtil.class);
	
	public static boolean isEmpty(String str) {
		return StringUtils.isEmpty(str);
	}
}
