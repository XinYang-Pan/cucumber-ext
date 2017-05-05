package cn.nextop.thorin.test.core.assertj.asserts.po;

import static cn.nextop.test.assertj.AssertjUtils.assertIgnoringNullEqualTo;
import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.api.SoftAssertions;

import cn.nextop.thorin.core.pricing.orm.po.LpDeviationConfigPo;
import cn.nextop.thorin.test.vo.pricer.DevStateVo;
import cn.nextop.thorin.test.vo.pricer.DeviationStatusVo;

public class LpDeviationConfigPoAssert extends AbstractPoAssert<LpDeviationConfigPoAssert, LpDeviationConfigPo> {

	public LpDeviationConfigPoAssert(LpDeviationConfigPo actual) {
		super(actual, LpDeviationConfigPoAssert.class);
	}

	public LpDeviationConfigPoAssert isDeviationStatus(DeviationStatusVo deviationStatusVo) {
		assertThat(deviationStatusVo).isNotNull();
		// 
		Boolean systemBid = deviationStatusVo.getSystemBid();
		Boolean systemAsk = deviationStatusVo.getSystemAsk();
		this.isDeviationStatus(systemBid, systemAsk);
		return myself;
	}

	public LpDeviationConfigPoAssert isDeviationStatus(Boolean systemBid, Boolean systemAsk) {
		if (actual != null & actual.isActive()) {
			this.isNotNull();
			// 
			SoftAssertions soft = new SoftAssertions();
			if (systemBid != null) {
				soft.assertThat(actual.getStatus().isBidSuspended()).as("Bid Dev").isEqualTo(systemBid);
			}
			if (systemAsk != null) {
				soft.assertThat(actual.getStatus().isAskSuspended()).as("Ask Dev").isEqualTo(systemAsk);
			}
			soft.assertAll();
		} else {
			assertThat((Boolean) null).isEqualTo(systemBid);
			assertThat((Boolean) null).isEqualTo(systemAsk);
		}
		return myself;
	}

	public LpDeviationConfigPoAssert isState(DevStateVo devStateVo) {
		this.isNotNull();
		// 
		SoftAssertions soft = new SoftAssertions();
		assertIgnoringNullEqualTo(soft.assertThat(actual.getGap1()).as("Gap1"), devStateVo.getGap1());
		assertIgnoringNullEqualTo(soft.assertThat(actual.getGap2()).as("Gap2"), devStateVo.getGap2());
		assertIgnoringNullEqualTo(soft.assertThat(actual.getDepth()).as("Depth"), devStateVo.getDepth());
		assertIgnoringNullEqualTo(soft.assertThat(actual.isActive()).as("isActive"), devStateVo.getActive());
		assertIgnoringNullEqualTo(soft.assertThat(actual.isIntraday()).as("isIntraday"), devStateVo.getIntraday());
		soft.assertAll();
		return myself;
	}

}
