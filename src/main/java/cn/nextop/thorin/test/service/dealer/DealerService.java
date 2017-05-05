package cn.nextop.thorin.test.service.dealer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import cn.nextop.thorin.core.dealing.DealingPositionAdministrator;
import cn.nextop.thorin.core.dealing.domain.pipeline.request.impl.InputSwapRequest;
import cn.nextop.thorin.core.dealing.domain.pipeline.response.impl.InputSwapResponse;
import cn.nextop.thorin.core.dealing.orm.po.DealingCashflowExPo;
import cn.nextop.thorin.test.core.assertj.hard.ThorinAssertions;
import cn.nextop.thorin.test.service.common.AbstractService;
import cn.nextop.thorin.test.vo.dealer.LpSwapExecution;

@Service
@Lazy
public class DealerService extends AbstractService {
	@Autowired
	private DealingPositionAdministrator dealingPositionAdministrator;

	public void swapCashflowInput(DealingCashflowExPo dealingCashflowExPo) {
		dealingPositionAdministrator.insertCashflows(dealingCashflowExPo.toCashflows());
	}

	public void swapExectionInput(LpSwapExecution lpSwapExecution) {
		InputSwapRequest inputSwapRequest = lpSwapExecution.toInputSwapRequest(marketManager.getTradingDate());
		InputSwapResponse inputSwapResponse = dealingPositionAdministrator.insertExecutions(inputSwapRequest);
		ThorinAssertions.assertThat(inputSwapResponse).isSuccessful();
	}

	public void swapExectionInput(List<LpSwapExecution> lpSwapExecutions) {
		for (LpSwapExecution lpSwapExecution : lpSwapExecutions) {
			this.swapExectionInput(lpSwapExecution);
		}
	}

}
