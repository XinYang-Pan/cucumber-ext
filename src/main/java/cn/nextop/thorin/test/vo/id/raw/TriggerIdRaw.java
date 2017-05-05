package cn.nextop.thorin.test.vo.id.raw;

import org.springframework.util.Assert;

import cn.nextop.test.parse.ParseUtils;
import cn.nextop.thorin.core.common.glossary.TriggerPolicy;
import cn.nextop.thorin.test.vo.id.has.single.HasCoId;

// 1:USDJPY:TVR_SHADE:0
public class TriggerIdRaw implements HasCoId {
//	private String rawString;
	private final byte coId;
	private final String symbolName;
	private final TriggerPolicy triggerPolicy;
	private final int tseq;

	public TriggerIdRaw(String rawString) {
		Assert.hasText(rawString);
		//
		String[] split = rawString.split(":");
		coId = ParseUtils.parseString(split[0], byte.class);
		symbolName = ParseUtils.parseString(split[1], String.class);
		triggerPolicy = ParseUtils.parseString(split[2], TriggerPolicy.class);
		tseq = ParseUtils.parseString(split[3], int.class);
	}

	@Override
	public byte coId() {
		return coId;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TriggerIdRaw [coId=");
		builder.append(coId);
		builder.append(", symbolName=");
		builder.append(symbolName);
		builder.append(", triggerPolicy=");
		builder.append(triggerPolicy);
		builder.append(", tseq=");
		builder.append(tseq);
		builder.append("]");
		return builder.toString();
	}

	public byte getCoId() {
		return coId;
	}

	public String getSymbolName() {
		return symbolName;
	}

	public TriggerPolicy getTriggerPolicy() {
		return triggerPolicy;
	}

	public int getTseq() {
		return tseq;
	}

}
