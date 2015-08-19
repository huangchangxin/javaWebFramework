package me.onlyxin.javaWebFramework.core;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRegistration;

import me.onlyxin.javaWebFramework.configuration.Constant;
//加载框架的帮助类，处理静态资源和jsp文件
public class ContainerListener implements ServletContextListener {

	
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		ServletContext servletContext = arg0.getServletContext();
		HelperLoader.init();
		addServletMapping(servletContext);
	}

	private void addServletMapping(ServletContext servletContext) {
		// TODO Auto-generated method stub
		registerDefaultServlet(servletContext);
		registerJspServlet(servletContext);
	}

	private void registerJspServlet(ServletContext servletContext) {
		// TODO Auto-generated method stub
		ServletRegistration jspServlet = servletContext.getServletRegistration("jsp");
		jspServlet.addMapping("/index.jsp");
		String jspPath = Constant.JSP_PATH;
		jspServlet.addMapping(jspPath);
	}

	private void registerDefaultServlet(ServletContext servletContext) {
		// TODO Auto-generated method stub
		ServletRegistration defaultServlet = servletContext.getServletRegistration("default");
		defaultServlet.addMapping("/index.html");
		String wwwPath = Constant.WWW_PATH;
		defaultServlet.addMapping(wwwPath);
	}

	public void contextInitialized(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
