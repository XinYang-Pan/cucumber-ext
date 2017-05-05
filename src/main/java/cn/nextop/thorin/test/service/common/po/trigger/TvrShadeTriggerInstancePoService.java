package cn.nextop.thorin.test.service.common.po.trigger;

import java.util.List;

import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import cn.nextop.thorin.core.trigger.domain.entity.TriggerStrategy;
import cn.nextop.thorin.core.trigger.domain.pipeline.request.impl.ModifyTriggerRequest;
import cn.nextop.thorin.core.trigger.domain.pipeline.response.impl.ModifyTriggerResponse;
import cn.nextop.thorin.core.trigger.orm.po.TvrShadeTriggerInstancePo;
import cn.nextop.thorin.test.core.assertj.hard.ThorinAssertions;

@Service
public class TvrShadeTriggerInstancePoService extends AbstractTriggerInstancePoService<TvrShadeTriggerInstancePo> {

	@Override
	protected TvrShadeTriggerInstancePo doGet(Long id) {
		TriggerStrategy strategy = getStrategy(id);
		return strategy.getTvrShadeTriggerInstance(id);
	}

	protected List<TvrShadeTriggerInstancePo> updateRequest(TvrShadeTriggerInstancePo input) {
		ModifyTriggerRequest request = new ModifyTriggerRequest(input.getCompanyId(), input.getSymbolId());
		request.setTvrShadeTriggerInstances(Lists.newArrayList(input));
		ModifyTriggerResponse response = triggerStrategyAdministrator.modify(request);
		ThorinAssertions.assertThat(response).isSuccessful();
		return Lists.newArrayList(response.tvrShadeTriggerInstances());
	}

}
