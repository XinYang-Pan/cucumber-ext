package cn.nextop.thorin.test.vo.id.obj.single;

import cn.nextop.thorin.test.vo.id.has.single.HasLpSeq;

public class LpSeq implements HasLpSeq {
	private final byte lpSeq;

	public LpSeq(byte lpSeq) {
		super();
		this.lpSeq = lpSeq;
	}

	@Override
	public byte lpSeq() {
		return lpSeq;
	}
	
}
