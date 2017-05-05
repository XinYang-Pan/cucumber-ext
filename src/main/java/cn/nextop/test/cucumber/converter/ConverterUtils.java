package cn.nextop.test.cucumber.converter;

import java.util.function.Function;

import org.apache.commons.lang3.StringUtils;

class ConverterUtils {

	static Object fromString(String str, Function<String, Object> fromStringFunction) {
		if (StringUtils.isBlank(str)) {
			return null;
		} else {
			return fromStringFunction.apply(str);
		}
	}
	
}
