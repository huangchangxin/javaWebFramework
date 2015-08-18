package me.onlyxin.javaWebFramework.mvc;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
//默认的处理器映射器
public class DefaultHandlerMapping implements HandlerMapping {

	public Handler getHandler(String method, String requestPath) {
		// TODO Auto-generated method stub
		Handler handler = null;
		Map<Requester, Handler> controllerMap = ControllerHelper.getControllerMap();
		for (Map.Entry<Requester, Handler> controllerEntry : controllerMap.entrySet()) {
			Requester requester = controllerEntry.getKey();
			String requestMethod = requester.getRequestMethod();
			String requestPath1 = requester.getRequestPath();
			Matcher matcher = Pattern.compile(requestPath1).matcher(requestPath);
			if (method.equalsIgnoreCase(requestMethod) && matcher.matches()) {
				handler = controllerEntry.getValue();
				if (handler != null) {
					handler.setRequestPathMatcher(matcher);
				}
				break;
			}
		}
		return handler;
	}

}
