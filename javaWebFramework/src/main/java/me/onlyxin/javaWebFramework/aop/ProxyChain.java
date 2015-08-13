package me.onlyxin.javaWebFramework.aop;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import net.sf.cglib.proxy.MethodProxy;
//责任链模式
public class ProxyChain {

	private final Class<?> targetClass;
	private final Object targetObject;
	private final Method targetMethod;
	private final MethodProxy methodProxy;
	private final Object[] methodParams;
	
	private List<Proxy> proxyList = new ArrayList<Proxy>();
	private int proxyIndex = 0;
	
	public ProxyChain(Class<?> targetClass, Object targetObject, Method targetMethod, MethodProxy methodProxy, Object[] methodParams, List<Proxy> proxyList) {
		this.targetClass = targetClass;
        this.targetObject = targetObject;
        this.targetMethod = targetMethod;
        this.methodProxy = methodProxy;
        this.methodParams = methodParams;
        this.proxyList = proxyList;
	}
	public Object doProxyChain() throws Throwable {
		Object res;
		if (proxyIndex < proxyList.size()) {
			res = proxyList.get(proxyIndex++).doProxy(this);
		} else {
			res = methodProxy.invokeSuper(targetObject, methodParams);
		}
		return res;
	}
	public List<Proxy> getProxyList() {
		return proxyList;
	}
	public void setProxyList(List<Proxy> proxyList) {
		this.proxyList = proxyList;
	}
	public int getProxyIndex() {
		return proxyIndex;
	}
	public void setProxyIndex(int proxyIndex) {
		this.proxyIndex = proxyIndex;
	}
	public Class<?> getTargetClass() {
		return targetClass;
	}
	public Object getTargetObject() {
		return targetObject;
	}
	public Method getTargetMethod() {
		return targetMethod;
	}
	public MethodProxy getMethodProxy() {
		return methodProxy;
	}
	public Object[] getMethodParams() {
		return methodParams;
	}
	
	
}
