package cn.nextop.thorin.test.vo.id.has.compound;

import cn.nextop.thorin.core.common.domain.CommonGenerator;
import cn.nextop.thorin.test.vo.id.has.single.HasSymbolId;

public interface HasBkSymbolId extends HasBkId, HasSymbolId, HasCoSymbolId {
	
	default int bkSymbolId() {
		return CommonGenerator.toBkSymbolId(bkId(), symbolId());
	}
	
}
