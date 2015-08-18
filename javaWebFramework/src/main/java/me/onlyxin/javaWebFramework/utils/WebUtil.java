package me.onlyxin.javaWebFramework.utils;

import java.io.IOException;
import java.io.PrintWriter;

import javax.management.RuntimeErrorException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import me.onlyxin.javaWebFramework.configuration.Constant;

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
			logger.error("重定向出错");
			throw new RuntimeException(e);
		}
	}    

	
	public static void sendError(int code, String message,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		try {
			response.sendError(code, message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("发送错误代码出错");
			throw new RuntimeException(e);
		}
	}

	public static void forwardRequest(String path, HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		try {
			request.getRequestDispatcher(path).forward(request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("转发出错", e);
			throw new RuntimeException(e);
		} 
	}
	
	public static void writeJSON(HttpServletResponse response, Object data) {
		try {
			response.setContentType("application/json");
			response.setCharacterEncoding(Constant.UTF_8);
			PrintWriter writer = response.getWriter();
			writer.write(JsonUtil.stringify(data));
			writer.flush();
			writer.close();
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("在响应中写数据出错", e);
			throw new RuntimeException(e);
		}
		
	}
}
