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
import cn.nextop.thorin.core.pricing.orm.po.LpDeviationConfigPo;
import cn.nextop.thorin.test.core.assertj.hard.ThorinAssertions;
import cn.nextop.thorin.test.util.TestUtils;
import cn.nextop.thorin.test.vo.id.has.derivative.HasLpDeviationConfigId;
import cn.nextop.thorin.test.vo.id.obj.derivative.LpDeviationConfigId;
import cn.nextop.thorin.test.vo.pricer.DevStateVo;

@Service
public class LpDeviationConfigPoService extends AbstractConfigPoService<Long, LpDeviationConfigPo, HasLpDeviationConfigId> {

	@Override
	protected Long getId(LpDeviationConfigPo po) {
		return po.getId();
	}

	@Override
	protected Converter<Long, HasLpDeviationConfigId> getConverter() {
		return new Converter<Long, HasLpDeviationConfigId>() {
			
			@Override
			protected HasLpDeviationConfigId doForward(Long a) {
				return LpDeviationConfigId.toLpDeviationConfigId(a);
			}
			
			@Override
			protected Long doBackward(HasLpDeviationConfigId b) {
				return b.lpDeviationConfigId();
			}
		};
	}

	@Override
	protected LpDeviationConfigPo doGetByUniqueKey(HasLpDeviationConfigId uniqueKey) {
		LongHashMap<LpDeviationConfigPo> lpDeviations = pricingQuotationAdministrator.getLpDeviations(uniqueKey.lpId());
		return lpDeviations.get(uniqueKey.lpDeviationConfigId());
	}

	@Override
	protected void setRequest(ModifyQuotationRequest request, List<LpDeviationConfigPo> value) {
		request.setLpDeviationConfigs(value);
	}

	@Override
	protected Iterator<LpDeviationConfigPo> getResponse(ModifyQuotationResponse response) {
		return response.lpDeviationConfigs();
	}
	
	@Override
	protected void updateAssert(LpDeviationConfigPo result, LpDeviationConfigPo input) {
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

	public List<LpDeviationConfigPo> getLpDeviations(Set<Short> lpIds) {
		List<LpDeviationConfigPo> list = Lists.newArrayList();
		for (Short brokerId : lpIds) {
			list.addAll(this.getLpDeviations(brokerId));
		}
		return list;
	}
	
	public List<LpDeviationConfigPo> getLpDeviations(short lpId) {
		return Lists.newArrayList(pricingQuotationAdministrator.getLpDeviations(lpId).values());
	}
	
	public void updateLpDeviationConfigPo(HasLpDeviationConfigId lpDeviationConfigId, DevStateVo devStateVo) {
		LpDeviationConfigPo lpDeviationConfigPo = this.getByUniqueKey(lpDeviationConfigId);
		setIfNotNull(devStateVo::getGap1, lpDeviationConfigPo::setGap1);
		setIfNotNull(devStateVo::getGap2, lpDeviationConfigPo::setGap2);
		setIfNotNull(devStateVo::getDepth, lpDeviationConfigPo::setDepth);
		setIfNotNull(devStateVo::getActive, lpDeviationConfigPo::setActive);
		setIfNotNull(devStateVo::getIntraday, lpDeviationConfigPo::setIntraday);
		this.update(lpDeviationConfigPo);
		// Re-check everything
		lpDeviationConfigPo = this.getByUniqueKey(lpDeviationConfigId);
		ThorinAssertions.assertThat(lpDeviationConfigPo).isState(devStateVo);
	}

	public void activateAllLpDev(byte companyId) {
		activateAll(companyId, true);
	}

	public void deactivateAllLpDev(byte companyId) {
		activateAll(companyId, false);
	}
	
	public void activateAll(byte companyId, boolean activate) {
		IntHashMap<Quotation> quotations = pricingQuotationAdministrator.getQuotations(companyId);
		for (Quotation quotation : quotations.values()) {
			LongHashMap<LpDeviationConfigPo> lpDeviations = quotation.getLpDeviations();
			for (LpDeviationConfigPo lpDeviationConfigPo : lpDeviations.values()) {
				if (lpDeviationConfigPo.isActive() != activate) {
					lpDeviationConfigPo.setActive(activate);
					this.update(lpDeviationConfigPo);
				}
			}
		}
		quotations = pricingQuotationAdministrator.getQuotations(companyId);
		for (Quotation quotation : quotations.values()) {
			LongHashMap<LpDeviationConfigPo> lpDeviations = quotation.getLpDeviations();
			for (LpDeviationConfigPo lpDeviationConfigPo : lpDeviations.values()) {
				assertThat(lpDeviationConfigPo.isActive(), is(activate));
			}
		}
	}
	
}
