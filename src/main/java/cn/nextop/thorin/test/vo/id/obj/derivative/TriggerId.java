package cn.nextop.thorin.test.vo.id.obj.derivative;

import cn.nextop.thorin.core.common.glossary.TriggerPolicy;
import cn.nextop.thorin.core.trigger.domain.TriggerGenerator;
import cn.nextop.thorin.test.vo.id.obj.compound.CoSymbolId;

// From Raw 1:USDJPY:TVR_SHADE:0
public class TriggerId extends CoSymbolId {
	private final TriggerPolicy triggerPolicy;
	private final int sequence;

	public TriggerId(byte coId, short symbolId, TriggerPolicy triggerPolicy, int sequence) {
		super(coId, symbolId);
		this.triggerPolicy = triggerPolicy;
		this.sequence = sequence;
	}

	public static TriggerId of(long triggerId) {
		byte coId = TriggerGenerator.getCompanyIdByTriggerId(triggerId);
		short symbolId = TriggerGenerator.getSymbolIdByTriggerId(triggerId);
		TriggerPolicy triggerPolicy = TriggerGenerator.getTriggerPolicyByTriggerId(triggerId);
		int sequence = TriggerGenerator.getSequenceByTriggerId(triggerId);
		return new TriggerId(coId, symbolId, triggerPolicy, sequence);
	}

	// -----------------------------
	// ----- methods
	// -----------------------------

	public long triggerId() {
		return TriggerGenerator.toTriggerId(coId, symbolId, triggerPolicy, sequence);
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TriggerIdRaw [triggerPolicy=");
		builder.append(triggerPolicy);
		builder.append(", sequence=");
		builder.append(sequence);
		builder.append(", coId=");
		builder.append(coId);
		builder.append(", symbolId=");
		builder.append(symbolId);
		builder.append("]");
		return builder.toString();
	}

	public TriggerPolicy triggerPolicy() {
		return triggerPolicy;
	}

	public int sequence() {
		return sequence;
	}

}
