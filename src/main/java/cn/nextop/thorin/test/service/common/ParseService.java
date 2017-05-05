package cn.nextop.thorin.test.service.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import cn.nextop.test.parse.ParseUtils;
import cn.nextop.thorin.core.common.glossary.TriggerPolicy;
import cn.nextop.thorin.test.service.common.po.holding.BookPoService;
import cn.nextop.thorin.test.service.common.po.trigger.MulCoverTriggerInstancePoService;
import cn.nextop.thorin.test.vo.id.obj.derivative.TriggerId;

@Service
public class ParseService {

	@Autowired
	protected CommonService commonService;
	@Autowired
	protected BookPoService bookPoService;
	@Autowired
	protected MulCoverTriggerInstancePoService mulCoverTriggerInstancePoService;

	private static final byte CO_ID = 1;

	public short toLpId(String itName) {
		if (!StringUtils.hasText(itName)) {
			return 0;
		}
		return commonService.getDefaultLpId(itName);
	}

	public int toBookId(String bookAbbr) {
		if (!StringUtils.hasText(bookAbbr)) {
			return 0;
		}
		return bookPoService.getBookId(CO_ID, bookAbbr);
	}

	public long toTimestamp(String seconds) {
		if (!StringUtils.hasText(seconds)) {
			return 0;
		}
		return System.currentTimeMillis() + (Long.valueOf(seconds) * 1000L);
	}

	// coId:symbol:policy:tseq
	// 1:USDJPY:MUL_SPREAD:0
	public long toTriggerId(String text) {
		if (!StringUtils.hasText(text)) {
			return 0;
		}
		String[] split = text.split(":");
		byte coId = ParseUtils.parseString(split[0], byte.class);
		String symbolName = ParseUtils.parseString(split[1], String.class);
		TriggerPolicy triggerPolicy = ParseUtils.parseString(split[2], TriggerPolicy.class);
		int tseq = ParseUtils.parseString(split[3], int.class);
		short symbolId = commonService.getSymbolId(symbolName);
		// 
		return mulCoverTriggerInstancePoService.toTriggerId(coId, symbolId, triggerPolicy, tseq);
	}

	// coId:symbol:policy:tseq
	// 1:USDJPY:MUL_SPREAD:0
	public TriggerId toTriggerIdObj(String text) {
		return TriggerId.of(this.toTriggerId(text));
	}

}
