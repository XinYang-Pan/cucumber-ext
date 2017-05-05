package cn.nextop.thorin.test.vo.id.obj.derivative;

import cn.nextop.thorin.common.util.datetime.TradingDate;
import cn.nextop.thorin.core.common.glossary.SymbolCode;
import cn.nextop.thorin.test.vo.id.has.derivative.HasBalanceId;
import cn.nextop.thorin.test.vo.id.obj.compound.LpId;

public class BalanceId extends LpId implements HasBalanceId {
	private final TradingDate tradingDate;
	private final SymbolCode currencyCode;

	public BalanceId(TradingDate tradingDate, byte itId, byte lpSeq, byte coId, SymbolCode currencyCode) {
		super(itId, lpSeq, coId);
		this.tradingDate = tradingDate;
		this.currencyCode = currencyCode;
	}

	// -----------------------------
	// ----- Static Methods
	// -----------------------------

	public static BalanceId toBalanceId(TradingDate tradingDate, LpId lpId, SymbolCode currencyCode) {
		return new BalanceId(tradingDate, lpId.itId(), lpId.lpSeq(), lpId.coId(), currencyCode);
	}

	// -----------------------------
	// ----- methods
	// -----------------------------

	@Override
	public TradingDate tradingDate() {
		return tradingDate;
	}

	@Override
	public SymbolCode currency() {
		return currencyCode;
	}

}
