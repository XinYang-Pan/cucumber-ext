package cn.nextop.thorin.test.service.dealer;

import java.util.Map;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.google.common.collect.Maps;

import cn.nextop.thorin.core.dealing.orm.po.DealingCounterpartyPo;
import cn.nextop.thorin.core.dealing.orm.po.DealingLadderPo;
import cn.nextop.thorin.core.dealing.orm.po.DealingPositionPo;
import cn.nextop.thorin.test.service.common.AbstractService;
import cn.nextop.thorin.test.vo.id.has.derivative.HasCounterpartyId;
import cn.nextop.thorin.test.vo.id.has.derivative.HasLadderId;
import cn.nextop.thorin.test.vo.id.has.derivative.HasPositionId;
import cn.nextop.thorin.test.vo.pricer.DealingCounterpartyDelta;
import cn.nextop.thorin.test.vo.pricer.DealingLadderDelta;
import cn.nextop.thorin.test.vo.pricer.DealingPositionDelta;

@Service
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class DealerSnapshotService extends AbstractService {
	// 
	private Map<Long, DealingCounterpartyPo> counterpartyMap = Maps.newHashMap();
	private Map<Long, DealingLadderPo> ladderMap = Maps.newHashMap();
	private Map<Long, DealingPositionPo> positionMap = Maps.newHashMap();

	public void clearAll() {
		counterpartyMap.clear();
		ladderMap.clear();
		positionMap.clear();
	}

	public void snapshotCounterparty(HasCounterpartyId... counterpartyIds) {
		for (HasCounterpartyId counterpartyId : counterpartyIds) {
			counterpartyMap.put(counterpartyId.counterpartyId(), dealingCounterpartyPoService.getByUniqueKey(counterpartyId));
		}
	}

	public void snapshotPosition(HasPositionId... positionIds) {
		for (HasPositionId positionId : positionIds) {
			positionMap.put(positionId.positionId(), dealingPositionPoService.getByUniqueKey(positionId));
		}
	}

	public void snapshotLadder(HasLadderId... ladderIds) {
		for (HasLadderId ladderId : ladderIds) {
			ladderMap.put(ladderId.ladderId(), dealingLadderPoService.getByUniqueKey(ladderId));
		}
	}

	public DealingCounterpartyDelta getCounterpartyDelta(HasCounterpartyId hasCounterpartyId) {
		long counterpartyId = hasCounterpartyId.counterpartyId();
		Assert.state(counterpartyMap.containsKey(counterpartyId), String.format("%s is not in snapshot.", counterpartyId));
		DealingCounterpartyPo now = dealingCounterpartyPoService.get(counterpartyId);
		DealingCounterpartyDelta delta = DealingCounterpartyDelta.delta(counterpartyMap.get(counterpartyId), now);
		counterpartyMap.put(counterpartyId, now);
		return delta;
	}

	public DealingPositionDelta getPositionDelta(HasPositionId hasPositionId) {
		long positionId = hasPositionId.positionId();
		Assert.state(positionMap.containsKey(positionId), String.format("%s is not in snapshot.", positionId));
		DealingPositionPo now = dealingPositionPoService.get(positionId);
		DealingPositionDelta delta = DealingPositionDelta.delta(positionMap.get(positionId), now);
		positionMap.put(positionId, now);
		return delta;
	}

	public DealingLadderDelta getLadderDelta(HasLadderId hasLadderId) {
		long ladderId = hasLadderId.ladderId();
		Assert.state(ladderMap.containsKey(ladderId), String.format("%s is not in snapshot.", ladderId));
		DealingLadderPo now = dealingLadderPoService.get(ladderId);
		DealingLadderDelta delta = DealingLadderDelta.delta(ladderMap.get(ladderId), now);
		ladderMap.put(ladderId, now);
		return delta;
	}

}
