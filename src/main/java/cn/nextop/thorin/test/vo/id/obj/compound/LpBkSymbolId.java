package cn.nextop.thorin.test.vo.id.obj.compound;

import org.springframework.util.Assert;

import cn.nextop.thorin.test.vo.id.has.compound.HasLpBkSymbolId;

public class LpBkSymbolId extends SuperId implements HasLpBkSymbolId {

	public LpBkSymbolId(byte itId, byte lpSeq, byte coId, byte bkSeq, short symbolId) {
		super(itId, lpSeq, coId, bkSeq, symbolId);
	}

	// -----------------------------
	// ----- Static
	// -----------------------------

	public static LpBkSymbolId toLpBkSymbolId(byte itId, byte lpSeq, byte coId, byte bkSeq, short symbolId) {
		return new LpBkSymbolId(itId, lpSeq, coId, bkSeq, symbolId);
	}

	public static LpBkSymbolId toLpBkSymbolId(short lpId, short bkId, short symbolId) {
		LpId lpId2 = LpId.toLpId(lpId);
		BkId bkId2 = BkId.toBkId(bkId);
		Assert.isTrue(lpId2.coId == bkId2.coId, String.format("LpId=%s, BkId=%s", lpId2.lpId(), bkId2.bkId()));
		return toLpBkSymbolId(lpId2.itId, lpId2.lpSeq, lpId2.coId, bkId2.bkSeq(), symbolId);
	}

	public static LpBkSymbolId toLpBkSymbolId(short lpId, int bkSymbolId) {
		LpId lpId2 = LpId.toLpId(lpId);
		BkSymbolId bkSymbolId2 = BkSymbolId.toBkSymbolId(bkSymbolId);
		return toLpBkSymbolId(lpId2.itId, lpId2.lpSeq, lpId2.coId, bkSymbolId2.bkSeq(), bkSymbolId2.symbolId);
	}

	public static LpBkSymbolId toLpBkSymbolId(int lpSymbolId, short bkId) {
		LpSymbolId lpSymbolId2 = LpSymbolId.toLpSymbolId(lpSymbolId);
		BkId bkId2 = BkId.toBkId(bkId);
		return toLpBkSymbolId(lpSymbolId2.itId, lpSymbolId2.lpSeq, lpSymbolId2.coId, bkId2.bkSeq(), lpSymbolId2.symbolId);
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

	public byte bkSeq() {
		return bkSeq;
	}

	public short symbolId() {
		return symbolId;
	}

}
