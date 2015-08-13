package me.onlyxin.javaWebFramework.aop;

import java.lang.annotation.Annotation;

//切面注解
public @interface Aspect {
	
	String pkg() default "";
	
	String cls() default "";
	
	Class<? extends Annotation> annotation() default Aspect.class;
}
