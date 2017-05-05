package cn.nextop.thorin.test.service.common.po.dealing;

import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Converter;

import cn.nextop.thorin.core.dealing.DealingPositionManager;
import cn.nextop.thorin.core.dealing.domain.entity.DealingRoom;
import cn.nextop.thorin.core.dealing.orm.po.DealingLadderPo;
import cn.nextop.thorin.test.service.common.po.IdCalculableService;
import cn.nextop.thorin.test.vo.id.has.derivative.HasLadderId;
import cn.nextop.thorin.test.vo.id.obj.derivative.LadderId;

@Service
public class DealingLadderPoService extends IdCalculableService<Long, DealingLadderPo, HasLadderId> {

	@Autowired
	private DealingPositionManager dealingPositionManager;
	
	@PostConstruct
	public void init() {
		getNullable = true;
	}
	
	@Override
	protected Long getId(DealingLadderPo po) {
		return po.getId();
	}

	@Override
	protected Converter<Long, HasLadderId> getConverter() {
		return new Converter<Long, HasLadderId>() {

			@Override
			protected HasLadderId doForward(Long ladderId) {
				return LadderId.toLadderId(ladderId);
			}

			@Override
			protected Long doBackward(HasLadderId hasLadderId) {
				return hasLadderId.ladderId();
			}

		};
	}

	@Override
	protected DealingLadderPo doGetByUniqueKey(HasLadderId ladderId) {
		DealingRoom dealingRoom = dealingPositionManager.getRoom(ladderId.coSymbolId());
		return dealingRoom.getLadder(ladderId.ladderId());
	}

	@Override
	protected Map<Long, DealingLadderPo> update(Map<Long, DealingLadderPo> valueMap) {
		throw new UnsupportedOperationException();
	}

}
