package me.onlyxin.javaWebFramework.classScanner;

public abstract class SuperClassTemplate extends ClassTemplate {

	protected final Class<?> superClass;
	public SuperClassTemplate(String packageName, Class<?> superClass) {
		super(packageName);
		this.superClass = superClass;
	}
}
