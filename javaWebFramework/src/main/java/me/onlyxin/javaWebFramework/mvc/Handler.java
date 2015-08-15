package me.onlyxin.javaWebFramework.mvc;

import java.lang.reflect.Method;
import java.util.regex.Matcher;

public class Handler {

	private Class<?> handlerClass;
	private Method handlerMethod;
	private Matcher requestPathMatcher;
	public Class<?> getHandlerClass() {
		return handlerClass;
	}

	public void setHandlerClass(Class<?> handlerClass) {
		this.handlerClass = handlerClass;
	}

	public Method getHandlerMethod() {
		return handlerMethod;
	}

	public void setHandlerMethod(Method handlerMethod) {
		this.handlerMethod = handlerMethod;
	}

	public Matcher getRequestPathMatcher() {
		return requestPathMatcher;
	}

	public void setRequestPathMatcher(Matcher requestPathMatcher) {
		this.requestPathMatcher = requestPathMatcher;
	}
	
	public Handler(Class<?> handlerClass, Method handlerMethod) {
		this.handlerClass = handlerClass;
		this.handlerMethod = handlerMethod;
	}
	
	
}
