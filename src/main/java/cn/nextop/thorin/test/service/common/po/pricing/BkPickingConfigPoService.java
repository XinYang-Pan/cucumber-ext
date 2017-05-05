package cn.nextop.thorin.test.service.common.po.pricing;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.google.common.base.Converter;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import cn.nextop.thorin.common.util.collection.map.LongHashMap;
import cn.nextop.thorin.core.common.glossary.PricingPickingPolicy;
import cn.nextop.thorin.core.pricing.domain.pipeline.request.impl.ModifyQuotationRequest;
import cn.nextop.thorin.core.pricing.domain.pipeline.response.impl.ModifyQuotationResponse;
import cn.nextop.thorin.core.pricing.orm.po.BkPickingConfigPo;
import cn.nextop.thorin.test.core.assertj.hard.ThorinAssertions;
import cn.nextop.thorin.test.vo.id.has.compound.HasBkSymbolId;
import cn.nextop.thorin.test.vo.id.has.derivative.HasBkPickingConfigId;
import cn.nextop.thorin.test.vo.id.obj.compound.LpBkSymbolId;
import cn.nextop.thorin.test.vo.id.obj.derivative.BkPickingConfigId;
import cn.nextop.thorin.test.vo.pricer.BkPickingStateVo;

@Service
public class BkPickingConfigPoService extends AbstractConfigPoService<Long, BkPickingConfigPo, HasBkPickingConfigId> {

	@Override
	protected Long getId(BkPickingConfigPo po) {
		return po.getId();
	}

	@Override
	protected Converter<Long, HasBkPickingConfigId> getConverter() {
		return new Converter<Long, HasBkPickingConfigId>() {
			
			@Override
			protected HasBkPickingConfigId doForward(Long a) {
				return BkPickingConfigId.toBkPickingConfigId(a);
			}
			
			@Override
			protected Long doBackward(HasBkPickingConfigId b) {
				return b.bkPickingConfigId();
			}
		};
	}

	@Override
	protected BkPickingConfigPo doGetByUniqueKey(HasBkPickingConfigId uniqueKey) {
		LongHashMap<BkPickingConfigPo> bkDeviations = pricingQuotationAdministrator.getBkPickings(uniqueKey.bkId());
		return bkDeviations.get(uniqueKey.bkPickingConfigId());
	}

	@Override
	protected void setRequest(ModifyQuotationRequest request, List<BkPickingConfigPo> value) {
		request.setBkPickingConfigs(value);
	}

	@Override
	protected Iterator<BkPickingConfigPo> getResponse(ModifyQuotationResponse response) {
		return response.bkPickingConfigs();
	}

	// -----------------------------
	// ----- Custom
	// -----------------------------

	public List<BkPickingConfigPo> getBkPickings(Set<Short> brokerIds) {
		List<BkPickingConfigPo> list = Lists.newArrayList();
		for (Short brokerId : brokerIds) {
			list.addAll(this.getBkPickings(brokerId));
		}
		return list;
	}
	
	public List<BkPickingConfigPo> getBkPickings(short brokerId) {
		return Lists.newArrayList(pricingQuotationAdministrator.getBkPickings(brokerId).values());
	}
	
	public void changePickingStrategyState(HasBkSymbolId bkSymbolId, PricingPickingPolicy pricingPickingPolicy, List<BkPickingStateVo> bkPickingStateVos) {
		Map<String, BkPickingStateVo> itNameKeyMap = Maps.uniqueIndex(bkPickingStateVos, BkPickingStateVo::getItName);
		for (Entry<String, BkPickingStateVo> e : itNameKeyMap.entrySet()) {
			String itName = e.getKey();
			BkPickingStateVo bkPickingStateVo = e.getValue();
			// 
			short lpId = commonService.getDefaultLpId(itName);
			LpBkSymbolId lpBkSymbolId = LpBkSymbolId.toLpBkSymbolId(lpId, bkSymbolId.bkId(), bkSymbolId.symbolId());
			BkPickingConfigId bkPickingConfigId = BkPickingConfigId.toBkPickingConfigId(lpBkSymbolId, pricingPickingPolicy);
			BkPickingConfigPo bkPickingConfigPo = getByUniqueKey(bkPickingConfigId);
			// Set
			if (bkPickingStateVo.getPriority() != null) {
				bkPickingConfigPo.setPriority(bkPickingStateVo.getPriority());
			}
			if (bkPickingStateVo.getWeight() != null) {
				bkPickingConfigPo.setWeight(bkPickingStateVo.getWeight());
			}
			if (bkPickingStateVo.getActive() != null) {
				bkPickingConfigPo.setActive(bkPickingStateVo.getActive());
			}
			update(bkPickingConfigPo);
		}
	}
	
	public void updateMasterTo(BkPickingConfigId bkPickingConfigId) {
		// 
		assertThat(bkPickingConfigId.pricingPickingPolicy()).isEqualTo(PricingPickingPolicy.ALP);
		// 
		short symbolId = bkPickingConfigId.symbolId();
		LongHashMap<BkPickingConfigPo> bkPickings = pricingQuotationAdministrator.getBkPickings(bkPickingConfigId.bkId());
		List<BkPickingConfigPo> bkPickingConfigPoList = bkPickings.values().stream()
			.filter(bkPickingConfigPo -> bkPickingConfigPo.getSymbolId() == symbolId && bkPickingConfigPo.getPolicy() == PricingPickingPolicy.ALP)
			.map(BkPickingConfigPo::copy)
			.collect(Collectors.toList());
		for (BkPickingConfigPo bkPickingConfigPo : bkPickingConfigPoList) {
			if (bkPickingConfigPo.getLpId() == bkPickingConfigId.lpId()) {
				if (bkPickingConfigPo.isMaster()) {
					// already master, dont need to update
					return;
				} else {
					bkPickingConfigPo.setMaster(true);
				}
			} else {
				bkPickingConfigPo.setMaster(false);
			}
		}
		// 
		ModifyQuotationRequest request = new ModifyQuotationRequest(bkPickingConfigId.coId(), symbolId);
		request.setBkPickingConfigs(bkPickingConfigPoList);
		ModifyQuotationResponse response = pricingQuotationAdministrator.modify(request);
		// 
		ThorinAssertions.assertThat(response).isSuccessful();
	}

	public void activateSuccessful(HasBkPickingConfigId bkPickingConfigId) {
		this.activate(bkPickingConfigId, true, true);
	}

	public void inactivateSuccessful(HasBkPickingConfigId bkPickingConfigId) {
		this.activate(bkPickingConfigId, false, true);
	}

	public void inactivateFailed(HasBkPickingConfigId bkPickingConfigId) {
		this.activate(bkPickingConfigId, false, false);
	}

	private void activate(HasBkPickingConfigId bkPickingConfigId, boolean activate, boolean successful) {
		// 
		AssertionError exception = null;
		try {
			updateByUniqueKey(bkPickingConfigId, bkPickingConfigPo -> bkPickingConfigPo.setActive(activate));
		} catch (AssertionError e) {
			exception = e;
		}
		if (successful) {
			assertThat(exception).isNull();
		} else {
			assertThat(exception).isNotNull()
				.isInstanceOf(AssertionError.class)
				.hasMessageContaining("ModifyQuotationResponse")
				.hasMessageContaining("Expected to be successful.")
				.hasMessageContaining("result=INVALID_REQUEST");
		}
	}

	public List<BkPickingConfigPo> getBkPickingConfigPos(HasBkSymbolId bkSymbolId, PricingPickingPolicy pricingPickingPolicy) {
		LongHashMap<BkPickingConfigPo> bkPickings = pricingQuotationAdministrator.getBkPickings(bkSymbolId.bkId());
		Collection<BkPickingConfigPo> col = Collections2.filter(bkPickings.values(), bkPickingConfigPo -> bkPickingConfigPo.isActive() && bkPickingConfigPo.getSymbolId() == bkSymbolId.symbolId() && pricingPickingPolicy.equals(bkPickingConfigPo.getPolicy()));
		return Lists.newArrayList(col);
	}

}
