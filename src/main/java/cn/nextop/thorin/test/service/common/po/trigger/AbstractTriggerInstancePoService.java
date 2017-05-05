package cn.nextop.thorin.test.service.common.po.trigger;

import static com.google.common.collect.Iterables.filter;
import static com.google.common.collect.Iterables.getOnlyElement;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import com.google.common.collect.Iterables;

import cn.nextop.thorin.common.glossary.Versionable;
import cn.nextop.thorin.common.persistence.jpa.PersistentObject;
import cn.nextop.thorin.core.common.domain.CommonGenerator;
import cn.nextop.thorin.core.common.domain.glossary.CompanyAware;
import cn.nextop.thorin.core.common.glossary.TriggerPolicy;
import cn.nextop.thorin.core.common.glossary.TriggerStatus;
import cn.nextop.thorin.core.trigger.TriggerStrategyAdministrator;
import cn.nextop.thorin.core.trigger.domain.TriggerGenerator;
import cn.nextop.thorin.core.trigger.domain.entity.TriggerInstance;
import cn.nextop.thorin.core.trigger.domain.entity.TriggerStrategy;
import cn.nextop.thorin.core.trigger.orm.po.TriggerSummaryPo;
import cn.nextop.thorin.test.service.common.po.AbstractPoService;
import cn.nextop.thorin.test.util.TestUtils;

public abstract class AbstractTriggerInstancePoService<T extends TriggerInstance & CompanyAware & Versionable & PersistentObject> extends AbstractPoService<Long, T> {

	@Autowired
	protected TriggerStrategyAdministrator triggerStrategyAdministrator;

	@Override
	protected final Long getId(T po) {
		return po.getId();
	}

	@Override
	protected final T doUpdate(T input) {
		// 
		List<T> smaShadeTriggerInstancePos = this.updateRaw(input);
		for (T smaShadeTriggerInstancePo : smaShadeTriggerInstancePos) {
			if (input.getId() == smaShadeTriggerInstancePo.getId()) {
				return smaShadeTriggerInstancePo;
			}
		}
		throw new RuntimeException("Internal Error! ref = " + input);
	}

	// -----------------------------
	// ----- New Public Methods
	// -----------------------------

	public final T insert(T input) {
		List<T> raws = this.insertRaw(input);
		T t = getOnlyElement(filter(raws, raw -> raw.getStatus() == TriggerStatus.ACTIVE));
		return t;
	}

	public final List<T> insertRaw(T input) {
		int coSymbolId = CommonGenerator.toCoSymbolId(input.getCompanyId(), input.getSymbolId());
		input.setCoSymbolId(coSymbolId);
		TestUtils.setCommonForInsert(input);
		return this.updateRequest(input);
	}

	public final List<T> updateRaw(T input) {
		return this.updateRequest(input);
	}

	public final T delete(T input) {
		Assert.state(input.getStatus() != TriggerStatus.ACTIVE);
		input.setStatus(TriggerStatus.DELETED);
		return Iterables.getOnlyElement(this.updateRequest(input));
	}

	@SuppressWarnings("unchecked")
	public final T forceDelete(T target) {
		switch (target.getStatus()) {
		case ACTIVE:
			// Update to STANDBY
			T copy = (T) target.copy();
			copy.setStatus(TriggerStatus.STANDBY);
			target = (T) this.update(copy).copy();
		case STANDBY:
			target = this.delete(target);
		case DELETED:
			break;
		default:
			break;
		}
		return target;
	}
	
	public final T update(long triggerId, TriggerStatus triggerStatus) {
		T t = this.get(triggerId);
		switch (triggerStatus) {
		case ACTIVE:
		case STANDBY:
			return this.update(t);
		case DELETED:
			return this.forceDelete(t);
		default:
			throw new RuntimeException();
		}
	}
	
	public final List<T> forceDelete(List<T> targets) {
		return targets.stream().map(this::forceDelete).collect(Collectors.toList());
	}

	// -----------------------------
	// ----- Protected Methods
	// -----------------------------

	protected final TriggerStrategy getStrategy(Long id) {
		byte companyId = TriggerGenerator.getCompanyIdByTriggerId(id);
		short symbolId = TriggerGenerator.getSymbolIdByTriggerId(id);
		TriggerStrategy strategy = triggerStrategyAdministrator.getStrategy(companyId, symbolId);
		return strategy;
	}

	public long toTriggerId(byte companyId, short symbolId, TriggerPolicy triggerPolicy, int tseq) {
		TriggerStrategy strategy = triggerStrategyAdministrator.getStrategy(companyId, symbolId);
		TriggerSummaryPo triggerSummaryPo = strategy.getSummary();
		int triggerSequence = triggerSummaryPo.getTriggerSequence() - tseq;
		return TriggerGenerator.toTriggerId(companyId, symbolId, triggerPolicy, triggerSequence);
	}

	protected abstract List<T> updateRequest(T input);

}
