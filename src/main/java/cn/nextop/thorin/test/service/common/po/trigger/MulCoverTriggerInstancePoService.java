package cn.nextop.thorin.test.service.common.po.trigger;

import java.util.List;

import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import cn.nextop.thorin.core.common.glossary.TriggerPolicy;
import cn.nextop.thorin.core.trigger.domain.entity.TriggerStrategy;
import cn.nextop.thorin.core.trigger.domain.pipeline.request.impl.ModifyTriggerRequest;
import cn.nextop.thorin.core.trigger.domain.pipeline.response.impl.ModifyTriggerResponse;
import cn.nextop.thorin.core.trigger.orm.po.MulCoverTriggerInstancePo;
import cn.nextop.thorin.test.core.assertj.hard.ThorinAssertions;

@Service
public class MulCoverTriggerInstancePoService extends AbstractTriggerInstancePoService<MulCoverTriggerInstancePo> {

	@Override
	protected MulCoverTriggerInstancePo doGet(Long id) {
		TriggerStrategy strategy = getStrategy(id);
		return strategy.getMulCoverTriggerInstance(id);
	}

	protected List<MulCoverTriggerInstancePo> updateRequest(MulCoverTriggerInstancePo input) {
		ModifyTriggerRequest request = new ModifyTriggerRequest(input.getCompanyId(), input.getSymbolId());
		request.setMulCoverTriggerInstances(Lists.newArrayList(input));
		ModifyTriggerResponse response = triggerStrategyAdministrator.modify(request);
		ThorinAssertions.assertThat(response).isSuccessful();
		return Lists.newArrayList(response.mulCoverTriggerInstances());
	}

	public MulCoverTriggerInstancePo getLatest(byte companyId, short symbolId, int tseq) {
		long id = this.toTriggerId(companyId, symbolId, TriggerPolicy.MUL_COVER, tseq);
		return this.get(id);
	}

}
