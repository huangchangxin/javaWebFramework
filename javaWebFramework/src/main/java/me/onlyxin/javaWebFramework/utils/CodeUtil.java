package me.onlyxin.javaWebFramework.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.onlyxin.javaWebFramework.configuration.Constant;

public class CodeUtil {

	//打印日志
	private static final Logger logger = LoggerFactory.getLogger(ClassUtil.class);
	//编码
	public static String encodeURL(String str) {
		String res = null;
		try {
			res = URLEncoder.encode(str, Constant.UTF_8);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			logger.error("编码出错", e);
			throw new RuntimeException(e);
		}
		return res;
	}
	
	public static String decodeURL(String str) {
		String res = null;
		try {
			res = URLDecoder.decode(str, Constant.UTF_8);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			logger.error("解码出错", e);
			throw new RuntimeException(e);
		}
		return res;
	}
}
