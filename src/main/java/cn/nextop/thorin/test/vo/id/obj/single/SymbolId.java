package cn.nextop.thorin.test.vo.id.obj.single;

import cn.nextop.thorin.test.vo.id.has.single.HasSymbolId;

public class SymbolId implements HasSymbolId {
	private final short symbolId;

	public SymbolId(short symbolId) {
		super();
		this.symbolId = symbolId;
	}

	@Override
	public short symbolId() {
		return symbolId;
	}
	
}
