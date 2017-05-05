package cn.nextop.thorin.test.service.common.po.holding;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.nextop.thorin.core.holding.HoldingManager;
import cn.nextop.thorin.core.holding.HoldingStrategyAdministrator;
import cn.nextop.thorin.core.holding.domain.pipeline.request.impl.ModifyStrategyRequest;
import cn.nextop.thorin.core.holding.domain.pipeline.response.HoldingResponse;
import cn.nextop.thorin.core.holding.orm.po.HoldingSummaryPo;
import cn.nextop.thorin.test.core.assertj.hard.ThorinAssertions;
import cn.nextop.thorin.test.service.common.po.AbstractPoService;

@Service
public class HoldingSummaryPoService extends AbstractPoService<Byte, HoldingSummaryPo> {

	@Autowired
	private HoldingStrategyAdministrator admin;
	@Autowired
	private HoldingManager holdingManager;
	
	public HoldingSummaryPoService() {
		this.getNullable = true;
	}
	
	@Override
	protected Byte getId(HoldingSummaryPo po) {
		return po.getId();
	}

	@Override
	protected HoldingSummaryPo doGet(Byte id) {
		return admin.getSummary(id);
	}

	@Override
	protected HoldingSummaryPo doUpdate(HoldingSummaryPo input) {
		try {
			ModifyStrategyRequest request = new ModifyStrategyRequest(input);
			HoldingResponse holdingResponse = holdingManager.invoke(request, true).get();
			ThorinAssertions.assertThat(holdingResponse).isSuccessful();
			return this.get(this.getId(input));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
