package cn.nextop.thorin.test.vo.id.obj.derivative;

import cn.nextop.thorin.core.common.glossary.PricingPickingPolicy;
import cn.nextop.thorin.core.pricing.domain.PricingGenerator;
import cn.nextop.thorin.test.vo.id.has.compound.HasLpBkSymbolId;
import cn.nextop.thorin.test.vo.id.has.derivative.HasBkPickingConfigId;
import cn.nextop.thorin.test.vo.id.obj.compound.LpBkSymbolId;

public class BkPickingConfigId extends LpBkSymbolId implements HasBkPickingConfigId {
	private final PricingPickingPolicy pricingPickingPolicy;
	
	public BkPickingConfigId(byte itId, byte lpSeq, byte coId, byte bkSeq, short symbolId, PricingPickingPolicy pricingPickingPolicy) {
		super(itId, lpSeq, coId, bkSeq, symbolId);
		this.pricingPickingPolicy = pricingPickingPolicy;
	}

	// -----------------------------
	// ----- Static
	// -----------------------------

	public static BkPickingConfigId toBkPickingConfigId(byte itId, byte lpSeq, byte coId, byte bkSeq, short symbolId, PricingPickingPolicy pricingPickingPolicy) {
		return new BkPickingConfigId(itId, lpSeq, coId, bkSeq, symbolId, pricingPickingPolicy);
	}

	public static BkPickingConfigId toBkPickingConfigId(long bkPickingConfigId) {
		short lpId = PricingGenerator.getLpIdByBkPickingConfigId(bkPickingConfigId);
		short brokerId = PricingGenerator.getBrokerIdByBkPickingConfigId(bkPickingConfigId);
		short symbolId = PricingGenerator.getSymbolIdByBkPickingConfigId(bkPickingConfigId);
		PricingPickingPolicy policy = PricingGenerator.getPolicyByBkPickingConfigId(bkPickingConfigId);
		LpBkSymbolId lpBkSymbolId = LpBkSymbolId.toLpBkSymbolId(lpId, brokerId, symbolId);
		return toBkPickingConfigId(lpBkSymbolId, policy);
	}

	public static BkPickingConfigId toBkPickingConfigId(short lpId, int bkSymbolId, PricingPickingPolicy pricingPickingPolicy) {
		LpBkSymbolId lpBkSymbolId = LpBkSymbolId.toLpBkSymbolId(lpId, bkSymbolId);
		return toBkPickingConfigId(lpBkSymbolId, pricingPickingPolicy);
	}

	public static BkPickingConfigId toBkPickingConfigId(HasLpBkSymbolId lpBkSymbolId, PricingPickingPolicy pricingPickingPolicy) {
		return toBkPickingConfigId(lpBkSymbolId.itId(), lpBkSymbolId.lpSeq(), lpBkSymbolId.coId(), lpBkSymbolId.bkSeq(), lpBkSymbolId.symbolId(), pricingPickingPolicy);
	}

	// -----------------------------
	// ----- methods
	// -----------------------------


	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("BkPickingConfigId [pricingPickingPolicy=");
		builder.append(pricingPickingPolicy);
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

	public PricingPickingPolicy pricingPickingPolicy() {
		return pricingPickingPolicy;
	}

}
