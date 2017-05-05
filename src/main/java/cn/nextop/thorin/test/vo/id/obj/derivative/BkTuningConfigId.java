package cn.nextop.thorin.test.vo.id.obj.derivative;

import cn.nextop.thorin.core.common.glossary.PricingTuningPolicy;
import cn.nextop.thorin.core.pricing.domain.PricingGenerator;
import cn.nextop.thorin.test.vo.id.has.compound.HasBkSymbolId;
import cn.nextop.thorin.test.vo.id.has.derivative.HasBkTuningConfigId;
import cn.nextop.thorin.test.vo.id.obj.compound.BkSymbolId;

public class BkTuningConfigId extends BkSymbolId implements HasBkTuningConfigId {
	private final PricingTuningPolicy pricingTuningPolicy;

	public BkTuningConfigId(byte coId, byte bkSeq, short symbolId, PricingTuningPolicy pricingTuningPolicy) {
		super(coId, bkSeq, symbolId);
		this.pricingTuningPolicy = pricingTuningPolicy;
	}

	// -----------------------------
	// ----- Static
	// -----------------------------

	public static BkTuningConfigId toBkTuningConfigId(byte coId, byte bkSeq, short symbolId, PricingTuningPolicy pricingTuningPolicy) {
		return new BkTuningConfigId(coId, bkSeq, symbolId, pricingTuningPolicy);
	}

	public static BkTuningConfigId toBkTuningConfigId(long bkTuningConfigId) {
		short brokerId = PricingGenerator.getBrokerIdByBkTuningConfigId(bkTuningConfigId);
		short symbolId = PricingGenerator.getSymbolIdByBkTuningConfigId(bkTuningConfigId);
		PricingTuningPolicy policy = PricingGenerator.getPolicyByBkTuningConfigId(bkTuningConfigId);
		BkSymbolId bkSymbolId = BkSymbolId.toBkSymbolId(brokerId, symbolId);
		return toBkTuningConfigId(bkSymbolId, policy);
	}

	public static BkTuningConfigId toBkTuningConfigId(HasBkSymbolId bkSymbolId, PricingTuningPolicy pricingTuningPolicy) {
		return toBkTuningConfigId(bkSymbolId.coId(), bkSymbolId.bkSeq(), bkSymbolId.symbolId(), pricingTuningPolicy);
	}

	// -----------------------------
	// ----- methods
	// -----------------------------

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("BkTuningConfigId [pricingTuningPolicy=");
		builder.append(pricingTuningPolicy);
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

	public PricingTuningPolicy pricingTuningPolicy() {
		return pricingTuningPolicy;
	}

}
