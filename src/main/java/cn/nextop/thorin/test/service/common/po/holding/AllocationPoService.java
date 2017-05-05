package cn.nextop.thorin.test.service.common.po.holding;

import java.util.Collection;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.google.common.collect.Collections2;
import com.google.common.collect.Iterables;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;

import cn.nextop.thorin.common.util.Objects;
import cn.nextop.thorin.common.util.collection.map.IntHashMap;
import cn.nextop.thorin.core.holding.domain.entity.HoldingStrategy;
import cn.nextop.thorin.core.holding.domain.pipeline.request.impl.DeleteAllocationRequest;
import cn.nextop.thorin.core.holding.domain.pipeline.request.impl.ModifyStrategyRequest;
import cn.nextop.thorin.core.holding.domain.pipeline.response.impl.DeleteAllocationResponse;
import cn.nextop.thorin.core.holding.domain.pipeline.response.impl.ModifyStrategyResponse;
import cn.nextop.thorin.core.holding.orm.po.AllocationPo;
import cn.nextop.thorin.test.core.assertj.hard.ThorinAssertions;

@Service
public class AllocationPoService extends AbstractHoldingService<AllocationPo> {

	@Override
	protected Integer getId(AllocationPo po) {
		return po.getId();
	}

	@Override
	protected AllocationPo doGet(Integer id) {
		return admin.getAllocation(id);
	}

	@Override
	protected AllocationPo doUpdate(AllocationPo input) {
		ModifyStrategyRequest request = new ModifyStrategyRequest(input.getCompanyId());
		request.setAllocation(Lists.newArrayList(input));
		ModifyStrategyResponse response = admin.modify(request);
		ThorinAssertions.assertThat(response).isSuccessful();
		return Iterators.getOnlyElement(response.allocation());
	}

	public void delete(AllocationPo input) {
		Assert.notNull(input);
		// 
		DeleteAllocationRequest request = new DeleteAllocationRequest(input.getCompanyId());
		request.setAllocation(input);
		DeleteAllocationResponse response = admin.delete(request);
		ThorinAssertions.assertThat(response).isSuccessful();
	}

	public void deleteAll(byte coId) {
		HoldingStrategy strategy = admin.getStrategy(coId);
		IntHashMap<AllocationPo> map = strategy.getAllocation();
		// 
		for (AllocationPo input : map.values()) {
			this.delete(input);
		}
	}

	public AllocationPo get(byte coId, String abbr) {
		HoldingStrategy strategy = admin.getStrategy(coId);
		IntHashMap<AllocationPo> map = strategy.getAllocation();
		// 
		Collection<AllocationPo> collection = Collections2.filter(map.values(), t -> Objects.isEquals(t.getAbbreviation(), abbr));
		if (CollectionUtils.isEmpty(collection)) {
			return null;
		} else {
			return Iterables.getOnlyElement(collection);
		}
	}

}
