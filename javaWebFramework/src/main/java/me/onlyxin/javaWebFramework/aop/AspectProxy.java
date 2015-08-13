package me.onlyxin.javaWebFramework.aop;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AspectProxy implements Proxy {

	private static final Logger logger = LoggerFactory
			.getLogger(AspectProxy.class);

	public Object doProxy(ProxyChain proxyChain) {
		// TODO Auto-generated method stub
		Object res = null;
		
		Class<?> targetClass = proxyChain.getTargetClass();
		Method targetMethod = proxyChain.getTargetMethod();
		Object[] methodParams = proxyChain.getMethodParams();
		
		begin();
		try {
			if (intercept(targetClass, targetMethod, methodParams)) {
				before(targetClass, targetMethod, methodParams);
				res = proxyChain.doProxyChain();
				after(targetClass, targetMethod, methodParams);
			} else {
				res = proxyChain.doProxyChain();
			}
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			logger.error("aop出错");
			error(targetClass, targetMethod, methodParams, e);
			throw new RuntimeException(e);
		} finally {
			end();
		}
		return res;
	}

	//异常通知
	private void error(Class<?> targetClass, Method targetMethod,
			Object[] methodParams, Throwable e) {
		// TODO Auto-generated method stub
		
	}
	//最终通知
	private void end() {
		// TODO Auto-generated method stub
		
	}

	//后置通知搜索
	private void after(Class<?> targetClass, Method targetMethod,
			Object[] methodParams) {
		// TODO Auto-generated method stub
		
	}

	//前置通知
	private void before(Class<?> targetClass, Method targetMethod,
			Object[] methodParams) {
		// TODO Auto-generated method stub

	}

	private boolean intercept(Class<?> targetClass, Method targetMethod,
			Object[] methodParams) {
		// TODO Auto-generated method stub
		return true;
	}
	//最前通知
	private void begin() {
		// TODO Auto-generated method stub
	}
}
