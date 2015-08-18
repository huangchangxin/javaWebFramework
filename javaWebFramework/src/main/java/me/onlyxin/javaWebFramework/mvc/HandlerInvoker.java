package me.onlyxin.javaWebFramework.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface HandlerInvoker {

	void invokeHandler(HttpServletRequest request,
			HttpServletResponse response, Handler handler) throws Exception;

}
