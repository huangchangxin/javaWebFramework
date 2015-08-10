package me.onlyxin.javaWebFramework.classScanner;

import java.util.List;

public class DefaultClassScanner implements ClassScanner {

	public List<Class<?>> getClassList(String packageName) {
		// TODO Auto-generated method stub
		return new ClassTemplate(packageName) {
			@Override
			public boolean doCheck(Class<?> clazz) {
				// TODO Auto-generated method stub
				String className = clazz.getName();
				String pkgName = className.substring(0, className.lastIndexOf("."));
				return pkgName.startsWith(packageName);
			}
		}.getClassList();
	}

	public List<Class<?>> getClassListBySuper(String packageName, final Class<?> interfaceClass) {
		// TODO Auto-generated method stub
		return new SuperClassTemplate(packageName, interfaceClass) {
			@Override
			public boolean doCheck(Class<?> clazz) {
				// TODO Auto-generated method stub
				return interfaceClass.isAssignableFrom(clazz) && !clazz.equals(interfaceClass);
			}
		}.getClassList();
	}

}
