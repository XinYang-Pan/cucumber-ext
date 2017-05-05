package cn.nextop.thorin.test.service.common.po.pricing;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.google.common.base.Converter;
import com.google.common.collect.Lists;

import cn.nextop.thorin.common.util.collection.map.LongHashMap;
import cn.nextop.thorin.core.pricing.domain.pipeline.request.impl.ModifyQuotationRequest;
import cn.nextop.thorin.core.pricing.domain.pipeline.response.impl.ModifyQuotationResponse;
import cn.nextop.thorin.core.pricing.orm.po.BkTuningConfigPo;
import cn.nextop.thorin.test.vo.id.has.derivative.HasBkTuningConfigId;
import cn.nextop.thorin.test.vo.id.obj.derivative.BkTuningConfigId;

@Service
public class BkTuningConfigPoService extends AbstractConfigPoService<Long, BkTuningConfigPo, HasBkTuningConfigId> {

	@Override
	protected Long getId(BkTuningConfigPo po) {
		return po.getId();
	}

	@Override
	protected Converter<Long, HasBkTuningConfigId> getConverter() {
		return new Converter<Long, HasBkTuningConfigId>() {

			@Override
			protected HasBkTuningConfigId doForward(Long a) {
				return BkTuningConfigId.toBkTuningConfigId(a);
			}

			@Override
			protected Long doBackward(HasBkTuningConfigId b) {
				return b.bkTuningConfigId();
			}
		};
	}

	@Override
	protected BkTuningConfigPo doGetByUniqueKey(HasBkTuningConfigId uniqueKey) {
		LongHashMap<BkTuningConfigPo> bkTunings = pricingQuotationAdministrator.getBkTunings(uniqueKey.bkId());
		return bkTunings.get(uniqueKey.bkTuningConfigId());
	}

	@Override
	protected void setRequest(ModifyQuotationRequest request, List<BkTuningConfigPo> value) {
		request.setBkTuningConfigs(value);
	}

	@Override
	protected Iterator<BkTuningConfigPo> getResponse(ModifyQuotationResponse response) {
		return response.bkTuningConfigs();
	}

	// -----------------------------
	// ----- Custom
	// -----------------------------

	public List<BkTuningConfigPo> getBkTunings(Set<Short> brokerIds) {
		List<BkTuningConfigPo> list = Lists.newArrayList();
		for (Short brokerId : brokerIds) {
			list.addAll(this.getBkTunings(brokerId));
		}
		return list;
	}
	
	public List<BkTuningConfigPo> getBkTunings(short brokerId) {
		return Lists.newArrayList(pricingQuotationAdministrator.getBkTunings(brokerId).values());
	}

}
