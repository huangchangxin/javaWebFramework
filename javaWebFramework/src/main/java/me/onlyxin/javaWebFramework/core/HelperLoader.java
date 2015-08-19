package me.onlyxin.javaWebFramework.core;

import me.onlyxin.javaWebFramework.aop.AopHelper;
import me.onlyxin.javaWebFramework.ioc.BeanHelper;
import me.onlyxin.javaWebFramework.ioc.IocHelper;
import me.onlyxin.javaWebFramework.mvc.ControllerHelper;
import me.onlyxin.javaWebFramework.utils.ClassUtil;
//加载框架各个组件
public class HelperLoader {

	public static void init() {
		Class<?>[] classList = {
			ControllerHelper.class,
			BeanHelper.class,
			IocHelper.class,
			AopHelper.class
		};
		for (Class<?> clazz : classList) {
			ClassUtil.loadClass(clazz.getName(), true);
		}
	}
}
