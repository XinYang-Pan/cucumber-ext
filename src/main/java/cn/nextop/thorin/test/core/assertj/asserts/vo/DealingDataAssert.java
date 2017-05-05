package cn.nextop.thorin.test.core.assertj.asserts.vo;

import cn.nextop.test.assertj.AssertjUtils;
import cn.nextop.test.assertj.asserts.AssertjAbstractAssert;
import cn.nextop.thorin.test.vo.dlrapi.DealingData;

public class DealingDataAssert extends AssertjAbstractAssert<DealingDataAssert, DealingData> {

	public DealingDataAssert(DealingData actual) {
		super(actual, DealingDataAssert.class);
	}

	public DealingDataAssert isContentEqualTo(DealingData expected) {
		// 
		AssertjUtils.exactlyInAnyOrder(actual.getDealingOrderPos(), expected.getDealingOrderPos());
		AssertjUtils.exactlyInAnyOrder(actual.getDealingLadderPos(), expected.getDealingLadderPos());
		AssertjUtils.exactlyInAnyOrder(actual.getDealingPositionPos(), expected.getDealingPositionPos());
		AssertjUtils.exactlyInAnyOrder(actual.getDealingCounterpartyPos(), expected.getDealingCounterpartyPos());
		return this;
	}

}
