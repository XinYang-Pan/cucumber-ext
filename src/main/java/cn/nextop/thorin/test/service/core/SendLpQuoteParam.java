package cn.nextop.thorin.test.service.core;

import org.springframework.util.Assert;

import cn.nextop.thorin.core.common.domain.glossary.decimal.Price;
import cn.nextop.thorin.test.vo.id.has.compound.HasLpSymbolId;

public class SendLpQuoteParam {
	private final HasLpSymbolId lpSymbolId;
	private Price bid;
	private Price ask;
	private int tplus = 0;
	private int repeat = 1;
	private boolean tradable = true;
	private boolean bidBug = false;
	private boolean askBug = false;

	// -----------------------------
	// ----- Constructors
	// -----------------------------

	public SendLpQuoteParam(HasLpSymbolId lpSymbolId) {
		super();
		this.lpSymbolId = lpSymbolId;
	}

	// -----------------------------
	// ----- Static Methods
	// -----------------------------

	public static SendLpQuoteParam newParam(HasLpSymbolId lpSymbolId) {
		return new SendLpQuoteParam(lpSymbolId);
	}

	// short name for newBuilder
	public static SendLpQuoteParam p(HasLpSymbolId lpSymbolId) {
		return newParam(lpSymbolId);
	}

	// -----------------------------
	// ----- Non-Static Methods
	// -----------------------------

	public void validate() {
		if (isAnySideBug()) {
			Assert.state(repeat == 1, "Can not repeat bug quote");
		}
	}

	public boolean isAnySideBug() {
		return bidBug || askBug;
	}
	
	public SendLpQuoteParam bid0(Price bid) {
		this.bid = bid;
		return this;
	}

	public SendLpQuoteParam ask0(Price ask) {
		this.ask = ask;
		return this;
	}

	public SendLpQuoteParam tplus(int tplus) {
		this.tplus = tplus;
		return this;
	}

	public SendLpQuoteParam repeat(int repeat) {
		this.repeat = repeat;
		return this;
	}

	public SendLpQuoteParam tradable(boolean tradable) {
		this.tradable = tradable;
		return this;
	}

	public SendLpQuoteParam bidBug() {
		return bidBug(true);
	}

	public SendLpQuoteParam bidBug(boolean bidBug) {
		this.bidBug = bidBug;
		return this;
	}

	public SendLpQuoteParam askBug() {
		return askBug(true);
	}
	
	public SendLpQuoteParam askBug(boolean askBug) {
		this.askBug = askBug;
		return this;
	}

	public SendLpQuoteParam band0(Price bid, Price ask) {
		return this.bid0(bid).ask0(ask);
	}

	public SendLpQuoteParam bothBug() {
		return this.bidBug(true).askBug(true);
	}

	// -----------------------------
	// ----- Get Set ToString HashCode Equals
	// -----------------------------

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SendLpQuoteBuilder [lpSymbolId=");
		builder.append(lpSymbolId);
		builder.append(", bid=");
		builder.append(bid);
		builder.append(", ask=");
		builder.append(ask);
		builder.append(", tplus=");
		builder.append(tplus);
		builder.append(", repeat=");
		builder.append(repeat);
		builder.append(", tradable=");
		builder.append(tradable);
		builder.append(", bidBug=");
		builder.append(bidBug);
		builder.append(", askBug=");
		builder.append(askBug);
		builder.append("]");
		return builder.toString();
	}

	public HasLpSymbolId getLpSymbolId() {
		return lpSymbolId;
	}

	public Price getBid() {
		return bid;
	}

	public void setBid(Price bid) {
		this.bid = bid;
	}

	public Price getAsk() {
		return ask;
	}

	public void setAsk(Price ask) {
		this.ask = ask;
	}

	public int getTplus() {
		return tplus;
	}

	public void setTplus(int tplus) {
		this.tplus = tplus;
	}

	public boolean isTradable() {
		return tradable;
	}

	public void setTradable(boolean tradable) {
		this.tradable = tradable;
	}

	public int getRepeat() {
		return repeat;
	}

	public void setRepeat(int repeat) {
		this.repeat = repeat;
	}

	public boolean isBidBug() {
		return bidBug;
	}

	public void setBidBug(boolean bidBug) {
		this.bidBug = bidBug;
	}

	public boolean isAskBug() {
		return askBug;
	}

	public void setAskBug(boolean askBug) {
		this.askBug = askBug;
	}

}
