package me.onlyxin.javaWebFramework.ioc;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.onlyxin.javaWebFramework.utils.CollectionUtil;

public class IocHelper {

	// 打印日志
	private static final Logger logger = LoggerFactory.getLogger(IocHelper.class);
	
	//依赖注入
	static {
		Map<Class<?>, Object> beanMap = BeanHelper.getBeanMap();
		for (Map.Entry<Class<?>, Object> beanEntry : beanMap.entrySet()) {
			Class<?> beanClass = beanEntry.getKey();
			Object beanInstance = beanEntry.getValue();
			try {
				Field[] beanFields = beanClass.getDeclaredFields();
				for (Field field : beanFields) {
					if (field.isAnnotationPresent(Autowired.class)) {
						Class<?> interfaceClass = field.getType();
						Class<?> implementClass = findImplementClass(interfaceClass);
						if (implementClass == null) {
							Object fieldInstance = beanMap.get(implementClass);
							if (fieldInstance != null) {
								field.setAccessible(true);
								field.set(beanInstance, fieldInstance);
							}
						}
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
				logger.error("依赖注入出错");
				throw new RuntimeException(e);
			}
		}
	}

	private static Class<?> findImplementClass(Class<?> interfaceClass) {
		// TODO Auto-generated method stub
		Class<?> implementClass = interfaceClass;
		if (interfaceClass.isAnnotationPresent(Implement.class)) {
			implementClass = implementClass.getAnnotation(Implement.class).value();
		} else {
			List<Class<?>> implementClassList = ClassHelper.getClassListBySuper(interfaceClass);
			if (CollectionUtil.isNotEmpty(implementClassList)) {
				implementClass = implementClassList.get(0);
			}
		}
		return implementClass;
	}
}
