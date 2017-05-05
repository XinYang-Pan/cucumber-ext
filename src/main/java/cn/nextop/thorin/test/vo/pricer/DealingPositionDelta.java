package cn.nextop.thorin.test.vo.pricer;

import java.math.BigDecimal;

import cn.nextop.thorin.core.dealing.orm.po.DealingPositionPo;

public class DealingPositionDelta {

	private BigDecimal tradeAmount;
	private BigDecimal hedgeAmount;
	private BigDecimal brokerAmount;
	private BigDecimal marketAmount;
	private BigDecimal hedgingVolume;
	private BigDecimal exposureVolume;
	private BigDecimal brokerLongVolume;
	private BigDecimal brokerShortVolume;

	public static DealingPositionDelta delta(DealingPositionPo old, DealingPositionPo now) {
		DealingPositionDelta delta = new DealingPositionDelta();
		if (old == null && now == null) {
			return delta;
		}
		if (old == null) {
			delta.setTradeAmount(now.getTradeAmount());
			delta.setHedgeAmount(now.getHedgeAmount());
			delta.setBrokerAmount(now.getBrokerAmount());
			delta.setMarketAmount(now.getMarketAmount());
			delta.setHedgingVolume(now.getHedgingVolume());
			delta.setExposureVolume(now.getExposureVolume());
			delta.setBrokerLongVolume(now.getBrokerLongVolume());
			delta.setBrokerShortVolume(now.getBrokerShortVolume());
		} else {
			delta.setTradeAmount(now.getTradeAmount().subtract(old.getTradeAmount()));
			delta.setHedgeAmount(now.getHedgeAmount().subtract(old.getHedgeAmount()));
			delta.setBrokerAmount(now.getBrokerAmount().subtract(old.getBrokerAmount()));
			delta.setMarketAmount(now.getMarketAmount().subtract(old.getMarketAmount()));
			delta.setHedgingVolume(now.getHedgingVolume().subtract(old.getHedgingVolume()));
			delta.setExposureVolume(now.getExposureVolume().subtract(old.getExposureVolume()));
			delta.setBrokerLongVolume(now.getBrokerLongVolume().subtract(old.getBrokerLongVolume()));
			delta.setBrokerShortVolume(now.getBrokerShortVolume().subtract(old.getBrokerShortVolume()));
		}
		return delta;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DealingPositionDelta [tradeAmount=");
		builder.append(tradeAmount);
		builder.append(", hedgeAmount=");
		builder.append(hedgeAmount);
		builder.append(", brokerAmount=");
		builder.append(brokerAmount);
		builder.append(", marketAmount=");
		builder.append(marketAmount);
		builder.append(", hedgingVolume=");
		builder.append(hedgingVolume);
		builder.append(", exposureVolume=");
		builder.append(exposureVolume);
		builder.append(", brokerLongVolume=");
		builder.append(brokerLongVolume);
		builder.append(", brokerShortVolume=");
		builder.append(brokerShortVolume);
		builder.append("]");
		return builder.toString();
	}

	public BigDecimal getTradeAmount() {
		return tradeAmount;
	}

	public void setTradeAmount(BigDecimal tradeAmount) {
		this.tradeAmount = tradeAmount;
	}

	public BigDecimal getHedgeAmount() {
		return hedgeAmount;
	}

	public void setHedgeAmount(BigDecimal hedgeAmount) {
		this.hedgeAmount = hedgeAmount;
	}

	public BigDecimal getBrokerAmount() {
		return brokerAmount;
	}

	public void setBrokerAmount(BigDecimal brokerAmount) {
		this.brokerAmount = brokerAmount;
	}

	public BigDecimal getMarketAmount() {
		return marketAmount;
	}

	public void setMarketAmount(BigDecimal marketAmount) {
		this.marketAmount = marketAmount;
	}

	public BigDecimal getHedgingVolume() {
		return hedgingVolume;
	}

	public void setHedgingVolume(BigDecimal hedgingVolume) {
		this.hedgingVolume = hedgingVolume;
	}

	public BigDecimal getExposureVolume() {
		return exposureVolume;
	}

	public void setExposureVolume(BigDecimal exposureVolume) {
		this.exposureVolume = exposureVolume;
	}

	public BigDecimal getBrokerLongVolume() {
		return brokerLongVolume;
	}

	public void setBrokerLongVolume(BigDecimal brokerLongVolume) {
		this.brokerLongVolume = brokerLongVolume;
	}

	public BigDecimal getBrokerShortVolume() {
		return brokerShortVolume;
	}

	public void setBrokerShortVolume(BigDecimal brokerShortVolume) {
		this.brokerShortVolume = brokerShortVolume;
	}

}
