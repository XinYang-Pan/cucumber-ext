package cn.nextop.thorin.test.vo.pricer;

public class DeviationStatusVo {
	private Boolean quote;
	private Boolean quoteBid;
	private Boolean quoteAsk;
	private Boolean systemBid;
	private Boolean systemAsk;

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DevStatusVo [quote=");
		builder.append(quote);
		builder.append(", quoteBid=");
		builder.append(quoteBid);
		builder.append(", quoteAsk=");
		builder.append(quoteAsk);
		builder.append(", systemBid=");
		builder.append(systemBid);
		builder.append(", systemAsk=");
		builder.append(systemAsk);
		builder.append("]");
		return builder.toString();
	}

	public Boolean getQuote() {
		return quote;
	}

	public void setQuote(Boolean quote) {
		this.quote = quote;
	}

	public Boolean getQuoteBid() {
		return quoteBid;
	}

	public void setQuoteBid(Boolean bid) {
		this.quoteBid = bid;
	}

	public Boolean getQuoteAsk() {
		return quoteAsk;
	}

	public void setQuoteAsk(Boolean ask) {
		this.quoteAsk = ask;
	}

	public Boolean getSystemBid() {
		return systemBid;
	}

	public void setSystemBid(Boolean systemBid) {
		this.systemBid = systemBid;
	}

	public Boolean getSystemAsk() {
		return systemAsk;
	}

	public void setSystemAsk(Boolean systemAsk) {
		this.systemAsk = systemAsk;
	}

}
