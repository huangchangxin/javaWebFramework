package me.onlyxin.javaWebFramework.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface HandlerExceptionResolver {

	void resolveHandlerException(HttpServletRequest request, HttpServletResponse response, Exception e);
}
