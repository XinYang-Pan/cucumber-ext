package cn.nextop.thorin.test.vo.id.obj.compound;

import cn.nextop.thorin.core.common.domain.CommonGenerator;
import cn.nextop.thorin.test.vo.id.has.compound.HasLpSymbolId;

public class LpSymbolId extends SuperId implements HasLpSymbolId {

	public LpSymbolId(byte itId, byte lpSeq, byte coId, short symbolId) {
		super(itId, lpSeq, coId, (byte) 0, symbolId);
	}

	// -----------------------------
	// ----- Static
	// -----------------------------

	public static LpSymbolId toLpSymbolId(byte itId, byte lpSeq, byte coId, short symbolId) {
		return new LpSymbolId(itId, lpSeq, coId, symbolId);
	}

	public static LpSymbolId toLpSymbolId(short lpId, short symbolId) {
		byte itId = CommonGenerator.getItIdByLpId(lpId);
		byte lpSeq = CommonGenerator.getSequenceByLpId(lpId);
		byte coId = CommonGenerator.getCompanyIdByLpId(lpId);
		return toLpSymbolId(itId, lpSeq, coId, symbolId);
	}

	public static LpSymbolId toLpSymbolId(int lpSymbolId) {
		short lpId = CommonGenerator.getLpIdByLpSymbolId(lpSymbolId);
		short symbolId = CommonGenerator.getSymbolIdByLpSymbolId(lpSymbolId);
		return toLpSymbolId(lpId, symbolId);
	}

	public static LpSymbolId toLpSymbolIdWithDefault(byte itId, byte coId, short symbolId) {
		return new LpSymbolId(itId, (byte) 0, coId, symbolId);
	}

	// -----------------------------
	// ----- methods
	// -----------------------------

	public byte itId() {
		return itId;
	}

	public byte lpSeq() {
		return lpSeq;
	}

	public byte coId() {
		return coId;
	}

	public short symbolId() {
		return symbolId;
	}

}
