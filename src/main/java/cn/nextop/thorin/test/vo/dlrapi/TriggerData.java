package cn.nextop.thorin.test.vo.dlrapi;

import static com.google.common.collect.Lists.transform;

import java.util.List;

import cn.nextop.thorin.api.dealer.proto.TriggerServiceProtoV1.TriggerEvent;
import cn.nextop.thorin.api.dealer.service.trigger.v1.model.TriggerSummaryV1Factory;
import cn.nextop.thorin.api.dealer.service.trigger.v1.model.ext.MulSpreadTriggerInstanceV1Factory;
import cn.nextop.thorin.api.dealer.service.trigger.v1.model.ext.SmaShadeTriggerInstanceV1Factory;
import cn.nextop.thorin.api.dealer.service.trigger.v1.model.ext.TvrShadeTriggerInstanceV1Factory;
import cn.nextop.thorin.core.trigger.orm.po.MulSpreadTriggerInstancePo;
import cn.nextop.thorin.core.trigger.orm.po.SmaShadeTriggerInstancePo;
import cn.nextop.thorin.core.trigger.orm.po.TriggerSummaryPo;
import cn.nextop.thorin.core.trigger.orm.po.TvrShadeTriggerInstancePo;

public class TriggerData {
	private List<SmaShadeTriggerInstancePo> smaShadeTriggerInstancePos;
	private List<TvrShadeTriggerInstancePo> tvrShadeTriggerInstancePos;
	private List<MulSpreadTriggerInstancePo> mulSpreadTriggerInstancePos;
	private List<TriggerSummaryPo> triggerSummaryPos;

	public static TriggerData from(TriggerEvent triggerEvent) {
		TriggerData triggerData = new TriggerData();
		triggerData.setSmaShadeTriggerInstancePos(transform(triggerEvent.getSmaShadeInstancesList(), SmaShadeTriggerInstanceV1Factory::toSmaShadeInstance));
		triggerData.setTvrShadeTriggerInstancePos(transform(triggerEvent.getTvrShadeInstancesList(), TvrShadeTriggerInstanceV1Factory::toTvrShadeInstance));
		triggerData.setMulSpreadTriggerInstancePos(transform(triggerEvent.getMulSpreadInstancesList(), MulSpreadTriggerInstanceV1Factory::toMulSpreadInstance));
		triggerData.setTriggerSummaryPos(transform(triggerEvent.getSummariesList(), TriggerSummaryV1Factory::toTriggerSummary));
		return triggerData;
	}

	// -----------------------------
	// ----- Get Set ToString HashCode Equals
	// -----------------------------

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TriggerData [smaShadeTriggerInstancePos=");
		builder.append(smaShadeTriggerInstancePos);
		builder.append(", tvrShadeTriggerInstancePos=");
		builder.append(tvrShadeTriggerInstancePos);
		builder.append(", mulSpreadTriggerInstancePos=");
		builder.append(mulSpreadTriggerInstancePos);
		builder.append(", triggerSummaryPos=");
		builder.append(triggerSummaryPos);
		builder.append("]");
		return builder.toString();
	}

	public List<SmaShadeTriggerInstancePo> getSmaShadeTriggerInstancePos() {
		return smaShadeTriggerInstancePos;
	}

	public void setSmaShadeTriggerInstancePos(List<SmaShadeTriggerInstancePo> smaShadeTriggerInstancePos) {
		this.smaShadeTriggerInstancePos = smaShadeTriggerInstancePos;
	}

	public List<TvrShadeTriggerInstancePo> getTvrShadeTriggerInstancePos() {
		return tvrShadeTriggerInstancePos;
	}

	public void setTvrShadeTriggerInstancePos(List<TvrShadeTriggerInstancePo> tvrShadeTriggerInstancePos) {
		this.tvrShadeTriggerInstancePos = tvrShadeTriggerInstancePos;
	}

	public List<MulSpreadTriggerInstancePo> getMulSpreadTriggerInstancePos() {
		return mulSpreadTriggerInstancePos;
	}

	public void setMulSpreadTriggerInstancePos(List<MulSpreadTriggerInstancePo> mulSpreadTriggerInstancePos) {
		this.mulSpreadTriggerInstancePos = mulSpreadTriggerInstancePos;
	}

	public List<TriggerSummaryPo> getTriggerSummaryPos() {
		return triggerSummaryPos;
	}

	public void setTriggerSummaryPos(List<TriggerSummaryPo> triggerSummaryPos) {
		this.triggerSummaryPos = triggerSummaryPos;
	}

}
