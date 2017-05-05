package cn.nextop.thorin.test.util;

import java.util.List;

import cn.nextop.thorin.test.emulator.bo.GwGroup;
import cn.nextop.thorin.test.emulator.bo.GwQuoteIncr;

public class HedgerTestUtils {

	public static GwQuoteIncr buildGwQuoteIncr(String itName, List<GwGroup> gwGroups) {
		// 
		GwQuoteIncr gwQuoteFull = new GwQuoteIncr();
		gwQuoteFull.setItName(itName);
		gwQuoteFull.setGroups(gwGroups);
		return gwQuoteFull;
	}

}
