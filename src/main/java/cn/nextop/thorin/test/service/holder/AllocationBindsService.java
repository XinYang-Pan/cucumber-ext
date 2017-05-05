package cn.nextop.thorin.test.service.holder;

import java.util.List;

import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.google.common.collect.Iterators;

import cn.nextop.thorin.core.holding.HoldingStrategyAdministrator;
import cn.nextop.thorin.core.holding.domain.pipeline.request.impl.BindAllocationRequest;
import cn.nextop.thorin.core.holding.domain.pipeline.response.impl.BindAllocationResponse;
import cn.nextop.thorin.core.holding.orm.po.AllocationAccountsConfigPo;
import cn.nextop.thorin.core.holding.orm.po.AllocationBookConfigPo;
import cn.nextop.thorin.core.holding.orm.po.AllocationBrokerConfigPo;
import cn.nextop.thorin.core.holding.orm.po.AllocationPo;
import cn.nextop.thorin.core.holding.orm.po.AllocationSymbolConfigPo;
import cn.nextop.thorin.test.core.assertj.hard.ThorinAssertions;
import cn.nextop.thorin.test.service.common.AbstractService;

@Service
@Lazy
public class AllocationBindsService extends AbstractService {
	@Autowired
	private HoldingStrategyAdministrator admin;
	
	public BindAllocationRequest getBindAllocationRequest(byte coId, String allocationAbbr) {
		AllocationPo allocationPo = allocationPoService.get(coId, allocationAbbr);
		// 
		BindAllocationRequest req = new BindAllocationRequest(allocationPo);
		req.setAllocationAccountsConfigs(this.getAllocationAccountsConfigPos(allocationPo.getId()));
		req.setAllocationBookConfigs(this.getAllocationBookConfigPos(allocationPo.getId()));
		req.setAllocationBrokerConfigs(this.getAllocationBrokerConfigPos(allocationPo.getId()));
		req.setAllocationSymbolConfigs(this.getAllocationSymbolConfigPos(allocationPo.getId()));
		return req;
	}
	
	public List<AllocationBookConfigPo> getAllocationBookConfigPos(int allocationId) {
		return Lists.newArrayList(admin.getAllocationBookConfigs(allocationId).values());
	}

	public List<AllocationAccountsConfigPo> getAllocationAccountsConfigPos(int allocationId) {
		return Lists.newArrayList(admin.getAllocationAccountsConfigs(allocationId).values());
	}

	public List<AllocationSymbolConfigPo> getAllocationSymbolConfigPos(int allocationId) {
		return Lists.newArrayList(admin.getAllocationSymbolConfigs(allocationId).values());
	}

	public List<AllocationBrokerConfigPo> getAllocationBrokerConfigPos(int allocationId) {
		return Lists.newArrayList(admin.getAllocationBrokerConfigs(allocationId).values());
	}
	
	public AllocationPo bindAllocation(BindAllocationRequest request) {
		log.debug("BindAllocationRequest = {}", request);
		BindAllocationResponse response = admin.bind(request);
		log.debug("BindAllocationResponse = {}", response);
		ThorinAssertions.assertThat(response).isSuccessful();
		return Iterators.getOnlyElement(response.allocation());
	}

}
