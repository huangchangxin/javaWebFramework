package me.onlyxin.javaWebFramework.mvc;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import me.onlyxin.javaWebFramework.aop.AopHelper;
import me.onlyxin.javaWebFramework.configuration.Constant;
import me.onlyxin.javaWebFramework.utils.InstanceFactory;
import me.onlyxin.javaWebFramework.utils.WebUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DispatcherServlet extends HttpServlet {
	// 打印日志
	private static final Logger logger = LoggerFactory
			.getLogger(DispatcherServlet.class);

	private final HandlerMapping handlerMapping = InstanceFactory.getHandlerMapping();
	
	private final HandlerInvoker handlerInvoker = InstanceFactory.getHandlerInvoker();
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding(Constant.UTF_8);
		response.setContentType("text/html;charset="+Constant.UTF_8);
		String method = request.getMethod();
		String requestPath = WebUtil.getRequestPath(request);
		if (requestPath.equals("/")) {
			WebUtil.redirectRequest(requestPath, request, response);
		}
		if (requestPath.endsWith("/")) {
			requestPath = requestPath.substring(0, requestPath.length()-1);
		}
		Handler handler = handlerMapping.getHandler(method, requestPath);
	}
	
}
