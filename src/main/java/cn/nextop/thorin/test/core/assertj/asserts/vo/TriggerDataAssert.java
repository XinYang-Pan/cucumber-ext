package cn.nextop.thorin.test.core.assertj.asserts.vo;

import static org.assertj.core.api.Assertions.assertThat;

import cn.nextop.test.assertj.AssertjUtils;
import cn.nextop.test.assertj.asserts.AssertjAbstractAssert;
import cn.nextop.thorin.core.trigger.orm.po.MulSpreadTriggerInstancePo;
import cn.nextop.thorin.core.trigger.orm.po.SmaShadeTriggerInstancePo;
import cn.nextop.thorin.core.trigger.orm.po.TvrShadeTriggerInstancePo;
import cn.nextop.thorin.test.vo.dlrapi.TriggerData;

public class TriggerDataAssert extends AssertjAbstractAssert<TriggerDataAssert, TriggerData> {

	public TriggerDataAssert(TriggerData actual) {
		super(actual, TriggerDataAssert.class);
	}

	public TriggerDataAssert isContentEqualTo(TriggerData expected) {
		// 
		AssertjUtils.exactlyInAnyOrder(actual.getSmaShadeTriggerInstancePos(), expected.getSmaShadeTriggerInstancePos());
		AssertjUtils.exactlyInAnyOrder(actual.getTvrShadeTriggerInstancePos(), expected.getTvrShadeTriggerInstancePos());
		AssertjUtils.exactlyInAnyOrder(actual.getMulSpreadTriggerInstancePos(), expected.getMulSpreadTriggerInstancePos());
		return this;
	}

	public TriggerDataAssert hasOnly(SmaShadeTriggerInstancePo... pos) {
		assertThat(actual.getSmaShadeTriggerInstancePos()).hasSameSizeAs(pos);
		assertThat(actual.getTvrShadeTriggerInstancePos()).hasSize(0);
		assertThat(actual.getMulSpreadTriggerInstancePos()).hasSize(0);
		// 
		AssertjUtils.exactlyInAnyOrder(actual.getSmaShadeTriggerInstancePos(), pos);
		return this;
	}

	public TriggerDataAssert hasOnly(TvrShadeTriggerInstancePo... pos) {
		assertThat(actual.getSmaShadeTriggerInstancePos()).hasSize(0);
		assertThat(actual.getTvrShadeTriggerInstancePos()).hasSameSizeAs(pos);
		assertThat(actual.getMulSpreadTriggerInstancePos()).hasSize(0);
		// 
		AssertjUtils.exactlyInAnyOrder(actual.getTvrShadeTriggerInstancePos(), pos);
		return this;
	}

	public TriggerDataAssert hasOnly(MulSpreadTriggerInstancePo... pos) {
		assertThat(actual.getSmaShadeTriggerInstancePos()).hasSize(0);
		assertThat(actual.getTvrShadeTriggerInstancePos()).hasSize(0);
		assertThat(actual.getMulSpreadTriggerInstancePos()).hasSameSizeAs(pos);
		// 
		AssertjUtils.exactlyInAnyOrder(actual.getMulSpreadTriggerInstancePos(), pos);
		return this;
	}

}
