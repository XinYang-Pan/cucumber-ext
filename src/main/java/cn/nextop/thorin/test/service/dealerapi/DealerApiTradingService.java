package cn.nextop.thorin.test.service.dealerapi;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import cn.nextop.thorin.api.dealer.proto.TradingServiceProtoV1.SubscribeTradingEventResponse;
import cn.nextop.thorin.api.dealer.proto.TradingServiceProtoV1.TradingEvent;
import cn.nextop.thorin.api.dealer.service.trading.v1.model.TradingSessionV1Factory;
import cn.nextop.thorin.core.trading.domain.entity.TradingSession;
import support.shell.dlrapi.service.TradingService;

@Service
@Lazy
public class DealerApiTradingService extends DealerApiAbstractService {

	@Autowired
	private TradingService tradingService;

	@PostConstruct
	public void init() {
		super.init();
		// 
		SubscribeTradingEventResponse subscribeTradingEvent = tradingService.subscribeTradingEvent();
		assertThat(subscribeTradingEvent.getResult()).as(subscribeTradingEvent.toString()).isEqualTo(SubscribeTradingEventResponse.Result.SUCCESS);
	}

	public List<TradingSession> next() {
		TradingEvent tradingEvent = tradingService.nextTradingEvent();
		return Lists.newArrayList(Lists.transform(tradingEvent.getSessionsList(), TradingSessionV1Factory::toSession));
	}

}
