package me.onlyxin.javaWebFramework.aop;

import java.lang.reflect.Method;
import java.util.List;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class ProxyHelper {

	@SuppressWarnings("unchecked")
	public static <T> T createProxyInstance(final Class<?> targetClass, final List<Proxy> proxyList) {
		return (T) Enhancer.create(targetClass, new MethodInterceptor() {
			
			public Object intercept(Object targetObject, Method targetMethod, Object[] methodParams,
					MethodProxy methodProxy) throws Throwable {
				// TODO Auto-generated method stub
				return new ProxyChain(targetClass, targetObject, targetMethod, methodProxy, methodParams, proxyList);
			}
		});
	}
}
