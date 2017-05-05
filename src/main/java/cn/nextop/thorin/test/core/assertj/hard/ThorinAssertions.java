package cn.nextop.thorin.test.core.assertj.hard;

import cn.nextop.test.assertj.hard.AssertjAssertions;
import cn.nextop.thorin.core.common.domain.pipeline.Response;
//import cn.nextop.thorin.core.pricing.domain.dom.pipeline.response.impl.AcquireLqtyResponse;
import cn.nextop.thorin.core.pricing.domain.entity.BkQuote;
import cn.nextop.thorin.core.pricing.domain.entity.LpQuote;
import cn.nextop.thorin.core.pricing.orm.po.BkDeviationConfigPo;
import cn.nextop.thorin.core.pricing.orm.po.LpDeviationConfigPo;
import cn.nextop.thorin.test.core.assertj.asserts.bo.BkQuoteAssert;
import cn.nextop.thorin.test.core.assertj.asserts.bo.LpQuoteAssert;
import cn.nextop.thorin.test.core.assertj.asserts.po.BkDeviationConfigPoAssert;
import cn.nextop.thorin.test.core.assertj.asserts.po.LpDeviationConfigPoAssert;
//import cn.nextop.thorin.test.core.assertj.asserts.response.AcquireLqtyResponseAssert;
import cn.nextop.thorin.test.core.assertj.asserts.response.ResponseAssert;
import cn.nextop.thorin.test.core.assertj.asserts.vo.DealingDataAssert;
import cn.nextop.thorin.test.core.assertj.asserts.vo.HoldingDataAssert;
import cn.nextop.thorin.test.core.assertj.asserts.vo.PricingConfigAssert;
import cn.nextop.thorin.test.core.assertj.asserts.vo.TriggerDataAssert;
import cn.nextop.thorin.test.vo.dlrapi.DealingData;
import cn.nextop.thorin.test.vo.dlrapi.HoldingData;
import cn.nextop.thorin.test.vo.dlrapi.PricingConfig;
import cn.nextop.thorin.test.vo.dlrapi.TriggerData;

public class ThorinAssertions extends AssertjAssertions {

	// -----------------------------
	// ----- Vo
	// -----------------------------

	public static DealingDataAssert assertThat(DealingData actual) {
		return new DealingDataAssert(actual);
	}

	public static TriggerDataAssert assertThat(TriggerData actual) {
		return new TriggerDataAssert(actual);
	}

	public static HoldingDataAssert assertThat(HoldingData actual) {
		return new HoldingDataAssert(actual);
	}

	public static PricingConfigAssert assertThat(PricingConfig actual) {
		return new PricingConfigAssert(actual);
	}

	// -----------------------------
	// ----- Response
	// -----------------------------

	public static <S extends ResponseAssert<S, A>, A extends Response> ResponseAssert<S, A> assertThat(A actual) {
		return new ResponseAssert<>(actual, ResponseAssert.class);
	}

	// -----------------------------
	// ----- Bo
	// -----------------------------

	public static BkQuoteAssert assertThat(BkQuote actual) {
		return new BkQuoteAssert(actual);
	}

	public static LpQuoteAssert assertThat(LpQuote actual) {
		return new LpQuoteAssert(actual);
	}

	// -----------------------------
	// ----- Po
	// -----------------------------

	public static LpDeviationConfigPoAssert assertThat(LpDeviationConfigPo actual) {
		return new LpDeviationConfigPoAssert(actual);
	}

	public static BkDeviationConfigPoAssert assertThat(BkDeviationConfigPo actual) {
		return new BkDeviationConfigPoAssert(actual);
	}

}
