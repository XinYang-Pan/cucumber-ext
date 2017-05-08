package org.blueo.cucumber.util;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.SimpleTypeConverter;

public class ElementUtils {
	public static SimpleTypeConverter TYPE_CONVERTER = new SimpleTypeConverter();

	public static <T> T newInstance(Class<T> clazz) {
		T t = BeanUtils.instantiate(clazz);
		return t;
	}

}
