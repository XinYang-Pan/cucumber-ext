package cn.nextop.test.cucumber;

import org.apache.commons.lang3.StringUtils;

import cucumber.deps.com.thoughtworks.xstream.mapper.Mapper;
import cucumber.deps.com.thoughtworks.xstream.mapper.MapperWrapper;

public class IgnoreMapperWrapper extends MapperWrapper {

	public IgnoreMapperWrapper(Mapper wrapped) {
		super(wrapped);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public boolean shouldSerializeMember(Class definedIn, String fieldName) {
		if (StringUtils.startsWith(fieldName, "_")) {
			return false;
		}
		return true;
	}

}
