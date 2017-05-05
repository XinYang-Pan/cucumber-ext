package cn.nextop.thorin.test.vo.id.obj.single;

import cn.nextop.thorin.test.vo.id.has.single.HasCoId;

public class CoId implements HasCoId {
	private final byte coId;

	public CoId(byte coId) {
		super();
		this.coId = coId;
	}

	@Override
	public byte coId() {
		return coId;
	}
	
}
