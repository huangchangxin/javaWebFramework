package me.onlyxin.javaWebFramework.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class StreamUtil {

	private static final Logger logger = LoggerFactory.getLogger(StreamUtil.class);
	public static String getString(InputStream is) {
		StringBuffer stringBuffer = new StringBuffer();
		try {
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				stringBuffer.append(line);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("读取字节流出错", e);
			throw new RuntimeException(e);
		}
		return stringBuffer.toString();
	}
}
