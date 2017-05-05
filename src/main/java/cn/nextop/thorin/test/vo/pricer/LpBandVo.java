package cn.nextop.thorin.test.vo.pricer;

import org.apache.commons.lang3.ObjectUtils;

import cn.nextop.thorin.core.common.domain.glossary.decimal.Price;
import cn.nextop.thorin.core.common.glossary.BidAsk;
import cn.nextop.thorin.core.common.glossary.PricingBandStatus;
import cn.nextop.thorin.core.common.glossary.PricingBandType;

public class LpBandVo {
	private int index;
	private BidAsk side;
	private PricingBandType type;
	private Price price;
	private Integer volume;
	private PricingBandStatus status;


	public PricingBandType getType() {
		return ObjectUtils.defaultIfNull(type, PricingBandType.NEW);
	}

	public PricingBandStatus getStatus() {
		return ObjectUtils.defaultIfNull(status, PricingBandStatus.ACTIVE);
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("LpBandVo [index=");
		builder.append(index);
		builder.append(", side=");
		builder.append(side);
		builder.append(", type=");
		builder.append(type);
		builder.append(", price=");
		builder.append(price);
		builder.append(", volume=");
		builder.append(volume);
		builder.append(", status=");
		builder.append(status);
		builder.append("]");
		return builder.toString();
	}

	public BidAsk getSide() {
		return side;
	}

	public void setSide(BidAsk side) {
		this.side = side;
	}

	public void setType(PricingBandType type) {
		this.type = type;
	}

	public Price getPrice() {
		return price;
	}

	public void setPrice(Price price) {
		this.price = price;
	}

	public Integer getVolume() {
		return volume;
	}

	public void setVolume(Integer volume) {
		this.volume = volume;
	}

	public void setStatus(PricingBandStatus status) {
		this.status = status;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

}
