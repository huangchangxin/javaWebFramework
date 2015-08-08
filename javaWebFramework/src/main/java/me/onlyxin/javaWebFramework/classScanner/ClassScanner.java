package me.onlyxin.javaWebFramework.classScanner;

import java.util.List;

//类扫描接口
public interface ClassScanner {

	//获取指定包下的所有类
	public abstract List<Class<?>> getClassList(String packageName);
}
