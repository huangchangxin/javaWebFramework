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
	
	private final HandlerExceptionResolver handlerExceptionResolver = InstanceFactory.getHandlerExceptionResolver();
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding(Constant.UTF_8);
		response.setContentType("text/html;charset="+Constant.UTF_8);
		String method = request.getMethod();
		String requestPath = WebUtil.getRequestPath(request);
		if (logger.isDebugEnabled()) {
			logger.debug("请求开始，请求方法为：method={}, requestPath={}", method, requestPath);
		}
		if (requestPath.equals("/")) {
			WebUtil.redirectRequest(requestPath, request, response);
		}
		if (requestPath.endsWith("/")) {
			requestPath = requestPath.substring(0, requestPath.length()-1);
		}
		Handler handler = handlerMapping.getHandler(method, requestPath);
		if (handler == null) {
			WebUtil.sendError(HttpServletResponse.SC_NOT_FOUND, "", response);
			return;
		}
		DataContext.init(request, response);
		try {
			handlerInvoker.invokeHandler(request, response, handler);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			handlerExceptionResolver.resolveHandlerException(request, response, e);
		} finally {
			DataContext.destroy();
		}
		if (logger.isDebugEnabled()) {
			logger.debug("请求结束");
		}
	}
	
}
