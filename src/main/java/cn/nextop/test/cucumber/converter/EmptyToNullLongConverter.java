package cn.nextop.test.cucumber.converter;

import cucumber.deps.com.thoughtworks.xstream.converters.basic.LongConverter;

public class EmptyToNullLongConverter extends LongConverter {

	@Override
	public Object fromString(String str) {
		return ConverterUtils.fromString(str, super::fromString);
	}

}
