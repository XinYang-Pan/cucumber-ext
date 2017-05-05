package cn.nextop.thorin.test.vo.id.has.compound;

import cn.nextop.thorin.core.common.domain.CommonGenerator;
import cn.nextop.thorin.test.vo.id.has.single.HasCoId;
import cn.nextop.thorin.test.vo.id.has.single.HasItId;
import cn.nextop.thorin.test.vo.id.has.single.HasLpSeq;

public interface HasLpId extends HasItId, HasLpSeq, HasCoId {
	
	default short lpId() {
		return CommonGenerator.toLpId(coId(), itId(), lpSeq());
	}
	
}
	