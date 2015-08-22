package me.onlyxin.javaWebFramework.utils;

import java.util.Map;

import org.apache.commons.collections4.MapUtils;

public class MapUtil {

	public static boolean isNotEmpty(Map<?, ?> map) {
		return MapUtils.isNotEmpty(map);
	}
	public static boolean isEmpty(Map<?, ?> map) {
		return MapUtils.isEmpty(map);
	}
}
