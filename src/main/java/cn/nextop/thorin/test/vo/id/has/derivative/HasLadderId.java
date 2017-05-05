package cn.nextop.thorin.test.vo.id.has.derivative;

import cn.nextop.thorin.core.dealing.domain.DealingGenerator;
import cn.nextop.thorin.test.vo.id.has.compound.HasLpSymbolId;
import cn.nextop.thorin.test.vo.id.has.single.HasTradingDate;

public interface HasLadderId extends HasLpSymbolId, HasTradingDate {

	default long ladderId() {
		return DealingGenerator.toLadderId(tradingDate(), lpId(), symbolId());
	}

}
