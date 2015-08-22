package me.onlyxin.javaWebFramework.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StringUtil {

	// 打印日志
	private static final Logger logger = LoggerFactory.getLogger(StringUtil.class);
	
	public static boolean isEmpty(String str) {
		return StringUtils.isEmpty(str);
	}

	public static boolean isNotEmpty(String str) {
		// TODO Auto-generated method stub
		return StringUtils.isNotEmpty(str);
	}
	
	public static String defaultIfEmpty(String str, String defaultValue) {
		return StringUtils.defaultIfEmpty(str, defaultValue);
	}

	public static String replaceAll(String str, String regex,
			String replacement) {
		// TODO Auto-generated method stub
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);
		StringBuffer stringBuffer = new StringBuffer();
		while (matcher.find()) {
			matcher.appendReplacement(stringBuffer, replacement);
		}
		matcher.appendTail(stringBuffer);
		return stringBuffer.toString();
	}
	
	public static String[] splitString(String str, String separator) {
		return StringUtils.splitByWholeSeparator(str, separator);
	}
	
	public static String camelhumpToUnderline(String str) {
		Matcher matcher = Pattern.compile("[A-Z]").matcher(str);
		StringBuffer stringBuffer = new StringBuffer();
		for (int i = 0; matcher.matches(); i++) {
			stringBuffer.replace(matcher.start()+i, matcher.end()+i, "_" + matcher.group().toLowerCase());
		}
		if (stringBuffer.charAt(0) == '_') {
			stringBuffer.deleteCharAt(0);
		}
		return stringBuffer.toString();
	}
}
