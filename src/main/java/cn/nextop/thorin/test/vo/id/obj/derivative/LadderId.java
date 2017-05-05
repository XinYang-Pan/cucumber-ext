package cn.nextop.thorin.test.vo.id.obj.derivative;

import cn.nextop.thorin.common.util.datetime.TradingDate;
import cn.nextop.thorin.core.dealing.domain.DealingGenerator;
import cn.nextop.thorin.test.vo.id.has.derivative.HasLadderId;
import cn.nextop.thorin.test.vo.id.obj.compound.LpSymbolId;

public class LadderId extends LpSymbolId implements HasLadderId {
	private final TradingDate tradingDate;

	public LadderId(TradingDate tradingDate, byte itId, byte lpSeq, byte coId, short symbolId) {
		super(itId, lpSeq, coId, symbolId);
		this.tradingDate = tradingDate;
	}

	// -----------------------------
	// ----- Static Methods
	// -----------------------------

	public static LadderId toLadderId(long ladderId) {
		short lpId = DealingGenerator.getLpIdByLadderId(ladderId);
		short symbolId = DealingGenerator.getSymbolIdByLadderId(ladderId);
		LpSymbolId lpSymbolId = LpSymbolId.toLpSymbolId(lpId, symbolId);
		return toLadderId(DealingGenerator.getValueDateByLadderId(ladderId), lpSymbolId.lpSymbolId());
	}

	public static LadderId toLadderId(TradingDate tradingDate, LpSymbolId lpSymbolId) {
		return new LadderId(tradingDate, lpSymbolId.itId(), lpSymbolId.lpSeq(), lpSymbolId.coId(), lpSymbolId.symbolId());
	}

	public static LadderId toLadderId(TradingDate tradingDate, int lpSymbolId) {
		return toLadderId(tradingDate, LpSymbolId.toLpSymbolId(lpSymbolId));
	}

	// -----------------------------
	// ----- methods
	// -----------------------------

	@Override
	public TradingDate tradingDate() {
		return tradingDate;
	}

}
