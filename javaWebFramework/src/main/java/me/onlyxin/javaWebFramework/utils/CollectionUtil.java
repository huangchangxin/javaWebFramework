package me.onlyxin.javaWebFramework.utils;

import java.util.Collection;

import org.apache.commons.collections4.CollectionUtils;

public class CollectionUtil {

	public static boolean isNotEmpty(Collection<Class<?>> collection) {
		return CollectionUtils.isNotEmpty(collection);
	}
}
