package cn.nextop.thorin.test.vo.id.obj.single;

import cn.nextop.thorin.test.vo.id.has.single.HasBkSeq;

public class BkSeq implements HasBkSeq {
	private final byte bkSeq;

	public BkSeq(byte bkSeq) {
		super();
		this.bkSeq = bkSeq;
	}

	@Override
	public byte bkSeq() {
		return bkSeq;
	}
	
}
