package cn.nextop.thorin.test.service.trigger;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import cn.nextop.thorin.common.util.collection.map.IntHashMap;
import cn.nextop.thorin.common.util.collection.page.Page;
import cn.nextop.thorin.core.holding.domain.HoldingGenerator;
import cn.nextop.thorin.core.trigger.TriggerStrategyAdministrator;
import cn.nextop.thorin.core.trigger.domain.entity.PricingTriggerInstance;
import cn.nextop.thorin.core.trigger.domain.entity.TriggerStrategy;
import cn.nextop.thorin.core.trigger.orm.po.DealingTriggerExecutionPo;
import cn.nextop.thorin.core.trigger.orm.po.MulSpreadTriggerInstancePo;
import cn.nextop.thorin.core.trigger.orm.po.PricingTriggerExecutionPo;
import cn.nextop.thorin.core.trigger.orm.po.SmaShadeTriggerInstancePo;
import cn.nextop.thorin.core.trigger.orm.po.TvrShadeTriggerInstancePo;
import cn.nextop.thorin.core.trigger.orm.query.DealingTriggerExecutionQuery;
import cn.nextop.thorin.core.trigger.orm.query.PricingTriggerExecutionQuery;
import cn.nextop.thorin.test.service.common.AbstractService;
import cn.nextop.thorin.test.vo.dlrapi.TriggerData;
import cn.nextop.thorin.test.vo.id.obj.derivative.TriggerId;

@Service
@Lazy
public class TriggerService extends AbstractService {

	@Autowired
	private TriggerStrategyAdministrator triggerStrategyAdministrator;

	public List<DealingTriggerExecutionPo> getLatestDealingTriggerExecutionPos(int bookId, short symbolId, int returnSize) {
		try {
			Thread.sleep(1000L);
			DealingTriggerExecutionQuery query = new DealingTriggerExecutionQuery();
			byte companyId = HoldingGenerator.getCompanyIdByBookId(bookId);
			query.setCompanyId(companyId);
			query.setSymbolId(symbolId);
			query.setPageNo(1);
			query.setPageSize(returnSize);
			query.setBookId(bookId);
			Page<DealingTriggerExecutionPo> paginateExecutions = triggerStrategyAdministrator.paginateExecutions(query);
			return paginateExecutions.getRecords();
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	public List<PricingTriggerExecutionPo> getLatestPricingTriggerExecutionPos(PricingTriggerInstance pricingTriggerInstance, int returnSize) {
		try {
			Thread.sleep(1000L);
			PricingTriggerExecutionQuery query = new PricingTriggerExecutionQuery();
			query.setCompanyId(pricingTriggerInstance.getCompanyId());
			query.setSymbolId(pricingTriggerInstance.getSymbolId());
			query.setPageNo(1);
			query.setPageSize(returnSize);
			query.setTriggerPolicy(pricingTriggerInstance.getPolicy());
			query.setBrokerId(pricingTriggerInstance.getBrokerId());
			query.setTriggerId(pricingTriggerInstance.getId());
			query.setAsc(true);
			Page<PricingTriggerExecutionPo> paginateExecutions = triggerStrategyAdministrator.paginateExecutions(query);
			return paginateExecutions.getRecords();
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	public List<PricingTriggerExecutionPo> getLatestPricingTriggerExecutionPos(TriggerId triggerId, int returnSize) {
		try {
			Thread.sleep(1000L);
			PricingTriggerExecutionQuery query = new PricingTriggerExecutionQuery();
			query.setCompanyId(triggerId.coId());
			query.setSymbolId(triggerId.symbolId());
			query.setPageNo(1);
			query.setPageSize(returnSize);
			query.setTriggerPolicy(triggerId.triggerPolicy());
			query.setTriggerId(triggerId.triggerId());
			query.setAsc(true);
			Page<PricingTriggerExecutionPo> paginateExecutions = triggerStrategyAdministrator.paginateExecutions(query);
			return paginateExecutions.getRecords();
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	public void forceDeleteAllPricerTriggers() {
		TriggerData triggerData = this.get(this.commonService.getAllCompanyIds());
		mulSpreadTriggerInstancePoService.forceDelete(triggerData.getMulSpreadTriggerInstancePos());
		smaShadeTriggerInstancePoService.forceDelete(triggerData.getSmaShadeTriggerInstancePos());
		tvrShadeTriggerInstancePoService.forceDelete(triggerData.getTvrShadeTriggerInstancePos());
	}

	public TriggerData get(Set<Byte> companyIds) {
		List<SmaShadeTriggerInstancePo> smaShadeTriggerInstancePos = Lists.newArrayList();
		List<TvrShadeTriggerInstancePo> tvrShadeTriggerInstancePos = Lists.newArrayList();
		List<MulSpreadTriggerInstancePo> mulSpreadTriggerInstancePo = Lists.newArrayList();
		// 
		for (Byte companyId : companyIds) {
			IntHashMap<TriggerStrategy> strategies = triggerStrategyAdministrator.getStrategies(companyId);
			for (TriggerStrategy triggerStrategy : strategies.values()) {
				smaShadeTriggerInstancePos.addAll(triggerStrategy.getSmaShadeTriggerInstances().values());
				tvrShadeTriggerInstancePos.addAll(triggerStrategy.getTvrShadeTriggerInstances().values());
				mulSpreadTriggerInstancePo.addAll(triggerStrategy.getMulSpreadTriggerInstances().values());
			}
		}
		// 
		TriggerData triggerData = new TriggerData();
		triggerData.setSmaShadeTriggerInstancePos(smaShadeTriggerInstancePos);
		triggerData.setTvrShadeTriggerInstancePos(tvrShadeTriggerInstancePos);
		triggerData.setMulSpreadTriggerInstancePos(mulSpreadTriggerInstancePo);
		return triggerData;
	}

}
