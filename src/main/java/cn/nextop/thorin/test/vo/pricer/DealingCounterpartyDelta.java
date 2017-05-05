package cn.nextop.thorin.test.vo.pricer;

import java.math.BigDecimal;

import cn.nextop.thorin.core.dealing.orm.po.DealingCounterpartyPo;

public class DealingCounterpartyDelta {

	private BigDecimal longTrades;
	private BigDecimal longVolume;
	private BigDecimal longAmount;
	private BigDecimal shortTrades;
	private BigDecimal shortVolume;
	private BigDecimal shortAmount;

	public static DealingCounterpartyDelta delta(DealingCounterpartyPo old, DealingCounterpartyPo now) {
		DealingCounterpartyDelta delta = new DealingCounterpartyDelta();
		if (old == null && now == null) {
			return delta;
		}
		if (old == null) {
			delta.setLongTrades(now.getLongTrades());
			delta.setLongVolume(now.getLongVolume());
			delta.setLongAmount(now.getLongAmount());
			delta.setShortTrades(now.getShortTrades());
			delta.setShortVolume(now.getShortVolume());
			delta.setShortAmount(now.getShortAmount());
		} else {
			delta.setLongTrades(now.getLongTrades().subtract(old.getLongTrades()));
			delta.setLongVolume(now.getLongVolume().subtract(old.getLongVolume()));
			delta.setLongAmount(now.getLongAmount().subtract(old.getLongAmount()));
			delta.setShortTrades(now.getShortTrades().subtract(old.getShortTrades()));
			delta.setShortVolume(now.getShortVolume().subtract(old.getShortVolume()));
			delta.setShortAmount(now.getShortAmount().subtract(old.getShortAmount()));
		}
		return delta;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DealingCounterpartyDelta [longTrades=");
		builder.append(longTrades);
		builder.append(", longVolume=");
		builder.append(longVolume);
		builder.append(", longAmount=");
		builder.append(longAmount);
		builder.append(", shortTrades=");
		builder.append(shortTrades);
		builder.append(", shortVolume=");
		builder.append(shortVolume);
		builder.append(", shortAmount=");
		builder.append(shortAmount);
		builder.append("]");
		return builder.toString();
	}

	public BigDecimal getLongTrades() {
		return longTrades;
	}

	public void setLongTrades(BigDecimal longTrades) {
		this.longTrades = longTrades;
	}

	public BigDecimal getLongVolume() {
		return longVolume;
	}

	public void setLongVolume(BigDecimal longVolume) {
		this.longVolume = longVolume;
	}

	public BigDecimal getLongAmount() {
		return longAmount;
	}

	public void setLongAmount(BigDecimal longAmount) {
		this.longAmount = longAmount;
	}

	public BigDecimal getShortTrades() {
		return shortTrades;
	}

	public void setShortTrades(BigDecimal shortTrades) {
		this.shortTrades = shortTrades;
	}

	public BigDecimal getShortVolume() {
		return shortVolume;
	}

	public void setShortVolume(BigDecimal shortVolume) {
		this.shortVolume = shortVolume;
	}

	public BigDecimal getShortAmount() {
		return shortAmount;
	}

	public void setShortAmount(BigDecimal shortAmount) {
		this.shortAmount = shortAmount;
	}

}
