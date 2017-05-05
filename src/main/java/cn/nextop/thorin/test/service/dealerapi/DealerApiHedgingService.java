package cn.nextop.thorin.test.service.dealerapi;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import cn.nextop.thorin.api.dealer.proto.HedgingServiceProtoV1.HedgingEvent;
import cn.nextop.thorin.api.dealer.proto.HedgingServiceProtoV1.SubscribeHedgingEventResponse;
import cn.nextop.thorin.api.dealer.service.hedging.v1.model.HedgingSessionV1Factory;
import cn.nextop.thorin.core.hedging.domain.entity.HedgingSession;
import support.shell.dlrapi.service.HedgingService;

@Service
@Lazy
public class DealerApiHedgingService extends DealerApiAbstractService {

	@Autowired
	private HedgingService hedgingService;

	@PostConstruct
	public void init() {
		super.init();
		// 
		SubscribeHedgingEventResponse subscribeHedgingEvent = hedgingService.subscribeHedgingEvent();
		assertThat(subscribeHedgingEvent.getResult()).as(subscribeHedgingEvent.toString()).isEqualTo(SubscribeHedgingEventResponse.Result.SUCCESS);
	}

	public List<HedgingSession> next() {
		HedgingEvent hedgingEvent = hedgingService.nextHedgingEvent();
		return Lists.newArrayList(Lists.transform(hedgingEvent.getSessionsList(), HedgingSessionV1Factory::toSession));
	}

}
