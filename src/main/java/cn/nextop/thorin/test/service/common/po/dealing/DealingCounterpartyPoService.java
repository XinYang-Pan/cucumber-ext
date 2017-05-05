package cn.nextop.thorin.test.service.common.po.dealing;

import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Converter;

import cn.nextop.thorin.core.dealing.DealingPositionManager;
import cn.nextop.thorin.core.dealing.domain.entity.DealingRoom;
import cn.nextop.thorin.core.dealing.orm.po.DealingCounterpartyPo;
import cn.nextop.thorin.test.service.common.po.IdCalculableService;
import cn.nextop.thorin.test.vo.id.has.derivative.HasCounterpartyId;
import cn.nextop.thorin.test.vo.id.obj.derivative.CounterpartyId;

@Service
public class DealingCounterpartyPoService extends IdCalculableService<Long, DealingCounterpartyPo, HasCounterpartyId> {

	@Autowired
	private DealingPositionManager dealingPositionManager;

	@PostConstruct
	public void init() {
		getNullable = true;
	}
	
	@Override
	protected Long getId(DealingCounterpartyPo po) {
		return po.getId();
	}

	@Override
	protected Converter<Long, HasCounterpartyId> getConverter() {
		return new Converter<Long, HasCounterpartyId>() {

			@Override
			protected HasCounterpartyId doForward(Long counterpartyId) {
				return CounterpartyId.toCounterpartyId(counterpartyId);
			}

			@Override
			protected Long doBackward(HasCounterpartyId hasCounterpartyId) {
				return hasCounterpartyId.counterpartyId();
			}

		};
	}

	@Override
	protected DealingCounterpartyPo doGetByUniqueKey(HasCounterpartyId counterpartyId) {
		DealingRoom dealingRoom = dealingPositionManager.getRoom(counterpartyId.coSymbolId());
		return dealingRoom.getCounterparty(counterpartyId.counterpartyId());
	}

	@Override
	protected Map<Long, DealingCounterpartyPo> update(Map<Long, DealingCounterpartyPo> valueMap) {
		throw new UnsupportedOperationException();
	}

}
