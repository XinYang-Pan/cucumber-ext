package cn.nextop.thorin.test.service.common.po.trigger;

import java.util.List;

import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import cn.nextop.thorin.core.trigger.domain.entity.TriggerStrategy;
import cn.nextop.thorin.core.trigger.domain.pipeline.request.impl.ModifyTriggerRequest;
import cn.nextop.thorin.core.trigger.domain.pipeline.response.impl.ModifyTriggerResponse;
import cn.nextop.thorin.core.trigger.orm.po.MulSpreadTriggerInstancePo;
import cn.nextop.thorin.test.core.assertj.hard.ThorinAssertions;

@Service
public class MulSpreadTriggerInstancePoService extends AbstractTriggerInstancePoService<MulSpreadTriggerInstancePo> {

	@Override
	protected MulSpreadTriggerInstancePo doGet(Long id) {
		TriggerStrategy strategy = getStrategy(id);
		return strategy.getMulSpreadTriggerInstance(id);
	}

	protected List<MulSpreadTriggerInstancePo> updateRequest(MulSpreadTriggerInstancePo input) {
		ModifyTriggerRequest request = new ModifyTriggerRequest(input.getCompanyId(), input.getSymbolId());
		request.setMulSpreadTriggerInstances(Lists.newArrayList(input));
		ModifyTriggerResponse response = triggerStrategyAdministrator.modify(request);
		ThorinAssertions.assertThat(response).isSuccessful();
		return Lists.newArrayList(response.mulSpreadTriggerInstances());
	}

}
