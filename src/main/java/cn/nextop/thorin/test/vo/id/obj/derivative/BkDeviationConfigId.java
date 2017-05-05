package cn.nextop.thorin.test.vo.id.obj.derivative;

import cn.nextop.thorin.core.common.glossary.PricingDeviationPolicy;
import cn.nextop.thorin.core.pricing.domain.PricingGenerator;
import cn.nextop.thorin.test.vo.id.has.compound.HasBkSymbolId;
import cn.nextop.thorin.test.vo.id.has.derivative.HasBkDeviationConfigId;
import cn.nextop.thorin.test.vo.id.obj.compound.BkSymbolId;

public class BkDeviationConfigId extends BkSymbolId implements HasBkDeviationConfigId {
	private final PricingDeviationPolicy pricingDeviationPolicy;

	public BkDeviationConfigId(byte coId, byte bkSeq, short symbolId, PricingDeviationPolicy pricingDeviationPolicy) {
		super(coId, bkSeq, symbolId);
		this.pricingDeviationPolicy = pricingDeviationPolicy;
	}

	// -----------------------------
	// ----- Static
	// -----------------------------

	public static BkDeviationConfigId toBkDeviationConfigId(byte coId, byte bkSeq, short symbolId, PricingDeviationPolicy pricingDeviationPolicy) {
		return new BkDeviationConfigId(coId, bkSeq, symbolId, pricingDeviationPolicy);
	}

	public static BkDeviationConfigId toBkDeviationConfigId(long bkDeviationConfigId) {
		short brokerId = PricingGenerator.getBrokerIdByBkDeviationConfigId(bkDeviationConfigId);
		short symbolId = PricingGenerator.getSymbolIdByBkDeviationConfigId(bkDeviationConfigId);
		PricingDeviationPolicy policy = PricingGenerator.getPolicyByBkDeviationConfigId(bkDeviationConfigId);
		BkSymbolId bkSymbolId = BkSymbolId.toBkSymbolId(brokerId, symbolId);
		return toBkDeviationConfigId(bkSymbolId, policy);
	}

	public static BkDeviationConfigId toBkDeviationConfigId(HasBkSymbolId bkSymbolId, PricingDeviationPolicy pricingDeviationPolicy) {
		return toBkDeviationConfigId(bkSymbolId.coId(), bkSymbolId.bkSeq(), bkSymbolId.symbolId(), pricingDeviationPolicy);
	}

	// -----------------------------
	// ----- methods
	// -----------------------------

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("BkDeviationConfigId [pricingDeviationPolicy=");
		builder.append(pricingDeviationPolicy);
		builder.append(", itId=");
		builder.append(itId);
		builder.append(", lpSeq=");
		builder.append(lpSeq);
		builder.append(", coId=");
		builder.append(coId);
		builder.append(", bkSeq=");
		builder.append(bkSeq);
		builder.append(", symbolId=");
		builder.append(symbolId);
		builder.append("]");
		return builder.toString();
	}

	public PricingDeviationPolicy pricingDeviationPolicy() {
		return pricingDeviationPolicy;
	}

}
