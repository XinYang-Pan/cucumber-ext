package cn.nextop.test.cucumber.converter;

import cucumber.deps.com.thoughtworks.xstream.converters.basic.ShortConverter;

public class EmptyToNullShortConverter extends ShortConverter {

	@Override
	public Object fromString(String str) {
		return ConverterUtils.fromString(str, super::fromString);
	}

}
