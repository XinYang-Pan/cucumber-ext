package cn.nextop.test.cucumber.converter;

import cucumber.deps.com.thoughtworks.xstream.converters.basic.IntConverter;

public class EmptyToNullIntegerConverter extends IntConverter {

	@Override
	public Object fromString(String str) {
		return ConverterUtils.fromString(str, super::fromString);
	}

}
