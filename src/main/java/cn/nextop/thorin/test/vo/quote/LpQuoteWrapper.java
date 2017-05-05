package cn.nextop.thorin.test.vo.quote;

import java.util.concurrent.atomic.AtomicInteger;

import cn.nextop.thorin.core.pricing.domain.entity.impl.LpQuoteImpl;

public class LpQuoteWrapper {
	private static AtomicInteger ID_GEN = new AtomicInteger(1);
	private final int messageId = ID_GEN.getAndIncrement();
	private long delay;
	private LpQuoteImpl lpQuote;

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("LpQuoteWrapper [messageId=");
		builder.append(messageId);
		builder.append(", delay=");
		builder.append(delay);
		builder.append(", lpQuote=");
		builder.append(lpQuote);
		builder.append("]");
		return builder.toString();
	}

	public LpQuoteImpl getLpQuote() {
		return lpQuote;
	}

	public void setLpQuote(LpQuoteImpl lpQuote) {
		this.lpQuote = lpQuote;
	}

	public long getDelay() {
		return delay;
	}

	public void setDelay(long delay) {
		this.delay = delay;
	}

	public int getMessageId() {
		return messageId;
	}

}
