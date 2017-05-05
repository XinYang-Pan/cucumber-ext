package cn.nextop.thorin.test.core.cucumber.converter;

import java.util.Map;

import cn.nextop.thorin.core.common.glossary.PricingBandStatus;

public class PricingBandStatus1Converter extends AbstractTypeConfigConverter<PricingBandStatus, PricingBandStatus> {

	@SuppressWarnings("rawtypes")
	@Override
	public boolean canConvert(Class clazz) {
		return PricingBandStatus.class.equals(clazz);
	}

	@Override
	protected void initMap(Map<String, PricingBandStatus> abbr2TypeMap) {
		abbr2TypeMap.put("A", PricingBandStatus.ACTIVE);
		abbr2TypeMap.put("D", PricingBandStatus.DUPLICATE);
		abbr2TypeMap.put("R", PricingBandStatus.RECOVERED);
		abbr2TypeMap.put("S", PricingBandStatus.SUSPENDED);
	}

	@Override
	protected PricingBandStatus newConfig() {
		return new PricingBandStatus();
	}

	@Override
	protected void toSetEnabled(PricingBandStatus typeConfig, PricingBandStatus type) {
		if (PricingBandStatus.ACTIVE.equals(type)) {
			typeConfig.setActive(true);
		} else if (PricingBandStatus.DUPLICATE.equals(type)) {
			typeConfig.setDuplicate(true);
		} else if (PricingBandStatus.RECOVERED.equals(type)) {
			typeConfig.setRecovered(true);
		} else if (PricingBandStatus.SUSPENDED.equals(type)) {
			typeConfig.setSuspended(true);
		} else {
			throw new RuntimeException();
		}
	}

}
