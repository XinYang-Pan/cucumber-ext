package cn.nextop.thorin.test.service.core;

import static cn.nextop.thorin.test.service.core.SendLpQuoteParam.p;

import java.util.Comparator;
import java.util.List;

import org.apache.commons.collections4.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import cn.nextop.thorin.common.util.Objects;
import cn.nextop.thorin.common.util.concurrent.future.FutureEx;
import cn.nextop.thorin.common.util.datetime.TradingDate;
import cn.nextop.thorin.core.common.domain.glossary.decimal.Price;
import cn.nextop.thorin.core.common.orm.po.LpSymbolPo;
import cn.nextop.thorin.core.pricing.PricingManager;
import cn.nextop.thorin.core.pricing.domain.PricingGenerator;
import cn.nextop.thorin.core.pricing.domain.entity.LpBand;
import cn.nextop.thorin.core.pricing.domain.entity.LpQuote;
import cn.nextop.thorin.core.pricing.domain.entity.impl.LpBandImpl;
import cn.nextop.thorin.core.pricing.domain.entity.impl.LpQuoteImpl;
import cn.nextop.thorin.core.pricing.domain.pipeline.request.impl.FeedLpQuoteRequest;
import cn.nextop.thorin.core.pricing.domain.pipeline.response.PricingResponse;
import cn.nextop.thorin.core.pricing.domain.pipeline.response.impl.FeedLpQuoteResponse;
import cn.nextop.thorin.test.core.assertj.hard.ThorinAssertions;
import cn.nextop.thorin.test.service.common.AbstractService;
import cn.nextop.thorin.test.util.LpQuoteUtils;
import cn.nextop.thorin.test.util.PricerTestUtils;
import cn.nextop.thorin.test.vo.id.has.compound.HasLpSymbolId;
import cn.nextop.thorin.test.vo.pricer.LpBandVo;
import cn.nextop.thorin.test.vo.quote.LpQuoteWrapper;
import edu.emory.mathcs.backport.java.util.Collections;

@Service
@Lazy
public class LpQuoteService extends AbstractService {
	private boolean cacheResponse = false;
	@Autowired
	private PricingManager pricingManager;
	// 
	private final List<FeedLpQuoteResponse> responses = Lists.newArrayList();

	// -----------------------------
	// ----- Send Quotes Overloads - Sync
	// -----------------------------

	public FeedLpQuoteResponse sendLpQuote(SendLpQuoteParam param) {
		return this.doSendLpQuote(param);
	}

	public FeedLpQuoteResponse sendLpQuote(HasLpSymbolId lpSymbolId, Price bid, Price ask, int repeat, int tplus) {
		SendLpQuoteParam param = p(lpSymbolId).band0(bid, ask).repeat(repeat).tplus(tplus);
		return this.doSendLpQuote(param);
	}

	public FeedLpQuoteResponse sendLpQuote(HasLpSymbolId lpSymbolId, Price bid, Price ask, int repeat) {
		SendLpQuoteParam param = p(lpSymbolId).band0(bid, ask).repeat(repeat);
		return this.doSendLpQuote(param);
	}

	public FeedLpQuoteResponse sendLpQuote(HasLpSymbolId lpSymbolId, Price bid, Price ask, boolean tradable) {
		SendLpQuoteParam param = p(lpSymbolId).band0(bid, ask).tradable(tradable);
		return this.doSendLpQuote(param);
	}

	public FeedLpQuoteResponse sendLpQuote(HasLpSymbolId lpSymbolId, Price bid, Price ask) {
		SendLpQuoteParam param = p(lpSymbolId).band0(bid, ask);
		return this.doSendLpQuote(param);
	}

	public FeedLpQuoteResponse sendLpQuote(HasLpSymbolId lpSymbolId, List<LpBandVo> lpBandVos) {
		lpBandVos = Lists.newArrayList(lpBandVos);
		LpSymbolPo lpSymbolPo = lpSymbolPoService.getByUniqueKey(lpSymbolId);
		// 
		LpQuoteImpl lpQuoteImpl = LpQuoteUtils.buildLpQuoteImpl(lpSymbolPo, marketManager.getTradingDate());
		Collections.sort(lpBandVos, Comparator.comparing(LpBandVo::getIndex));
		for (LpBandVo lpBandVo : lpBandVos) {
			LpQuoteUtils.buildLpBandImpl(lpQuoteImpl, lpSymbolPo, lpBandVo.getSide(), lpBandVo.getType(), lpBandVo.getPrice(), lpBandVo.getVolume(), lpBandVo.getStatus(), 1);
		}
		return this.doSendQuote(lpQuoteImpl);
	}
	
	// -----------------------------
	// ----- Send Quotes Overloads - Async
	// -----------------------------

	public FutureEx<PricingResponse> asyncSendQuote(LpQuote lpQuote) {
		FeedLpQuoteRequest req = new FeedLpQuoteRequest(lpQuote);
		return pricingManager.invoke(req, false);
	}

	public FutureEx<PricingResponse> asyncSendQuote(HasLpSymbolId lpSymbolId, Price bid, Price ask, int tplus, boolean tradable) {
		LpQuote lpQuote = this.buildLpQuote(p(lpSymbolId).band0(bid, ask).tplus(tplus).tradable(tradable));
		return this.asyncSendQuote(lpQuote);
	}

	public FutureEx<PricingResponse> asyncSendLpQuote(HasLpSymbolId lpSymbolId, Price bid, Price ask) {
		return this.asyncSendQuote(lpSymbolId, bid, ask, 0, true);
	}

	// -----------------------------
	// ----- Send Quotes Overloads - Wrapper
	// -----------------------------

	public FeedLpQuoteResponse sendLpQuoteWrapper(LpQuoteWrapper lpQuoteWrapper) {
		try {
			log.debug("Sending {}", lpQuoteWrapper);
			Thread.sleep(lpQuoteWrapper.getDelay());
			return this.doSendQuote(lpQuoteWrapper.getLpQuote());
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	public void sendLpQuoteWrapper(List<LpQuoteWrapper> lpQuoteWrappers) {
		for (LpQuoteWrapper lpQuoteWrapper : lpQuoteWrappers) {
			this.sendLpQuoteWrapper(lpQuoteWrapper);
		}
	}

	// -----------------------------
	// ----- Others
	// -----------------------------

	private FeedLpQuoteResponse doSendLpQuote(SendLpQuoteParam p) {
		p.validate();
		FeedLpQuoteResponse feedLpQuoteResponse = null;
		for (int i = 0; i < p.getRepeat(); i++) {
			LpQuoteImpl lpQuote = this.buildLpQuote(p);
			feedLpQuoteResponse = this.doSendQuote(lpQuote);
		}
		return feedLpQuoteResponse;
	}

	private FeedLpQuoteResponse doSendQuote(LpQuoteImpl lpQuote) {
		try {
			updateIdAndExpireTime(lpQuote);
			FeedLpQuoteRequest req = new FeedLpQuoteRequest(lpQuote);
			log.debug("FeedLpQuoteRequest - {}", req);
			FutureEx<PricingResponse> responseFuture = pricingManager.invoke(req, false);
			FeedLpQuoteResponse feedLpQuoteResponse = Objects.cast(responseFuture.get());
			ThorinAssertions.assertThat(feedLpQuoteResponse).isSuccessful();
			if (cacheResponse) {
				responses.add(feedLpQuoteResponse);
				log.debug("FeedLpQuoteResponse {} - {} \n{}", Iterables.size(responses), feedLpQuoteResponse, PricerTestUtils.lpQuoteToString(feedLpQuoteResponse));
			} else {
				log.debug("FeedLpQuoteResponse - {} \n{}", feedLpQuoteResponse, PricerTestUtils.lpQuoteToString(feedLpQuoteResponse));
			}
			return feedLpQuoteResponse;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private void updateIdAndExpireTime(LpQuoteImpl lpQuote) {
		long id = PricingGenerator.getDefault().next();
		LpSymbolPo lpSymbolPo = lpSymbolPoService.get(lpQuote.getLpSymbolId());
		lpQuote.setId(id);
		long timestamp = PricingGenerator.getTimestamp(id);
		//
		long expireTime = timestamp + lpSymbolPo.getQuoteExpiration();
		for (LpBand lpBand : ListUtils.emptyIfNull(lpQuote.getBids())) {
			((LpBandImpl)lpBand).setExpireTime(expireTime);
		}
		for (LpBand lpBand : ListUtils.emptyIfNull(lpQuote.getAsks())) {
			((LpBandImpl)lpBand).setExpireTime(expireTime);
		}
	}

	private LpQuoteImpl buildLpQuote(SendLpQuoteParam p) {
		LpSymbolPo lpSymbolPo = lpSymbolPoService.getByUniqueKey(p.getLpSymbolId());
		TradingDate tradingDate = commonService.currentTradingDate().add(p.getTplus());
		LpQuoteImpl lpQuote = LpQuoteUtils.buildLpQuoteImpl(lpSymbolPo, tradingDate, p.getBid(), p.getAsk(), p.isTradable());
		if (p.isBidBug()) {
			lpQuote.getBids().get(0).getStatus().setSuspended(true);
		}
		if (p.isAskBug()) {
			lpQuote.getAsks().get(0).getStatus().setSuspended(true);
		}
		lpQuote.getStatus().setSuspended(p.isAnySideBug());
		return lpQuote;
	}

	// system generated bug quote method
//	private FeedLpQuoteResponse sendBugLpQuote(SendLpQuoteParam p) {
//		HasLpSymbolId lpSymbolId = p.getLpSymbolId();
//		LpDeviationConfigId lpDeviationConfigId = LpDeviationConfigId.toLpDeviationConfigId(lpSymbolId, PricingDeviationPolicy.DEFAULT);
//		// enable lp DeviationConfig  
//		LpDeviationConfigPo lpDeviationConfigPo = lpDeviationConfigPoService.updateByUniqueKey(lpDeviationConfigId, t -> {
//			t.setGap1(new BigDecimal("0.0005"));
//			t.setGap2(new BigDecimal("0.0005"));
//			t.setReferenceLpId((short) 0);
//			t.setDepth((short) 2);
//			t.setActive(true);
//		});
//		// Send T - X quotes, should end up with none bug quote
//		Price quote0Bid = p.getBid();
//		if (p.isBidBug()) {
//			BigDecimal bidBd = p.getBid().toDecimal();
//			quote0Bid = new Price(bidBd.multiply(new BigDecimal("0.8")).setScale(bidBd.scale(), RoundingMode.HALF_UP));
//		}
//		Price quote0Ask = p.getAsk();
//		if (p.isAskBug()) {
//			BigDecimal askBd = p.getAsk().toDecimal();
//			quote0Ask = new Price(askBd.multiply(new BigDecimal("1.2")).setScale(askBd.scale(), RoundingMode.HALF_UP));
//		}
//		FeedLpQuoteResponse feedLpQuoteResponse = this.sendLpQuote(lpSymbolId, quote0Bid, quote0Ask, lpDeviationConfigPo.getDepth() + 1);
//		LpQuote lpQuote = PricerTestUtils.getOnlyLpQuote(feedLpQuoteResponse);
//		ThorinAssertions.assertThat(lpQuote).notBugQuote();
//		// Send the bug quote
//		feedLpQuoteResponse = this.sendLpQuote(lpSymbolId, p.getBid(), p.getAsk());
//		lpQuote = PricerTestUtils.getOnlyLpQuote(feedLpQuoteResponse);
//		ThorinAssertions.assertThat(lpQuote).isDeviationStatus(true, p.isBidBug(), p.isAskBug());
//		// disable lp DeviationConfig  
//		lpDeviationConfigPoService.updateByUniqueKey(lpDeviationConfigId, t -> t.setActive(false));
//		return feedLpQuoteResponse;
//	}
	
	
	// -----------------------------
	// ----- Get Set ToString HashCode Equals
	// -----------------------------
	public boolean isCacheResponse() {
		return cacheResponse;
	}

	public void setCacheResponse(boolean cacheResponse) {
		this.cacheResponse = cacheResponse;
	}

}
