package cn.nextop.thorin.test.util;

import static cn.nextop.thorin.common.util.Iterators.iterable;

import java.util.List;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import cn.nextop.thorin.core.dealing.domain.pipeline.request.impl.AbstractDealingShardableRequest;
import cn.nextop.thorin.core.dealing.domain.pipeline.request.impl.ExecuteOrderRequest;
import cn.nextop.thorin.core.dealing.domain.pipeline.request.impl.RejectOrderRequest;
import cn.nextop.thorin.core.dealing.orm.po.DealingOrderPo;
import cn.nextop.thorin.core.hedging.domain.HedgingGenerator;
import cn.nextop.thorin.core.hedging.domain.pipeline.response.HedgingShardableResponse;
import cn.nextop.thorin.core.hedging.domain.pipeline.response.impl.ExecuteOrderResponse;
import cn.nextop.thorin.core.hedging.domain.pipeline.response.impl.result.ExecuteOrderResult;
import cn.nextop.thorin.core.hedging.orm.po.HedgingExecutionPo;
import cn.nextop.thorin.core.hedging.orm.po.HedgingRejectionPo;
import cn.nextop.thorin.test.vo.dealer.LpExecuteDetail;
import cn.nextop.thorin.test.vo.id.obj.compound.LpSymbolId;

public class DealerTestUtils {

	public static ExecuteOrderResponse buildExecuteOrderResponse(DealingOrderPo dealingOrderPo, LpExecuteDetail lpExecuteDetail) {
		HedgingExecutionPo hedgingExecutionPo = new HedgingExecutionPo();
		hedgingExecutionPo.setId(HedgingGenerator.getDefault().next());
		hedgingExecutionPo.setLpId(lpExecuteDetail.getLpId());
		hedgingExecutionPo.setBookId(dealingOrderPo.getBookId());
		hedgingExecutionPo.setSymbolId(dealingOrderPo.getSymbolId());
		hedgingExecutionPo.setSourceId(dealingOrderPo.getId());
		hedgingExecutionPo.setCompanyId(dealingOrderPo.getCompanyId());
		hedgingExecutionPo.setLpOrderId(dealingOrderPo.getLpOrderId());
		hedgingExecutionPo.setLpSymbolId(LpSymbolId.toLpSymbolId(hedgingExecutionPo.getLpId(), dealingOrderPo.getSymbolId()).lpSymbolId());
		hedgingExecutionPo.setLpExecuteId("from-web-test");
		hedgingExecutionPo.setSide(dealingOrderPo.getSide());
		hedgingExecutionPo.setSettleDate(dealingOrderPo.getOrderDate().add(lpExecuteDetail.getTday()));
		hedgingExecutionPo.setExecuteDate(dealingOrderPo.getOrderDate());
		hedgingExecutionPo.setExecuteTime(dealingOrderPo.getOrderTime());
		hedgingExecutionPo.setExecutePrice(lpExecuteDetail.getPrice());
		hedgingExecutionPo.setExecuteVolume(lpExecuteDetail.getVolume());
		// Amount should not be set, but be asserted
//		hedgingExecutionPo.setExecuteAmount(lpExecuteDetail.getAmount());
		hedgingExecutionPo.setExecuteResult(lpExecuteDetail.getResult());
		hedgingExecutionPo.setExecuteComment("from-web-test-comment");
		TestUtils.setCommonForInsert(hedgingExecutionPo);
		// 
		ExecuteOrderResponse response = new ExecuteOrderResponse();
		response.setLpId(hedgingExecutionPo.getLpId());
		response.setExecutions(Lists.newArrayList(hedgingExecutionPo));
		response.setResult(ExecuteOrderResult.SUCCESS);
		return response;
	}

	public static AbstractDealingShardableRequest buildOnlyRequest(HedgingShardableResponse response) {
		return Iterables.getOnlyElement(buildRequests(response));
	}

	public static List<AbstractDealingShardableRequest> buildRequests(HedgingShardableResponse response) {
		List<AbstractDealingShardableRequest> reqList = Lists.newArrayList();
		for (HedgingRejectionPo v : iterable(response.rejections())) {
			reqList.add(onRejectOrder(response, v));
		}
		for (HedgingExecutionPo v : iterable(response.executions())) {
			reqList.add(onExecuteOrder(response, v));
		}
		return reqList;
	}

	private static ExecuteOrderRequest onExecuteOrder(HedgingShardableResponse response, HedgingExecutionPo v) {
		final ExecuteOrderRequest r = new ExecuteOrderRequest(response.getCompanyId(), v.getSymbolId());
		r.setContextId(response.getId());
		r.setBookId(v.getBookId());
		r.setExecution(v);
		return r;
	}

	private static RejectOrderRequest onRejectOrder(HedgingShardableResponse response, HedgingRejectionPo v) {
		final RejectOrderRequest r = new RejectOrderRequest(response.getCompanyId(), v.getSymbolId());
		r.setContextId(response.getId());
		r.setBookId(v.getBookId());
		r.setOrderId(v.getSourceId());
		return r;
	}

}
