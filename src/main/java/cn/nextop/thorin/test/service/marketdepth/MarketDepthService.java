package cn.nextop.thorin.test.service.marketdepth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import cn.nextop.thorin.core.pricing.domain.dom.MarketDepth;
import cn.nextop.thorin.core.pricing.domain.dom.impl.MarketDepthImpl;
import cn.nextop.thorin.core.pricing.domain.dom.pipeline.request.impl.FeedQuoteRequest;
import cn.nextop.thorin.core.pricing.domain.entity.LpQuote;
import cn.nextop.thorin.test.service.common.AbstractService;

@Service
@Lazy
public class MarketDepthService extends AbstractService {
	@Autowired
	private MarketDepth marketDepth;
	
	public void feedQuote(LpQuote lpQuote) {
		FeedQuoteRequest request = new FeedQuoteRequest(lpQuote);
		((MarketDepthImpl)marketDepth).invoke(request);
	}

	public MarketDepth getMarketDepth() {
		return marketDepth;
	}

}
