package cn.nextop.thorin.test.core.assertj.asserts.vo;

import static org.assertj.core.api.Assertions.assertThat;

import cn.nextop.test.assertj.AssertjUtils;
import cn.nextop.test.assertj.asserts.AssertjAbstractAssert;
import cn.nextop.thorin.core.pricing.orm.po.BkDeviationConfigPo;
import cn.nextop.thorin.core.pricing.orm.po.BkPickingConfigPo;
import cn.nextop.thorin.core.pricing.orm.po.BkSymbolConfigPo;
import cn.nextop.thorin.core.pricing.orm.po.BkTuningConfigPo;
import cn.nextop.thorin.core.pricing.orm.po.LpDeviationConfigPo;
import cn.nextop.thorin.test.vo.dlrapi.PricingConfig;

public class PricingConfigAssert extends AssertjAbstractAssert<PricingConfigAssert, PricingConfig> {

	public PricingConfigAssert(PricingConfig actual) {
		super(actual, PricingConfigAssert.class);
	}

	public PricingConfigAssert isContentEqualTo(PricingConfig expected) {
		// 
		AssertjUtils.exactlyInAnyOrder(actual.getBkDeviationConfigPos(), expected.getBkDeviationConfigPos());
		AssertjUtils.exactlyInAnyOrder(actual.getBkPickingConfigPos(), expected.getBkPickingConfigPos());
		AssertjUtils.exactlyInAnyOrder(actual.getBkSymbolConfigPos(), expected.getBkSymbolConfigPos());
		AssertjUtils.exactlyInAnyOrder(actual.getBkTuningConfigPos(), expected.getBkTuningConfigPos());
		AssertjUtils.exactlyInAnyOrder(actual.getLpDeviationConfigPos(), expected.getLpDeviationConfigPos());
		return this;
	}

	public PricingConfigAssert hasOnly(BkDeviationConfigPo bkDeviationConfigPo) {
		assertThat(actual.getBkDeviationConfigPos()).hasSize(1);
		assertThat(actual.getBkPickingConfigPos()).hasSize(0);
		assertThat(actual.getBkSymbolConfigPos()).hasSize(0);
		assertThat(actual.getBkTuningConfigPos()).hasSize(0);
		assertThat(actual.getLpDeviationConfigPos()).hasSize(0);
		// 
		BkDeviationConfigPo actualElement = actual.getBkDeviationConfigPos().get(0);
		assertThat(actualElement).isEqualTo(bkDeviationConfigPo);
		return this;
	}

	public PricingConfigAssert hasOnly(BkPickingConfigPo bkPickingConfigPo) {
		assertThat(actual.getBkDeviationConfigPos()).hasSize(0);
		assertThat(actual.getBkPickingConfigPos()).hasSize(1);
		assertThat(actual.getBkSymbolConfigPos()).hasSize(0);
		assertThat(actual.getBkTuningConfigPos()).hasSize(0);
		assertThat(actual.getLpDeviationConfigPos()).hasSize(0);
		// 
		BkPickingConfigPo actualElement = actual.getBkPickingConfigPos().get(0);
		assertThat(actualElement).isEqualTo(bkPickingConfigPo);
		return this;
	}

	public PricingConfigAssert hasOnly(BkSymbolConfigPo bkSymbolConfigPo) {
		assertThat(actual.getBkDeviationConfigPos()).hasSize(0);
		assertThat(actual.getBkPickingConfigPos()).hasSize(0);
		assertThat(actual.getBkSymbolConfigPos()).hasSize(1);
		assertThat(actual.getBkTuningConfigPos()).hasSize(0);
		assertThat(actual.getLpDeviationConfigPos()).hasSize(0);
		// 
		BkSymbolConfigPo actualElement = actual.getBkSymbolConfigPos().get(0);
		assertThat(actualElement).isEqualTo(bkSymbolConfigPo);
		return this;
	}

	public PricingConfigAssert hasOnly(BkTuningConfigPo bkTuningConfigPo) {
		assertThat(actual.getBkDeviationConfigPos()).hasSize(0);
		assertThat(actual.getBkPickingConfigPos()).hasSize(0);
		assertThat(actual.getBkSymbolConfigPos()).hasSize(0);
		assertThat(actual.getBkTuningConfigPos()).hasSize(1);
		assertThat(actual.getLpDeviationConfigPos()).hasSize(0);
		// 
		BkTuningConfigPo actualElement = actual.getBkTuningConfigPos().get(0);
		assertThat(actualElement).isEqualTo(bkTuningConfigPo);
		return this;
	}

	public PricingConfigAssert hasOnly(LpDeviationConfigPo bkDeviationConfigPo) {
		assertThat(actual.getBkDeviationConfigPos()).hasSize(0);
		assertThat(actual.getBkPickingConfigPos()).hasSize(0);
		assertThat(actual.getBkSymbolConfigPos()).hasSize(0);
		assertThat(actual.getBkTuningConfigPos()).hasSize(0);
		assertThat(actual.getLpDeviationConfigPos()).hasSize(1);
		// 
		LpDeviationConfigPo actualElement = actual.getLpDeviationConfigPos().get(0);
		assertThat(actualElement).isEqualTo(bkDeviationConfigPo);
		return this;
	}

}
