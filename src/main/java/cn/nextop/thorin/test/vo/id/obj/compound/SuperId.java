package cn.nextop.thorin.test.vo.id.obj.compound;

class SuperId {
	protected final byte itId;
	protected final byte lpSeq;
	protected final byte coId;
	protected final byte bkSeq;
	protected final short symbolId;

	public SuperId(byte itId, byte lpSeq, byte coId, byte bkSeq, short symbolId) {
		super();
		this.itId = itId;
		this.lpSeq = lpSeq;
		this.coId = coId;
		this.bkSeq = bkSeq;
		this.symbolId = symbolId;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("LpSymbolId [itId=");
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
	
	byte itId() {
		return itId;
	}

	byte lpSeq() {
		return lpSeq;
	}

	byte coId() {
		return coId;
	}

	byte bkSeq() {
		return bkSeq;
	}

	short symbolId() {
		return symbolId;
	}

}
