package cn.nextop.thorin.test.vo.id.obj.derivative;

import cn.nextop.thorin.common.util.datetime.TradingDate;
import cn.nextop.thorin.core.common.domain.CommonGenerator;
import cn.nextop.thorin.core.common.glossary.DealingCounterpartyType;
import cn.nextop.thorin.core.dealing.domain.DealingGenerator;
import cn.nextop.thorin.test.vo.id.has.derivative.HasCounterpartyId;
import cn.nextop.thorin.test.vo.id.obj.compound.LpSymbolId;
import cn.nextop.thorin.test.vo.id.obj.single.SymbolId;

public class CounterpartyId extends SymbolId implements HasCounterpartyId {
	private final TradingDate tradingDate;
	private final DealingCounterpartyType type;
	private final short cpId;

	public CounterpartyId(TradingDate tradingDate, DealingCounterpartyType type, short cpId, short symbolId) {
		super(symbolId);
		this.tradingDate = tradingDate;
		this.type = type;
		this.cpId = cpId;
	}

	// -----------------------------
	// ----- Static
	// -----------------------------

	public static CounterpartyId fromBrokerId(TradingDate tradingDate, short brokerId, short symbolId) {
		return new CounterpartyId(tradingDate, DealingCounterpartyType.BROKER, brokerId, symbolId);
	}

	public static CounterpartyId fromLpId(TradingDate tradingDate, short lpId, short symbolId) {
		return new CounterpartyId(tradingDate, DealingCounterpartyType.LP, lpId, symbolId);
	}

	public static CounterpartyId toCounterpartyId(TradingDate tradingDate, LpSymbolId lpSymbolId) {
		return fromLpId(tradingDate, lpSymbolId.lpId(), lpSymbolId.symbolId());
	}

	public static CounterpartyId toCounterpartyId(long counterpartyId) {
		short cpId = DealingGenerator.getCpIdByCounterpartyId(counterpartyId);
		short symbolId = DealingGenerator.getSymbolIdByCounterpartyId(counterpartyId);
		TradingDate tradingDate = DealingGenerator.getTradingDateByCounterpartyId(counterpartyId);
		DealingCounterpartyType type = DealingGenerator.getTypeByCounterpartyId(counterpartyId);
		return new CounterpartyId(tradingDate, type, cpId, symbolId);
	}

	// -----------------------------
	// ----- methods
	// -----------------------------

	@Override
	public long counterpartyId() {
		return DealingGenerator.toCounterpartyId(tradingDate, type, cpId, symbolId());
	}

	@Override
	public TradingDate tradingDate() {
		return tradingDate;
	}

	@Override
	public byte coId() {
		switch (type) {
		case BROKER:
			return CommonGenerator.getCompanyIdByBrokerId(cpId);
		case LP:
			return CommonGenerator.getCompanyIdByLpId(cpId);
		default:
			throw new RuntimeException();
		}
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CounterpartyId [date=");
		builder.append(tradingDate);
		builder.append(", type=");
		builder.append(type);
		builder.append(", cpId=");
		builder.append(cpId);
		builder.append("]");
		return builder.toString();
	}

	public TradingDate getTradingDate() {
		return tradingDate;
	}

	public DealingCounterpartyType getType() {
		return type;
	}

	public short getCpId() {
		return cpId;
	}

}
