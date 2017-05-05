package cn.nextop.thorin.test.core.cucumber.converter;

import java.util.Map;

import cn.nextop.thorin.core.common.glossary.HedgingOrderType;
import cn.nextop.thorin.core.common.glossary.HedgingOrderTypeConfig;

public class HedgingOrderTypeConfigConverter extends AbstractTypeConfigConverter<HedgingOrderType, HedgingOrderTypeConfig> {

	@SuppressWarnings("rawtypes")
	@Override
	public boolean canConvert(Class clazz) {
		return HedgingOrderTypeConfig.class.equals(clazz);
	}

	@Override
	protected void initMap(Map<String, HedgingOrderType> abbr2TypeMap) {
		abbr2TypeMap.put("L", HedgingOrderType.LIMIT);
		abbr2TypeMap.put("M", HedgingOrderType.MARKET);
		abbr2TypeMap.put("P", HedgingOrderType.PREVIOUSLY_QUOTED);
		abbr2TypeMap.put("S", HedgingOrderType.SLIPPAGE);
	}

	@Override
	protected HedgingOrderTypeConfig newConfig() {
		return new HedgingOrderTypeConfig();
	}

	@Override
	protected void toSetEnabled(HedgingOrderTypeConfig typeConfig, HedgingOrderType hedgingOrderType) {
		typeConfig.setEnabled(hedgingOrderType, true);
	}

}
