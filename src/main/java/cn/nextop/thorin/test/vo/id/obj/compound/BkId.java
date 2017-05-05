package cn.nextop.thorin.test.vo.id.obj.compound;

import cn.nextop.thorin.core.common.domain.CommonGenerator;
import cn.nextop.thorin.test.vo.id.has.compound.HasBkId;

public class BkId extends SuperId implements HasBkId {
	
	public BkId(byte coId, byte bkSeq) {
		super((byte) 0, (byte) 0, coId, bkSeq, (short) 0);
	}
	
	// -----------------------------
	// ----- Static
	// -----------------------------
	
	public static BkId toBkId(byte coId, byte bkSeq) {
		return new BkId(coId, bkSeq);
	}
	
	public static BkId toBkId(short brokerId) {
		byte coId = CommonGenerator.getCompanyIdByBrokerId(brokerId);
		byte bkSeq = CommonGenerator.getBrokerSequence(brokerId);
		return new BkId(coId, bkSeq);
	}

	// -----------------------------
	// ----- methods
	// -----------------------------
	
	public byte coId() {
		return coId;
	}

	public byte bkSeq() {
		return bkSeq;
	}

}
