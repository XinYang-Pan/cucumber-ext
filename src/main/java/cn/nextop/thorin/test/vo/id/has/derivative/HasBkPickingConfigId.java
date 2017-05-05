package cn.nextop.thorin.test.vo.id.has.derivative;

import cn.nextop.thorin.core.pricing.domain.PricingGenerator;
import cn.nextop.thorin.test.vo.id.has.compound.HasLpBkSymbolId;
import cn.nextop.thorin.test.vo.id.has.single.HasPricingPickingPolicy;

public interface HasBkPickingConfigId extends HasLpBkSymbolId, HasPricingPickingPolicy {

	default long bkPickingConfigId() {
		return PricingGenerator.toBkPickingConfigId(bkId(), lpId(), symbolId(), pricingPickingPolicy());
	}

}
