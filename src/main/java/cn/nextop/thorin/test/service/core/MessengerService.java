package cn.nextop.thorin.test.service.core;

import static com.google.common.collect.Iterators.filter;
import static com.google.common.collect.Iterators.getOnlyElement;

import java.math.RoundingMode;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.google.common.base.Suppliers;
import com.google.common.collect.Lists;

import cn.nextop.thorin.common.messaging.Channel;
import cn.nextop.thorin.common.messaging.Messenger;
import cn.nextop.thorin.common.util.marshaller.Marshallable;
import cn.nextop.thorin.core.common.domain.CommonGenerator;
import cn.nextop.thorin.core.common.domain.pipeline.Response;
import cn.nextop.thorin.core.common.domain.pipeline.Responses;
import cn.nextop.thorin.core.common.glossary.HedgingExecuteResult;
import cn.nextop.thorin.core.dealing.domain.pipeline.response.impl.ExecuteOrderResponse;
import cn.nextop.thorin.core.dealing.domain.pipeline.response.impl.PlaceOrderResponse;
import cn.nextop.thorin.core.dealing.orm.po.DealingOrderPo;
import cn.nextop.thorin.core.hedging.domain.HedgingGenerator;
import cn.nextop.thorin.core.hedging.domain.pipeline.response.impl.result.ExecuteOrderResult;
import cn.nextop.thorin.core.hedging.orm.po.HedgingExecutionPo;
import cn.nextop.thorin.test.util.TestUtils;
import cn.nextop.thorin.test.vo.id.obj.compound.LpSymbolId;
import reactor.core.publisher.WorkQueueProcessor;

@Service
@Lazy
public class MessengerService  {
	protected final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	protected Messenger<Marshallable> messenger;
	//
	private WorkQueueProcessor<ExecuteOrderResponse> processor;

	public void sendResponse(Response response, String channel) {
		response.setId(CommonGenerator.getDefault().next());
		messenger.send(messenger.getChannel(channel), response);
	}

	public void registerPlaceOrderResponse() {
		Suppliers.memoize(this::doRegisterPlaceOrderResponse).get();
	}

	private Void doRegisterPlaceOrderResponse() {
		processor = WorkQueueProcessor.create();
		Channel channel = messenger.getChannel("dealing.server");
		messenger.addListener(channel, m -> {
			Marshallable payload = m.getPayload();
			if (payload instanceof Responses) {
				List<? extends Response> responses = ((Responses) payload).getResponses();
				for (Response response : responses) {
					log.info("DealingResponse - {}", response);
					if (response instanceof PlaceOrderResponse) {
						PlaceOrderResponse resp = (PlaceOrderResponse) response;
						log.info("Dealing PlaceOrderResponse - {}", resp);
						// 
						DealingOrderPo dealingOrderPo = getOnlyElement(filter(resp.orders(), dealingOrderPo1 -> dealingOrderPo1.getLpId() != 0));
						executeDealingOrderPo(dealingOrderPo);
					} else if (response instanceof ExecuteOrderResponse) {
						ExecuteOrderResponse resp = (ExecuteOrderResponse) response;
						log.info("Dealing ExecuteOrderResponse - {}", resp);
						processor.onNext(resp);
					}
				}
			}
		});
		return null;
	}

	private void executeDealingOrderPo(DealingOrderPo dealingOrderPo) {
		HedgingExecutionPo hedgingExecutionPo = new HedgingExecutionPo();
		hedgingExecutionPo.setId(HedgingGenerator.getDefault().next());
		hedgingExecutionPo.setLpId(dealingOrderPo.getLpId());
		hedgingExecutionPo.setBookId(dealingOrderPo.getBookId());
		hedgingExecutionPo.setSymbolId(dealingOrderPo.getSymbolId());
		hedgingExecutionPo.setSourceId(dealingOrderPo.getId());
		hedgingExecutionPo.setCompanyId(dealingOrderPo.getCompanyId());
		hedgingExecutionPo.setLpOrderId(dealingOrderPo.getLpOrderId());
		hedgingExecutionPo.setLpSymbolId(LpSymbolId.toLpSymbolId(dealingOrderPo.getLpId(), dealingOrderPo.getSymbolId()).lpSymbolId());
		hedgingExecutionPo.setLpExecuteId("from-web-test");
		hedgingExecutionPo.setSide(dealingOrderPo.getSide());
		hedgingExecutionPo.setSettleDate(dealingOrderPo.getOrderDate());
		hedgingExecutionPo.setExecuteDate(dealingOrderPo.getOrderDate());
		hedgingExecutionPo.setExecuteTime(dealingOrderPo.getExecuteTime());
		hedgingExecutionPo.setExecutePrice(dealingOrderPo.getOrderPrice());
		hedgingExecutionPo.setExecuteVolume(dealingOrderPo.getOrderVolume());
		hedgingExecutionPo.setExecuteAmount(dealingOrderPo.getOrderPrice().multiply(dealingOrderPo.getOrderVolume()).setScale(2, RoundingMode.HALF_UP));
		hedgingExecutionPo.setExecuteResult(HedgingExecuteResult.SUCCESS);
		hedgingExecutionPo.setExecuteComment("from-web-test-comment");
		TestUtils.setCommonForInsert(hedgingExecutionPo);
		// 
		cn.nextop.thorin.core.hedging.domain.pipeline.response.impl.ExecuteOrderResponse response = new cn.nextop.thorin.core.hedging.domain.pipeline.response.impl.ExecuteOrderResponse();
		response.setLpId(hedgingExecutionPo.getLpId());
		response.setExecutions(Lists.newArrayList(hedgingExecutionPo));
		response.setResult(ExecuteOrderResult.SUCCESS);
//		this.sendResponse(response, "hedging.server");
	}

}
