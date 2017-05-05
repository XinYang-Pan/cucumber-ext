package cn.nextop.thorin.test.vo.pricer;

import cn.nextop.thorin.core.common.domain.glossary.decimal.Price;

// BkQuoteImpl
public class BkQuoteStateVo {

	private Long spreadId;
	private Long shadeId;
	private Boolean aiSpread;
	private Boolean maSpread;
	private Boolean aiShade;
	private Boolean maShade;
	private Short aiShadeVal;
	private Short maShadeVal;
	private Short aiSpreadVal;
	private Short bidMaAdjustment;
	private Short askMaAdjustment;
	private Price bid;
	private Price ask;
	private Byte spreadPolicy;

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("BkQuoteStateVo [spreadId=");
		builder.append(spreadId);
		builder.append(", shadeId=");
		builder.append(shadeId);
		builder.append(", aiSpread=");
		builder.append(aiSpread);
		builder.append(", maSpread=");
		builder.append(maSpread);
		builder.append(", aiShade=");
		builder.append(aiShade);
		builder.append(", maShade=");
		builder.append(maShade);
		builder.append(", aiShadeVal=");
		builder.append(aiShadeVal);
		builder.append(", maShadeVal=");
		builder.append(maShadeVal);
		builder.append(", aiSpreadVal=");
		builder.append(aiSpreadVal);
		builder.append(", bidMaAdjustment=");
		builder.append(bidMaAdjustment);
		builder.append(", askMaAdjustment=");
		builder.append(askMaAdjustment);
		builder.append(", bid=");
		builder.append(bid);
		builder.append(", ask=");
		builder.append(ask);
		builder.append(", spreadPolicy=");
		builder.append(spreadPolicy);
		builder.append("]");
		return builder.toString();
	}

	public Long getSpreadId() {
		return spreadId;
	}

	public void setSpreadId(Long spreadId) {
		this.spreadId = spreadId;
	}

	public Long getShadeId() {
		return shadeId;
	}

	public void setShadeId(Long shadeId) {
		this.shadeId = shadeId;
	}

	public Boolean getAiSpread() {
		return aiSpread;
	}

	public void setAiSpread(Boolean aiSpread) {
		this.aiSpread = aiSpread;
	}

	public Boolean getMaSpread() {
		return maSpread;
	}

	public void setMaSpread(Boolean maSpread) {
		this.maSpread = maSpread;
	}

	public Boolean getAiShade() {
		return aiShade;
	}

	public void setAiShade(Boolean aiShade) {
		this.aiShade = aiShade;
	}

	public Boolean getMaShade() {
		return maShade;
	}

	public void setMaShade(Boolean maShade) {
		this.maShade = maShade;
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

	public Short getAiShadeVal() {
		return aiShadeVal;
	}

	public void setAiShadeVal(Short aiShadeVal) {
		this.aiShadeVal = aiShadeVal;
	}

	public Short getMaShadeVal() {
		return maShadeVal;
	}

	public void setMaShadeVal(Short maShadeVal) {
		this.maShadeVal = maShadeVal;
	}

	public Short getBidMaAdjustment() {
		return bidMaAdjustment;
	}

	public void setBidMaAdjustment(Short bidMaAdjustment) {
		this.bidMaAdjustment = bidMaAdjustment;
	}

	public Short getAskMaAdjustment() {
		return askMaAdjustment;
	}

	public void setAskMaAdjustment(Short askMaAdjustment) {
		this.askMaAdjustment = askMaAdjustment;
	}

	public Byte getSpreadPolicy() {
		return spreadPolicy;
	}

	public void setSpreadPolicy(Byte spreadPolicy) {
		this.spreadPolicy = spreadPolicy;
	}

	public Short getAiSpreadVal() {
		return aiSpreadVal;
	}

	public void setAiSpreadVal(Short aiSpreadVal) {
		this.aiSpreadVal = aiSpreadVal;
	}

}
