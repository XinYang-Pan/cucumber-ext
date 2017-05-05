package cn.nextop.test.cucumber.converter;

import cucumber.deps.com.thoughtworks.xstream.converters.basic.BooleanConverter;

public class EmptyToNullBooleanConverter extends BooleanConverter {

	@Override
	public Object fromString(String str) {
		return ConverterUtils.fromString(str, super::fromString);
	}

}
