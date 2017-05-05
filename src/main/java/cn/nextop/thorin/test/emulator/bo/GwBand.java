package cn.nextop.thorin.test.emulator.bo;

import cn.nextop.thorin.core.common.glossary.BidAsk;
import quickfix.field.QuoteCondition;

public class GwBand {
	private BidAsk bidAsk;
	private String quoteEntryId;
	private String quoteCondition = QuoteCondition.OPEN_ACTIVE;
	private Double price;
	private Integer volumn = 10000;

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("GwBand [bidAsk=");
		builder.append(bidAsk);
		builder.append(", quoteEntryId=");
		builder.append(quoteEntryId);
		builder.append(", quoteCondition=");
		builder.append(quoteCondition);
		builder.append(", price=");
		builder.append(price);
		builder.append(", volumn=");
		builder.append(volumn);
		builder.append("]");
		return builder.toString();
	}

	public String getQuoteEntryId() {
		return quoteEntryId;
	}

	public void setQuoteEntryId(String quoteEntryId) {
		this.quoteEntryId = quoteEntryId;
	}

	public String getQuoteCondition() {
		return quoteCondition;
	}

	public void setQuoteCondition(String quoteCondition) {
		this.quoteCondition = quoteCondition;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public BidAsk getBidAsk() {
		return bidAsk;
	}

	public void setBidAsk(BidAsk bidAsk) {
		this.bidAsk = bidAsk;
	}

	public Integer getVolumn() {
		return volumn;
	}

	public void setVolumn(Integer size) {
		this.volumn = size;
	}

}
