package cn.nextop.thorin.test.vo.id.has.compound;

import cn.nextop.thorin.core.common.domain.CommonGenerator;
import cn.nextop.thorin.test.vo.id.has.single.HasSymbolId;

public interface HasLpSymbolId extends HasLpId, HasSymbolId, HasCoSymbolId {
	
	default int lpSymbolId() {
		return CommonGenerator.toLpSymbolId(lpId(), symbolId());
	}
	
}
