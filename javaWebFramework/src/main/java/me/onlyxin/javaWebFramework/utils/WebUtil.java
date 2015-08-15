package me.onlyxin.javaWebFramework.utils;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebUtil {

	// 打印日志
	private static final Logger logger = LoggerFactory.getLogger(StringUtil.class);
	
	public static  String getRequestPath(HttpServletRequest request) {
		String servletPath = request.getServletPath();
		String pathInfo = StringUtil.defaultIfEmpty(request.getPathInfo(), "");
		return servletPath + pathInfo;
	}
	
	public static void redirectRequest(String path, HttpServletRequest request, HttpServletResponse response) {
		try {
			response.sendRedirect(request.getContextPath() + path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("转发出错");
			throw new RuntimeException(e);
		}
	}
}
