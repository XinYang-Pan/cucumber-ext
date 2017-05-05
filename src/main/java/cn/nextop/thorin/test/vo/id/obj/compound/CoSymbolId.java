package cn.nextop.thorin.test.vo.id.obj.compound;

import cn.nextop.thorin.core.common.domain.CommonGenerator;
import cn.nextop.thorin.test.vo.id.has.compound.HasCoSymbolId;

public class CoSymbolId extends SuperId implements HasCoSymbolId {

	public CoSymbolId(byte coId, short symbolId) {
		super((byte) 0, (byte) 0, coId, (byte) 0, symbolId);
	}
	
	// -----------------------------
	// ----- Static
	// -----------------------------

	public static CoSymbolId toCoSymbolId(byte coId, short symbolId) {
		return new CoSymbolId(coId, symbolId);
	}

	public static CoSymbolId toCoSymbolId(int coSymbolId) {
		byte coId = CommonGenerator.getCompanyIdByCoSymbolId(coSymbolId);
		short symbolId = CommonGenerator.getSymbolIdByCoSymbolId(coSymbolId);
		return toCoSymbolId(coId, symbolId);
	}

	// -----------------------------
	// ----- methods
	// -----------------------------

	public byte coId() {
		return coId;
	}

	public short symbolId() {
		return symbolId;
	}

}
