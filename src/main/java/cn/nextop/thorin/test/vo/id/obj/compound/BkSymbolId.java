package cn.nextop.thorin.test.vo.id.obj.compound;

import cn.nextop.thorin.core.common.domain.CommonGenerator;
import cn.nextop.thorin.test.vo.id.has.compound.HasBkSymbolId;

public class BkSymbolId extends SuperId implements HasBkSymbolId {

	public BkSymbolId(byte coId, byte bkSeq, short symbolId) {
		super((byte)0, (byte)0, coId, bkSeq, symbolId);
	}
	
	// -----------------------------
	// ----- Static
	// -----------------------------

	public static BkSymbolId toBkSymbolId(byte coId, byte bkSeq, short symbolId) {
		return new BkSymbolId(coId, bkSeq, symbolId);
	}
	
	public static BkSymbolId toBkSymbolId(short brokerId, short symbolId) {
		BkId bkId = BkId.toBkId(brokerId);
		return toBkSymbolId(bkId.coId, bkId.bkSeq(), symbolId);
	}
	
	public static BkSymbolId toBkSymbolId(int bkSymbolId) {
		short brokerId = CommonGenerator.getBrokerIdByBkSymbolId(bkSymbolId);
		short symbolId = CommonGenerator.getSymbolIdByBkSymbolId(bkSymbolId);
		return toBkSymbolId(brokerId, symbolId);
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

	public short symbolId() {
		return symbolId;
	}

}
