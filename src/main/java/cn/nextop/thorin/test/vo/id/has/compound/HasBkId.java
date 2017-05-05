package cn.nextop.thorin.test.vo.id.has.compound;

import cn.nextop.thorin.core.common.domain.CommonGenerator;
import cn.nextop.thorin.test.vo.id.has.single.HasBkSeq;
import cn.nextop.thorin.test.vo.id.has.single.HasCoId;

public interface HasBkId extends HasBkSeq, HasCoId {

	default short bkId() {
		return CommonGenerator.toBrokerId(coId(), bkSeq());
	}
	
}
