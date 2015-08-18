package me.onlyxin.javaWebFramework.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//封装与每个请求相关的数据
public class DataContext {

	private static final ThreadLocal<DataContext> dataContextContainer = new ThreadLocal<DataContext>();
	
	private HttpServletRequest request;
	private HttpServletResponse response;
	
	public static void init(HttpServletRequest request, HttpServletResponse response) {
		DataContext dataContext = new DataContext();
		dataContext.request = request;
		dataContext.response = response;
		dataContextContainer.set(dataContext);
	}
	
	public static void destroy() {
		dataContextContainer.remove();
	}
}
