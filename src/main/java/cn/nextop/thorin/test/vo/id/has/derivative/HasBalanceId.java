package cn.nextop.thorin.test.vo.id.has.derivative;

import cn.nextop.thorin.core.dealing.domain.DealingGenerator;
import cn.nextop.thorin.test.vo.id.has.compound.HasLpId;
import cn.nextop.thorin.test.vo.id.has.single.HasCurrency;
import cn.nextop.thorin.test.vo.id.has.single.HasTradingDate;

public interface HasBalanceId extends HasLpId, HasTradingDate, HasCurrency {

	default long balanceId() {
		return DealingGenerator.toBalanceId(tradingDate(), lpId(), currency());
	}

}
