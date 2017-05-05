package cn.nextop.test.cucumber;

import java.util.Map;

import cn.nextop.thorin.common.util.datetime.TradingDate;
import cn.nextop.thorin.test.vo.id.obj.compound.LpSymbolId;
import cn.nextop.thorin.test.vo.id.obj.derivative.CounterpartyId;
import cn.nextop.thorin.test.vo.id.obj.derivative.LadderId;

public class Element extends AbstractElement {

	public Element(Map<String, String> keyValueMap) {
		super(keyValueMap);
	}

	// -----------------------------
	// ----- raw
	// -----------------------------
	public TradingDate getTradingDate() {
		return this.getValue("tradingDate", TradingDate.class);
	}

	public short getLpId() {
		return this.getValue("lpId", short.class);
	}

	public short getSymbolId() {
		return this.getValue("symbolId", short.class);
	}

	// -----------------------------
	// ----- Convert raw to Target type
	// -----------------------------

	public LpSymbolId toLpSymbolId() {
		short lpId = this.getLpId();
		short symbolId = this.getSymbolId();
		return LpSymbolId.toLpSymbolId(lpId, symbolId);
	}

	public LadderId toLadderId() {
		LpSymbolId lpSymbolId = LpSymbolId.toLpSymbolId(getLpId(), getSymbolId());
		TradingDate tradingDate = this.getTradingDate();
		return LadderId.toLadderId(tradingDate, lpSymbolId);
	}

	public CounterpartyId toCounterpartyId() {
		LpSymbolId lpSymbolId = LpSymbolId.toLpSymbolId(getLpId(), getSymbolId());
		TradingDate tradingDate = this.getTradingDate();
		return CounterpartyId.toCounterpartyId(tradingDate, lpSymbolId);
	}

}
