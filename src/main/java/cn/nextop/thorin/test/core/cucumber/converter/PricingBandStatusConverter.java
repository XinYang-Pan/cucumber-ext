package cn.nextop.thorin.test.core.cucumber.converter;

import org.apache.commons.lang3.StringUtils;

import cn.nextop.thorin.core.common.glossary.PricingBandStatus;
import cucumber.deps.com.thoughtworks.xstream.converters.basic.AbstractSingleValueConverter;

public class PricingBandStatusConverter extends AbstractSingleValueConverter {

	@SuppressWarnings("rawtypes")
	@Override
	public boolean canConvert(Class clazz) {
		return PricingBandStatus.class.equals(clazz);
	}

	@Override
	public PricingBandStatus fromString(String str) {
		if (StringUtils.isBlank(str)) {
			return null;
		} else {
			byte statusCode = Byte.parseByte(str, 2);
			return new PricingBandStatus(statusCode);
		}
	}

}
