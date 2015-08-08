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

}
