package me.onlyxin.javaWebFramework.ioc;

import java.util.List;

import me.onlyxin.javaWebFramework.classScanner.ClassScanner;
import me.onlyxin.javaWebFramework.configuration.ConfigHelper;
import me.onlyxin.javaWebFramework.utils.ClassUtil;
import me.onlyxin.javaWebFramework.utils.InstanceFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClassHelper {

	// 打印日志
	private static final Logger logger = LoggerFactory.getLogger(ClassHelper.class);
	//获取基础包名
	private static final String basePackage = ConfigHelper.getString("base_package");
	//获取类扫描器
	private static final ClassScanner classScanner = InstanceFactory.getClassScanner();
	//获取指定包下的所有类
	public static List<Class<?>> getClassList() {
		return classScanner.getClassList(basePackage);
	}
	//获取指定父类下的所有子类
	public static List<Class<?>> getClassListBySuper(Class<?> interfaceClass) {
		// TODO Auto-generated method stub
		return classScanner.getClassListBySuper(basePackage, interfaceClass);
	}
}
