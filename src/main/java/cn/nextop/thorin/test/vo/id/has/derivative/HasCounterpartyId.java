package cn.nextop.thorin.test.vo.id.has.derivative;

import cn.nextop.thorin.test.vo.id.has.compound.HasCoSymbolId;
import cn.nextop.thorin.test.vo.id.has.single.HasTradingDate;

public interface HasCounterpartyId extends HasCoSymbolId, HasTradingDate {

	long counterpartyId();
	
}
