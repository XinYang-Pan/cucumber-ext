package cn.nextop.thorin.test.core.assertj.delegate;

import cn.nextop.thorin.core.trigger.orm.po.DealingTriggerExecutionPo;

public class DelegateAssertions {

	public static DealingTriggerExecutionPoAssert assertThat(DealingTriggerExecutionPo actual) {
		return new DealingTriggerExecutionPoAssert(actual);
	}
	
}
