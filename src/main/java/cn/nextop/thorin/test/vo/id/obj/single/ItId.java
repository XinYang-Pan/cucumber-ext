package cn.nextop.thorin.test.vo.id.obj.single;

import cn.nextop.thorin.test.vo.id.has.single.HasItId;

public class ItId implements HasItId {
	private final byte itId;

	public ItId(byte itId) {
		super();
		this.itId = itId;
	}

	@Override
	public byte itId() {
		return itId;
	}
	
}
