package cn.nextop.thorin.test.vo.id.has.derivative;

import cn.nextop.thorin.core.pricing.domain.PricingGenerator;
import cn.nextop.thorin.test.vo.id.has.compound.HasBkSymbolId;
import cn.nextop.thorin.test.vo.id.has.single.HasPricingDeviationPolicy;

public interface HasBkDeviationConfigId extends HasBkSymbolId, HasPricingDeviationPolicy {
	
	default long bkDeviationConfigId() {
		return PricingGenerator.toBkDeviationConfigId(bkId(), symbolId(), pricingDeviationPolicy());
	}
	
}
