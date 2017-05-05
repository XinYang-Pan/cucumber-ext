package cn.nextop.thorin.test.service.core;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import cn.nextop.thorin.core.common.domain.glossary.decimal.Price;
import cn.nextop.thorin.core.common.glossary.BidAsk;
import cn.nextop.thorin.core.common.glossary.PricingBandStatus;
import cn.nextop.thorin.core.common.glossary.PricingBandType;
import cn.nextop.thorin.core.common.glossary.PricingQuoteStatus;
import cn.nextop.thorin.core.common.glossary.PricingQuoteType;
import cn.nextop.thorin.core.common.glossary.PricingTuningStatus;
import cn.nextop.thorin.core.common.orm.po.BkSymbolPo;
import cn.nextop.thorin.core.pricing.domain.entity.impl.BkBandImpl;
import cn.nextop.thorin.core.pricing.domain.entity.impl.BkQuoteImpl;
import cn.nextop.thorin.core.pricing.domain.pipeline.response.impl.FeedLpQuoteResponse;
import cn.nextop.thorin.core.pricing.domain.pipeline.response.impl.result.FeedLpQuoteResult;
import cn.nextop.thorin.test.core.SimpleLongGenerator;
import cn.nextop.thorin.test.service.common.AbstractService;
import cn.nextop.thorin.test.vo.id.obj.compound.BkSymbolId;

@Service
@Lazy
public class BkQuoteService extends AbstractService {
	// 
	private SimpleLongGenerator simpleLongGenerator = new SimpleLongGenerator();

	public void sendFeedLpQuoteResponse(BkQuoteImpl bkQuoteImpl) {
		BkSymbolId bkSymbolId = BkSymbolId.toBkSymbolId(bkQuoteImpl.getBkSymbolId());
		// 
		FeedLpQuoteResponse response = new FeedLpQuoteResponse();
		response.setCompanyId(bkSymbolId.coId());
		response.setSymbolId(bkSymbolId.symbolId());
		response.setResult(FeedLpQuoteResult.SUCCESS);
		response.setQuotes(Lists.newArrayList(bkQuoteImpl));
		// 
		log.debug("Sending {}", response);
		messengerService.sendResponse(response, "pricing.server");
	}

	public BkQuoteImpl build(Price mid, long quoteTime, BkSymbolId bkSymbolId) {
		BkSymbolPo bkSymbolPo = bkSymbolPoService.getByUniqueKey(bkSymbolId);
		byte scale = bkSymbolPo.getScale();
		// 
		BkBandImpl bidBand = new BkBandImpl();
		bidBand.setBidAsk(BidAsk.BID);
		bidBand.setLadder((byte) 0);
		bidBand.setPrice(mid.add(-1, scale));
		bidBand.setRawPrice(mid.add(-1, scale));
		bidBand.setStatus(PricingBandStatus.ACTIVE);
		bidBand.setType(PricingBandType.NEW);
		bidBand.setVolume(10000);
		// 
		BkBandImpl askBand = new BkBandImpl();
		askBand.setBidAsk(BidAsk.ASK);
		askBand.setLadder((byte) 0);
		askBand.setPrice(mid.add(1, scale));
		askBand.setRawPrice(mid.add(1, scale));
		askBand.setStatus(PricingBandStatus.ACTIVE);
		askBand.setType(PricingBandType.NEW);
		askBand.setVolume(10000);
		// 
		long id = simpleLongGenerator.next(quoteTime);
		BkQuoteImpl bkQuote = new BkQuoteImpl();
		bkQuote.setId(id);
		bkQuote.setBkSymbolId(bkSymbolId.bkSymbolId());
		bkQuote.setBids(Lists.newArrayList(bidBand));
		bkQuote.setAsks(Lists.newArrayList(askBand));
		bkQuote.setDate(marketManager.getTradingDate());
		bkQuote.setScale(scale);
		bkQuote.setStatus(PricingQuoteStatus.ACTIVE);
		bkQuote.setTuningStatus(PricingTuningStatus.MA_SHADE);
		bkQuote.setType(PricingQuoteType.SNAPSHOT);
		bkQuote.setVersion(1);
		return bkQuote;
	}

}
