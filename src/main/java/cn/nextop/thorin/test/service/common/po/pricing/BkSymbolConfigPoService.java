package cn.nextop.thorin.test.service.common.po.pricing;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.google.common.base.Converter;
import com.google.common.collect.Lists;

import cn.nextop.thorin.common.util.collection.map.IntHashMap;
import cn.nextop.thorin.core.common.glossary.PricingPickingPolicy;
import cn.nextop.thorin.core.common.glossary.PricingRecoverPolicy;
import cn.nextop.thorin.core.pricing.domain.pipeline.request.impl.ModifyQuotationRequest;
import cn.nextop.thorin.core.pricing.domain.pipeline.response.impl.ModifyQuotationResponse;
import cn.nextop.thorin.core.pricing.orm.po.BkSymbolConfigPo;
import cn.nextop.thorin.test.vo.id.has.compound.HasBkSymbolId;
import cn.nextop.thorin.test.vo.id.obj.compound.BkSymbolId;

@Service
public class BkSymbolConfigPoService extends AbstractConfigPoService<Integer, BkSymbolConfigPo, HasBkSymbolId> {

	@Override
	protected Integer getId(BkSymbolConfigPo po) {
		return po.getId();
	}

	@Override
	protected Converter<Integer, HasBkSymbolId> getConverter() {
		return new Converter<Integer, HasBkSymbolId>() {
			
			@Override
			protected HasBkSymbolId doForward(Integer a) {
				return BkSymbolId.toBkSymbolId(a);
			}
			
			@Override
			protected Integer doBackward(HasBkSymbolId b) {
				return b.bkSymbolId();
			}
		};
	}

	@Override
	protected BkSymbolConfigPo doGetByUniqueKey(HasBkSymbolId uniqueKey) {
		IntHashMap<BkSymbolConfigPo> bkSymbols = pricingQuotationAdministrator.getBkSymbols(uniqueKey.bkId());
		return bkSymbols.get(uniqueKey.bkSymbolId());
	}

	@Override
	protected void setRequest(ModifyQuotationRequest request, List<BkSymbolConfigPo> value) {
		request.setBkSymbolConfigs(value);
	}

	@Override
	protected Iterator<BkSymbolConfigPo> getResponse(ModifyQuotationResponse response) {
		return response.bkSymbolConfigs();
	}

	// -----------------------------
	// ----- Custom
	// -----------------------------

	public List<BkSymbolConfigPo> getBkSymbols(Set<Short> brokerIds) {
		List<BkSymbolConfigPo> list = Lists.newArrayList();
		for (Short brokerId : brokerIds) {
			list.addAll(this.getBkSymbols(brokerId));
		}
		return list;
	}
	
	public List<BkSymbolConfigPo> getBkSymbols(short brokerId) {
		return Lists.newArrayList(pricingQuotationAdministrator.getBkSymbols(brokerId).values());
	}
	
	public void changePickInactiveQuote(HasBkSymbolId bkSymbolId, boolean pickInactiveQuote) {
		updateByUniqueKey(bkSymbolId, bkSymbolConfigPo -> bkSymbolConfigPo.setPickInactiveQuote(pickInactiveQuote));
	}

	public void changePickingStrategy(HasBkSymbolId bkSymbolId, PricingPickingPolicy pricingPickingPolicy) {
		updateByUniqueKey(bkSymbolId, bkSymbolConfigPo -> bkSymbolConfigPo.setPickingPolicy(pricingPickingPolicy));
	}

	public void changeRecoverPolicy(HasBkSymbolId bkSymbolId, PricingRecoverPolicy pricingRecoverPolicy) {
		updateByUniqueKey(bkSymbolId, bkSymbolConfigPo -> bkSymbolConfigPo.setRecoverPolicy(pricingRecoverPolicy));
	}

}
