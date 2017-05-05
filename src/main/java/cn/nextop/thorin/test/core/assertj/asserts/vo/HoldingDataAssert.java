package cn.nextop.thorin.test.core.assertj.asserts.vo;

import cn.nextop.test.assertj.AssertjUtils;
import cn.nextop.test.assertj.asserts.AssertjAbstractAssert;
import cn.nextop.thorin.test.vo.dlrapi.HoldingData;

public class HoldingDataAssert extends AssertjAbstractAssert<HoldingDataAssert, HoldingData> {

	public HoldingDataAssert(HoldingData actual) {
		super(actual, HoldingDataAssert.class);
	}

	public HoldingDataAssert isContentEqualTo(HoldingData expected) {
		// 
		AssertjUtils.exactlyInAnyOrder(actual.getBookPos(), expected.getBookPos());
		AssertjUtils.exactlyInAnyOrder(actual.getBooksPos(), expected.getBooksPos());
		AssertjUtils.exactlyInAnyOrder(actual.getAccountsPos(), expected.getAccountsPos());
		AssertjUtils.exactlyInAnyOrder(actual.getAllocationPos(), expected.getAllocationPos());
		AssertjUtils.exactlyInAnyOrder(actual.getHoldingStrategyPos(), expected.getHoldingStrategyPos());

		AssertjUtils.exactlyInAnyOrder(actual.getBooksConfigPos(), expected.getBooksConfigPos());
		AssertjUtils.exactlyInAnyOrder(actual.getAccountsConfigPos(), expected.getAccountsConfigPos());
		AssertjUtils.exactlyInAnyOrder(actual.getBookQuoteConfigPos(), expected.getBookQuoteConfigPos());

		AssertjUtils.exactlyInAnyOrder(actual.getAllocationBookConfigPos(), expected.getAllocationBookConfigPos());
		AssertjUtils.exactlyInAnyOrder(actual.getAllocationBrokerConfigPos(), expected.getAllocationBrokerConfigPos());
		AssertjUtils.exactlyInAnyOrder(actual.getAllocationSymbolConfigPos(), expected.getAllocationSymbolConfigPos());
		AssertjUtils.exactlyInAnyOrder(actual.getAllocationAccountsConfigPos(), expected.getAllocationAccountsConfigPos());
		return this;
	}

}
