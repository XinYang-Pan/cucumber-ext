package cn.nextop.thorin.test.vo.id.has.derivative;

import cn.nextop.thorin.core.pricing.domain.PricingGenerator;
import cn.nextop.thorin.test.vo.id.has.compound.HasLpSymbolId;
import cn.nextop.thorin.test.vo.id.has.single.HasPricingDeviationPolicy;

public interface HasLpDeviationConfigId extends HasLpSymbolId, HasPricingDeviationPolicy {
	
	default long lpDeviationConfigId() {
		return PricingGenerator.toLpDeviationConfigId(lpId(), symbolId(), pricingDeviationPolicy());
	}
	
}
