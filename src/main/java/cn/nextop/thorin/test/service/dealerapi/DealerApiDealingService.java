package cn.nextop.thorin.test.service.dealerapi;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import cn.nextop.thorin.api.dealer.proto.DealingServiceProtoV1.DealingEvent;
import cn.nextop.thorin.api.dealer.proto.DealingServiceProtoV1.SubscribeDealingEventResponse;
import cn.nextop.thorin.core.dealing.DealingPositionManager;
import cn.nextop.thorin.core.dealing.domain.entity.DealingRoom;
import cn.nextop.thorin.test.vo.dlrapi.DealingData;
import support.shell.dlrapi.service.DealingService;

@Service
@Lazy
public class DealerApiDealingService extends DealerApiAbstractService {

	@Autowired
	private DealingService dealingService;
	@Autowired
	private DealingPositionManager dealingPositionManager;

	@PostConstruct
	public void init() {
		super.init();
		// 
		SubscribeDealingEventResponse subscribeDealingEvent = dealingService.subscribeDealingEvent();
		assertThat(subscribeDealingEvent.getResult()).as(subscribeDealingEvent.toString()).isEqualTo(SubscribeDealingEventResponse.Result.SUCCESS);
	}

	public DealingData next() {
		DealingEvent dealingEvent = dealingService.nextDealingEvent();
		return DealingData.from(dealingEvent);
	}

	public DealingData get() {
		Set<Byte> companyIds = commonService.getAllCompanyIds();
		List<DealingRoom> dealingRooms = Lists.newArrayList();
		for (Byte companyId : companyIds) {
			dealingRooms.addAll(dealingPositionManager.getRooms(companyId).values());
		}
		// 
		DealingData dealingData = new DealingData();
		dealingData.setDealingOrderPos(Lists.newArrayList());
		dealingData.setDealingLadderPos(Lists.newArrayList());
		dealingData.setDealingPositionPos(Lists.newArrayList());
		dealingData.setDealingCounterpartyPos(Lists.newArrayList());
		for (DealingRoom dealingRoom : dealingRooms) {
			dealingData.getDealingOrderPos().addAll(dealingRoom.getOrders().values());
			dealingData.getDealingLadderPos().addAll(dealingRoom.getLadders().values());
			dealingData.getDealingPositionPos().addAll(dealingRoom.getPositions().values());
			dealingData.getDealingCounterpartyPos().addAll(dealingRoom.getCounterparties().values());
		}
		return dealingData;
	}

}
