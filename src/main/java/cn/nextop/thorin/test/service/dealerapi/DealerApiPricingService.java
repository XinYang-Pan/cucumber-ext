package cn.nextop.thorin.test.service.dealerapi;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import cn.nextop.thorin.api.dealer.proto.PricingServiceProtoV1.PricingEvent;
import cn.nextop.thorin.api.dealer.proto.PricingServiceProtoV1.SubscribePricingEventResponse;
import cn.nextop.thorin.test.vo.dlrapi.PricingConfig;
import support.shell.dlrapi.service.PricingService;

@Service
@Lazy
public class DealerApiPricingService extends DealerApiAbstractService {

	@Autowired
	private PricingService pricingService;

	@PostConstruct
	public void init() {
		super.init();
		// 
		SubscribePricingEventResponse subPricing = pricingService.subscribePricingEvent();
		assertThat(subPricing.getResult()).as(subPricing.toString()).isEqualTo(SubscribePricingEventResponse.Result.SUCCESS);
	}

	public PricingConfig next() {
		PricingEvent pricingEvent = pricingService.nextPricingEvent();
		PricingConfig pricingConfig = PricingConfig.from(pricingEvent);
		log.debug("nextPricingConfigFromDealerApi - {}", pricingConfig.toIdsString());
		return pricingConfig;
	}

	public PricingConfig get(Set<Short> brokerIds, Set<Short> lpIds) {
		PricingConfig pricingConfig = new PricingConfig();
		pricingConfig.setBkDeviationConfigPos(bkDeviationConfigPoService.getBkDeviations(brokerIds));
		pricingConfig.setBkPickingConfigPos(bkPickingConfigPoService.getBkPickings(brokerIds));
		pricingConfig.setBkSymbolConfigPos(bkSymbolConfigPoService.getBkSymbols(brokerIds));
		pricingConfig.setBkTuningConfigPos(bkTuningConfigPoService.getBkTunings(brokerIds));
		pricingConfig.setLpDeviationConfigPos(lpDeviationConfigPoService.getLpDeviations(lpIds));
		return pricingConfig;
	}

}
