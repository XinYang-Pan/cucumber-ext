package cn.nextop.thorin.test.service.common.po.pricing;

import static cn.nextop.thorin.test.util.TestUtils.setIfNotNull;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.stereotype.Service;

import com.google.common.base.Converter;
import com.google.common.collect.Lists;

import cn.nextop.thorin.common.util.collection.map.IntHashMap;
import cn.nextop.thorin.common.util.collection.map.LongHashMap;
import cn.nextop.thorin.core.pricing.domain.entity.Quotation;
import cn.nextop.thorin.core.pricing.domain.pipeline.request.impl.ModifyQuotationRequest;
import cn.nextop.thorin.core.pricing.domain.pipeline.response.impl.ModifyQuotationResponse;
import cn.nextop.thorin.core.pricing.orm.po.BkDeviationConfigPo;
import cn.nextop.thorin.test.core.assertj.hard.ThorinAssertions;
import cn.nextop.thorin.test.util.TestUtils;
import cn.nextop.thorin.test.vo.id.has.derivative.HasBkDeviationConfigId;
import cn.nextop.thorin.test.vo.id.obj.derivative.BkDeviationConfigId;
import cn.nextop.thorin.test.vo.pricer.DevStateVo;

@Service
public class BkDeviationConfigPoService extends AbstractConfigPoService<Long, BkDeviationConfigPo, HasBkDeviationConfigId> {

	@Override
	protected Long getId(BkDeviationConfigPo po) {
		return po.getId();
	}

	@Override
	protected Converter<Long, HasBkDeviationConfigId> getConverter() {
		return new Converter<Long, HasBkDeviationConfigId>() {

			@Override
			protected HasBkDeviationConfigId doForward(Long a) {
				return BkDeviationConfigId.toBkDeviationConfigId(a);
			}

			@Override
			protected Long doBackward(HasBkDeviationConfigId b) {
				return b.bkDeviationConfigId();
			}
		};
	}

	@Override
	protected BkDeviationConfigPo doGetByUniqueKey(HasBkDeviationConfigId uniqueKey) {
		LongHashMap<BkDeviationConfigPo> bkDeviations = pricingQuotationAdministrator.getBkDeviations(uniqueKey.bkId());
		return bkDeviations.get(uniqueKey.bkDeviationConfigId());
	}

	@Override
	protected void setRequest(ModifyQuotationRequest request, List<BkDeviationConfigPo> value) {
		request.setBkDeviationConfigs(value);
	}

	@Override
	protected Iterator<BkDeviationConfigPo> getResponse(ModifyQuotationResponse response) {
		return response.bkDeviationConfigs();
	}

	@Override
	protected void updateAssert(BkDeviationConfigPo result, BkDeviationConfigPo input) {
		if (input.isActive()) {
			super.updateAssert(result, input);
		} else {
			String[] ignoreFields = ArrayUtils.add(TestUtils.IGNORE_FIELDS, "status");
			assertThat(result).isEqualToIgnoringGivenFields(input, ignoreFields);
			assertThat(result.getStatus().isSuspended()).isFalse();
		}
	}

	// -----------------------------
	// ----- Custom
	// -----------------------------

	public List<BkDeviationConfigPo> getBkDeviations(Set<Short> brokerIds) {
		List<BkDeviationConfigPo> list = Lists.newArrayList();
		for (Short brokerId : brokerIds) {
			list.addAll(this.getBkDeviations(brokerId));
		}
		return list;
	}

	public List<BkDeviationConfigPo> getBkDeviations(short brokerId) {
		return Lists.newArrayList(pricingQuotationAdministrator.getBkDeviations(brokerId).values());
	}

	public void updateBkDeviationConfigPo(HasBkDeviationConfigId bkDeviationConfigId, DevStateVo devStateVo) {
		BkDeviationConfigPo bkDeviationConfigPo = this.getByUniqueKey(bkDeviationConfigId);
		setIfNotNull(devStateVo::getGap1, bkDeviationConfigPo::setGap1);
		setIfNotNull(devStateVo::getGap2, bkDeviationConfigPo::setGap2);
		setIfNotNull(devStateVo::getDepth, bkDeviationConfigPo::setDepth);
		setIfNotNull(devStateVo::getActive, bkDeviationConfigPo::setActive);
		setIfNotNull(devStateVo::getIntraday, bkDeviationConfigPo::setIntraday);
		this.update(bkDeviationConfigPo);
		// Re-check everything
		bkDeviationConfigPo = this.getByUniqueKey(bkDeviationConfigId);
		ThorinAssertions.assertThat(bkDeviationConfigPo).isState(devStateVo);

	}

	public void activateAll(byte companyId) {
		activateAll(companyId, true);
	}

	public void deactivateAll(byte companyId) {
		activateAll(companyId, false);
	}

	public void activateAll(byte companyId, boolean activate) {
		IntHashMap<Quotation> quotations = pricingQuotationAdministrator.getQuotations(companyId);
		for (Quotation quotation : quotations.values()) {
			LongHashMap<BkDeviationConfigPo> bkDeviations = quotation.getBkDeviations();
			for (BkDeviationConfigPo bkDeviationConfigPo : bkDeviations.values()) {
				if (bkDeviationConfigPo.isActive() != activate) {
					bkDeviationConfigPo.setActive(activate);
					this.update(bkDeviationConfigPo);
				}
			}
		}
		quotations = pricingQuotationAdministrator.getQuotations(companyId);
		for (Quotation quotation : quotations.values()) {
			LongHashMap<BkDeviationConfigPo> bkDeviations = quotation.getBkDeviations();
			for (BkDeviationConfigPo bkDeviationConfigPo : bkDeviations.values()) {
				assertThat(bkDeviationConfigPo.isActive(), is(activate));
			}
		}
	}

}
