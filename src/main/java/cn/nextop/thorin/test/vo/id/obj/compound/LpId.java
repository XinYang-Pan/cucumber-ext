package cn.nextop.thorin.test.vo.id.obj.compound;

import cn.nextop.thorin.core.common.domain.CommonGenerator;
import cn.nextop.thorin.test.vo.id.has.compound.HasLpId;

public class LpId extends SuperId implements HasLpId {

	public LpId(byte itId, byte lpSeq, byte coId) {
		super(itId, lpSeq, coId, (byte) 0, (short) 0);
	}
	
	// -----------------------------
	// ----- Static
	// -----------------------------

	public static LpId toLpId(byte itId, byte lpSeq, byte coId) {
		return new LpId(itId, lpSeq, coId);
	}

	public static LpId toLpId(short lpId) {
		byte itId = CommonGenerator.getItIdByLpId(lpId);
		byte lpSeq = CommonGenerator.getSequenceByLpId(lpId);
		byte coId = CommonGenerator.getCompanyIdByLpId(lpId);
		return toLpId(itId, lpSeq, coId);
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

}
