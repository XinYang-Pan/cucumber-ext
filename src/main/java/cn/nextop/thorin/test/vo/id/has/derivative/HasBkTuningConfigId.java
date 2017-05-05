package cn.nextop.thorin.test.vo.id.has.derivative;

import cn.nextop.thorin.core.pricing.domain.PricingGenerator;
import cn.nextop.thorin.test.vo.id.has.compound.HasBkSymbolId;
import cn.nextop.thorin.test.vo.id.has.single.HasPricingTuningPolicy;

public interface HasBkTuningConfigId extends HasBkSymbolId, HasPricingTuningPolicy {
	
	default long bkTuningConfigId() {
		return PricingGenerator.toBkTuningConfigId(bkId(), symbolId(), pricingTuningPolicy());
	}
	
}
