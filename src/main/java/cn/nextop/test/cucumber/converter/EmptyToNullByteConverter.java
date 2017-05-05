package cn.nextop.test.cucumber.converter;

import cucumber.deps.com.thoughtworks.xstream.converters.basic.ByteConverter;

public class EmptyToNullByteConverter extends ByteConverter {

	@Override
	public Object fromString(String str) {
		return ConverterUtils.fromString(str, super::fromString);
	}

}
