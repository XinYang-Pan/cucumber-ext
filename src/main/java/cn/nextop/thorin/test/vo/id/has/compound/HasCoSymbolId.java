package cn.nextop.thorin.test.vo.id.has.compound;

import cn.nextop.thorin.core.common.domain.CommonGenerator;
import cn.nextop.thorin.test.vo.id.has.single.HasCoId;
import cn.nextop.thorin.test.vo.id.has.single.HasSymbolId;

public interface HasCoSymbolId extends HasCoId, HasSymbolId {
	
	default int coSymbolId() {
		return CommonGenerator.toCoSymbolId(coId(), symbolId());
	}
	
}
