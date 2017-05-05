package cn.nextop.thorin.test.vo.dlrapi;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Lists.transform;

import java.util.List;

import cn.nextop.thorin.api.dealer.proto.DealingServiceProtoV1.DealingEvent;
import cn.nextop.thorin.api.dealer.service.dealing.v1.model.DealingCounterpartyV1Factory;
import cn.nextop.thorin.api.dealer.service.dealing.v1.model.DealingLadderV1Factory;
import cn.nextop.thorin.api.dealer.service.dealing.v1.model.DealingOrderV1Factory;
import cn.nextop.thorin.api.dealer.service.dealing.v1.model.DealingPositionV1Factory;
import cn.nextop.thorin.core.dealing.orm.po.DealingCounterpartyPo;
import cn.nextop.thorin.core.dealing.orm.po.DealingLadderPo;
import cn.nextop.thorin.core.dealing.orm.po.DealingOrderPo;
import cn.nextop.thorin.core.dealing.orm.po.DealingPositionPo;

public class DealingData {
	private List<DealingOrderPo> dealingOrderPos;
	private List<DealingLadderPo> dealingLadderPos;
	private List<DealingPositionPo> dealingPositionPos;
	private List<DealingCounterpartyPo> dealingCounterpartyPos;

	public static DealingData from(DealingEvent dealingEvent) {
		DealingData dealingData = new DealingData();
		// 
		dealingData.setDealingOrderPos(newArrayList(transform(dealingEvent.getOrdersList(), DealingOrderV1Factory::toOrder)));
		dealingData.setDealingLadderPos(newArrayList(transform(dealingEvent.getLaddersList(), DealingLadderV1Factory::toLadder)));
		dealingData.setDealingPositionPos(newArrayList(transform(dealingEvent.getPositionsList(), DealingPositionV1Factory::toPosition)));
		dealingData.setDealingCounterpartyPos(newArrayList(transform(dealingEvent.getCounterpartiesList(), DealingCounterpartyV1Factory::toCounterparty)));
		return dealingData;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DealingData [dealingOrderPos=");
		builder.append(dealingOrderPos);
		builder.append(", dealingLadderPos=");
		builder.append(dealingLadderPos);
		builder.append(", dealingPositionPos=");
		builder.append(dealingPositionPos);
		builder.append(", dealingCounterpartyPos=");
		builder.append(dealingCounterpartyPos);
		builder.append("]");
		return builder.toString();
	}

	public List<DealingOrderPo> getDealingOrderPos() {
		return dealingOrderPos;
	}

	public void setDealingOrderPos(List<DealingOrderPo> dealingOrderPos) {
		this.dealingOrderPos = dealingOrderPos;
	}

	public List<DealingLadderPo> getDealingLadderPos() {
		return dealingLadderPos;
	}

	public void setDealingLadderPos(List<DealingLadderPo> dealingLadderPos) {
		this.dealingLadderPos = dealingLadderPos;
	}

	public List<DealingPositionPo> getDealingPositionPos() {
		return dealingPositionPos;
	}

	public void setDealingPositionPos(List<DealingPositionPo> dealingPositionPos) {
		this.dealingPositionPos = dealingPositionPos;
	}

	public List<DealingCounterpartyPo> getDealingCounterpartyPos() {
		return dealingCounterpartyPos;
	}

	public void setDealingCounterpartyPos(List<DealingCounterpartyPo> dealingCounterpartyPos) {
		this.dealingCounterpartyPos = dealingCounterpartyPos;
	}

}
