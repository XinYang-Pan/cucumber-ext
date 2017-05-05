package cn.nextop.thorin.test.service.dealerapi;

import static org.assertj.core.api.Assertions.assertThat;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import cn.nextop.thorin.api.dealer.proto.TriggerServiceProtoV1.SubscribeTriggerEventResponse;
import cn.nextop.thorin.api.dealer.proto.TriggerServiceProtoV1.TriggerEvent;
import cn.nextop.thorin.test.vo.dlrapi.TriggerData;
import support.shell.dlrapi.service.TriggerService;

@Service
@Lazy
public class DealerApiTriggerService extends DealerApiAbstractService {

	@Autowired
	private TriggerService triggerService;

	@PostConstruct
	public void init() {
		super.init();
		// 
		SubscribeTriggerEventResponse subscribeTriggerEvent = triggerService.subscribeTriggerEvent();
		assertThat(subscribeTriggerEvent.getResult()).as(subscribeTriggerEvent.toString()).isEqualTo(SubscribeTriggerEventResponse.Result.SUCCESS);
	}

	public TriggerData next() {
		TriggerEvent triggerEvent = triggerService.nextTriggerEvent();
		return TriggerData.from(triggerEvent);
	}
	
}
