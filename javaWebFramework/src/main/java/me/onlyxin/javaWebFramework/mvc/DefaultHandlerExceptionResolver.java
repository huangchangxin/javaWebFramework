package me.onlyxin.javaWebFramework.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import me.onlyxin.javaWebFramework.configuration.Constant;
import me.onlyxin.javaWebFramework.utils.WebUtil;

public class DefaultHandlerExceptionResolver implements HandlerExceptionResolver {

	public void resolveHandlerException(HttpServletRequest request,
			HttpServletResponse response, Exception e) {
		// TODO Auto-generated method stub
		WebUtil.redirectRequest(Constant.HOME_PAGE, request, response);
	}

}
