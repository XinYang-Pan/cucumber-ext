package cn.nextop.thorin.test.service.common.po.dealing;

import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Converter;

import cn.nextop.thorin.core.dealing.DealingPositionManager;
import cn.nextop.thorin.core.dealing.domain.entity.DealingRoom;
import cn.nextop.thorin.core.dealing.orm.po.DealingPositionPo;
import cn.nextop.thorin.test.service.common.po.IdCalculableService;
import cn.nextop.thorin.test.vo.id.has.derivative.HasPositionId;
import cn.nextop.thorin.test.vo.id.obj.derivative.PositionId;

@Service
public class DealingPositionPoService extends IdCalculableService<Long, DealingPositionPo, HasPositionId> {

	@Autowired
	private DealingPositionManager dealingPositionManager;
	
	@PostConstruct
	public void init() {
		getNullable = true;
	}
	
	@Override
	protected Long getId(DealingPositionPo po) {
		return po.getId();
	}

	@Override
	protected Converter<Long, HasPositionId> getConverter() {
		return new Converter<Long, HasPositionId>() {

			@Override
			protected HasPositionId doForward(Long positionId) {
				return PositionId.toPositionId(positionId);
			}

			@Override
			protected Long doBackward(HasPositionId hasPositionId) {
				return hasPositionId.positionId();
			}

		};
	}

	@Override
	protected DealingPositionPo doGetByUniqueKey(HasPositionId positionId) {
		DealingRoom dealingRoom = dealingPositionManager.getRoom(positionId.coSymbolId());
		return dealingRoom.getPosition(positionId.positionId());
	}

	@Override
	protected Map<Long, DealingPositionPo> update(Map<Long, DealingPositionPo> valueMap) {
		throw new UnsupportedOperationException();
	}

}
