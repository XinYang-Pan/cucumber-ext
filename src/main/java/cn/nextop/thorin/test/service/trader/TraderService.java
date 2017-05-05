package cn.nextop.thorin.test.service.trader;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.google.common.collect.Lists;

import cn.nextop.thorin.core.common.glossary.PricingTuningStatus;
import cn.nextop.thorin.core.common.glossary.TradeType;
import cn.nextop.thorin.core.trading.TradingManager;
import cn.nextop.thorin.core.trading.domain.TradingGenerator;
import cn.nextop.thorin.core.trading.domain.pipeline.request.impl.FeedExecutionRequest;
import cn.nextop.thorin.core.trading.domain.pipeline.response.TradingResponse;
import cn.nextop.thorin.core.trading.domain.pipeline.response.impl.FeedExecutionResponse;
import cn.nextop.thorin.core.trading.orm.po.TradingExecutionPo;
import cn.nextop.thorin.test.core.assertj.hard.ThorinAssertions;
import cn.nextop.thorin.test.service.common.AbstractService;
import cn.nextop.thorin.test.util.TestUtils;
import cn.nextop.thorin.test.vo.id.obj.compound.BkSymbolId;

@Service
@Lazy
public class TraderService extends AbstractService {
	@Autowired
	private TradingManager tradingManager;
	// 
	private FeedExecutionResponse lastFeedExecutionResponse;

	public FeedExecutionResponse feedTradingExecutions(List<TradingExecutionPo> tradingExecutionPos) throws InterruptedException, ExecutionException {
		short brokerId = this.decorateAssertSameBroker(tradingExecutionPos);
		FeedExecutionRequest feedExecutionRequest = new FeedExecutionRequest(brokerId, tradingExecutionPos);
		// 
		log.debug("Sending - {}", feedExecutionRequest);
		TradingResponse tradingResponse = tradingManager.invoke(feedExecutionRequest, true).get();
		log.debug("{}", tradingResponse);
		// 
		ThorinAssertions.assertThat(tradingResponse).isSuccessful();
		lastFeedExecutionResponse = (FeedExecutionResponse) tradingResponse;
		return lastFeedExecutionResponse;
	}

	private short decorateAssertSameBroker(List<TradingExecutionPo> tradingExecutionPos) {
		short brokerId = 0;
		tradingExecutionPos.stream().forEach(this::decorate);
		for (TradingExecutionPo tradingExecutionPo : tradingExecutionPos) {
			if (brokerId == 0) {
				brokerId = tradingExecutionPo.getBrokerId();
			} else {
				Assert.isTrue(brokerId == tradingExecutionPo.getBrokerId());
			}
		}
		return brokerId;
	}

	public void decorate(TradingExecutionPo tradingExecutionPo) {
		long id = TradingGenerator.getDefault().next();
		BkSymbolId bkSymbolId = BkSymbolId.toBkSymbolId(tradingExecutionPo.getBrokerId(), tradingExecutionPo.getSymbolId());
		tradingExecutionPo.setId(id);
		tradingExecutionPo.setBkSymbolId(bkSymbolId.bkSymbolId());
		tradingExecutionPo.setExecuteDate(marketManager.getTradingDate());
		if (tradingExecutionPo.getOpenTime() == 0) {
			tradingExecutionPo.setOpenTime(System.currentTimeMillis());
		}
		tradingExecutionPo.setTuning(new PricingTuningStatus());
		TestUtils.setCommonForInsert(tradingExecutionPo);
		if (tradingExecutionPo.getTradeType() == TradeType.OPEN) {
			tradingExecutionPo.setExecuteTime(tradingExecutionPo.getOpenTime());
			tradingExecutionPo.setExecutePrice(tradingExecutionPo.getOpenPrice());
			tradingExecutionPo.setExecuteVolume(tradingExecutionPo.getOpenVolume());
		}
	}

	public FeedExecutionResponse getLastFeedExecutionResponse() {
		return lastFeedExecutionResponse;
	}

	public TradingExecutionPo getLatestExecution() {
		List<TradingExecutionPo> executions = Lists.newArrayList(this.getLastFeedExecutionResponse().executions());
		return executions.stream().max(Comparator.comparing(TradingExecutionPo::getId)).get();
	}

}
