package cn.nextop.thorin.test.service.common.po.trigger;

import java.util.List;

import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import cn.nextop.thorin.core.trigger.domain.entity.TriggerStrategy;
import cn.nextop.thorin.core.trigger.domain.pipeline.request.impl.ModifyTriggerRequest;
import cn.nextop.thorin.core.trigger.domain.pipeline.response.impl.ModifyTriggerResponse;
import cn.nextop.thorin.core.trigger.orm.po.SmaShadeTriggerInstancePo;
import cn.nextop.thorin.test.core.assertj.hard.ThorinAssertions;

@Service
public class SmaShadeTriggerInstancePoService extends AbstractTriggerInstancePoService<SmaShadeTriggerInstancePo> {

	@Override
	protected SmaShadeTriggerInstancePo doGet(Long id) {
		TriggerStrategy strategy = getStrategy(id);
		return strategy.getSmaShadeTriggerInstance(id);
	}

	protected List<SmaShadeTriggerInstancePo> updateRequest(SmaShadeTriggerInstancePo input) {
		ModifyTriggerRequest request = new ModifyTriggerRequest(input.getCompanyId(), input.getSymbolId());
		request.setSmaShadeTriggerInstances(Lists.newArrayList(input));
		ModifyTriggerResponse response = triggerStrategyAdministrator.modify(request);
		ThorinAssertions.assertThat(response).isSuccessful();
		return Lists.newArrayList(response.smaShadeTriggerInstances());
	}

}
