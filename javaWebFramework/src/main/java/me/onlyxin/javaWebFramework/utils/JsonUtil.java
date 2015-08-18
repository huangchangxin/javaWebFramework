package me.onlyxin.javaWebFramework.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {

	// 打印日志
	private static final Logger logger = LoggerFactory.getLogger(JsonUtil.class);
	private static final ObjectMapper objectMapper = new ObjectMapper();
	
	public static <T> String stringify(T obj) {
		String res = null;
		try {
			res = objectMapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			logger.error("解析对象出错", e);
			throw new RuntimeException(e);
		}
		return res;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T parse(String str, Class<?> type) {
		T obj = null;
		try {
			obj = (T) objectMapper.readValue(str, type);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("解析字符串出错", e);
			throw new RuntimeException(e);
		}
		return obj;
	}
}
