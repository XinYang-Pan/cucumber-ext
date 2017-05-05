package cn.nextop.thorin.test.vo.id.obj.derivative;

import cn.nextop.thorin.core.common.glossary.PricingDeviationPolicy;
import cn.nextop.thorin.core.pricing.domain.PricingGenerator;
import cn.nextop.thorin.test.vo.id.has.compound.HasLpSymbolId;
import cn.nextop.thorin.test.vo.id.has.derivative.HasLpDeviationConfigId;
import cn.nextop.thorin.test.vo.id.obj.compound.LpSymbolId;

public class LpDeviationConfigId extends LpSymbolId implements HasLpDeviationConfigId {
	private final PricingDeviationPolicy pricingDeviationPolicy;

	public LpDeviationConfigId(byte itId, byte lpSeq, byte coId, short symbolId, PricingDeviationPolicy pricingDeviationPolicy) {
		super(itId, lpSeq, coId, symbolId);
		this.pricingDeviationPolicy = pricingDeviationPolicy;
	}

	// -----------------------------
	// ----- Static
	// -----------------------------

	public static LpDeviationConfigId toLpDeviationConfigId(byte itId, byte lpSeq, byte coId, short symbolId, PricingDeviationPolicy pricingDeviationPolicy) {
		return new LpDeviationConfigId(itId, lpSeq, coId, symbolId, pricingDeviationPolicy);
	}

	public static LpDeviationConfigId toLpDeviationConfigId(long lpDeviationConfigId) {
		short lpId = PricingGenerator.getLpIdByLpDeviationConfigId(lpDeviationConfigId);
		short symbolId = PricingGenerator.getSymbolIdByLpDeviationConfigId(lpDeviationConfigId);
		PricingDeviationPolicy policy = PricingGenerator.getPolicyByLpDeviationConfigId(lpDeviationConfigId);
		LpSymbolId lpSymbolId = LpSymbolId.toLpSymbolId(lpId, symbolId);
		return toLpDeviationConfigId(lpSymbolId, policy);
	}

	public static LpDeviationConfigId toLpDeviationConfigId(HasLpSymbolId lpSymbolId, PricingDeviationPolicy pricingDeviationPolicy) {
		return toLpDeviationConfigId(lpSymbolId.itId(), lpSymbolId.lpSeq(), lpSymbolId.coId(), lpSymbolId.symbolId(), pricingDeviationPolicy);
	}

	// -----------------------------
	// ----- methods
	// -----------------------------

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("LpDeviationId [pricingDeviationPolicy=");
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
