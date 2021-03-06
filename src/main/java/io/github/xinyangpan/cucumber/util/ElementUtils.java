package io.github.xinyangpan.cucumber.util;

import org.springframework.beans.BeanWrapperImpl;
import org.springframework.core.convert.support.DefaultConversionService;

public class ElementUtils {
	// 
	private static DefaultConversionService CONVERSION_SERVICE = new DefaultConversionService();

	public static <T> T newInstance(Class<T> clazz) {
		try {
			T t = clazz.newInstance();
			return t;
		} catch (Exception e) {
			return null;
		}
	}

	public static BeanWrapperImpl newBeanWrapperImpl(Object target) {
		BeanWrapperImpl beanWrapperImpl = new BeanWrapperImpl(target);
		beanWrapperImpl.setConversionService(CONVERSION_SERVICE);
		return beanWrapperImpl;
	}

	public static DefaultConversionService defaultConversionService() {
		return CONVERSION_SERVICE;
	}
	
}
