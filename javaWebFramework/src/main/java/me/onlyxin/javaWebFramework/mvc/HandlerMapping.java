package me.onlyxin.javaWebFramework.mvc;

public interface HandlerMapping {

	Handler getHandler(String method, String requestPath);

}
