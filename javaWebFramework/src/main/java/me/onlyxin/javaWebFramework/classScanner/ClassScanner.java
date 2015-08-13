package me.onlyxin.javaWebFramework.classScanner;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.List;

//类扫描接口
public interface ClassScanner {

	//获取指定包下的所有类
	public abstract List<Class<?>> getClassList(String packageName);

	public abstract List<Class<?>> getClassListBySuper(String packageName, Class<?> interfaceClass);

	public abstract List<Class<?>> getClassListByAnnotation(
			String pkg, Class<? extends Annotation> annotation);
}
