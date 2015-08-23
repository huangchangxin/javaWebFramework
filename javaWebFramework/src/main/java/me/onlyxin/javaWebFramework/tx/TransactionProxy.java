package me.onlyxin.javaWebFramework.tx;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.onlyxin.javaWebFramework.aop.AopHelper;
import me.onlyxin.javaWebFramework.aop.Proxy;
import me.onlyxin.javaWebFramework.aop.ProxyChain;
import me.onlyxin.javaWebFramework.dao.DaoHelper;

public class TransactionProxy implements Proxy {
	
	// 打印日志
	private static final Logger logger = LoggerFactory
				.getLogger(TransactionProxy.class);

	public Object doProxy(ProxyChain proxyChain) {
		// TODO Auto-generated method stub
		Object res = null;
		Method method = proxyChain.getTargetMethod();
		if (method.isAnnotationPresent(Transaction.class)) {
			try {
				DaoHelper.beginTransaction();
				res = proxyChain.doProxyChain();
				DaoHelper.commitTransaction();
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				DaoHelper.rollbackTransaction();
				e.printStackTrace();
			}
			
		}
		return res;
	}

}
