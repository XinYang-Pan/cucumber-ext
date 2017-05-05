package cn.nextop.thorin.test.service.pricer;

import static org.assertj.core.api.Assertions.assertThat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import cn.nextop.thorin.common.util.Objects;
import cn.nextop.thorin.common.util.Sets;
import cn.nextop.thorin.core.common.domain.CommonGenerator;
import cn.nextop.thorin.core.common.domain.glossary.decimal.Price;
import cn.nextop.thorin.core.common.glossary.PricingDomain;
import cn.nextop.thorin.core.common.glossary.PricingPickingPolicy;
import cn.nextop.thorin.core.pricing.PricingManager;
import cn.nextop.thorin.core.pricing.domain.pipeline.request.impl.ResumeQuotationRequest;
import cn.nextop.thorin.core.pricing.domain.pipeline.response.impl.ResumeQuotationResponse;
import cn.nextop.thorin.core.pricing.orm.po.BkPickingConfigPo;
import cn.nextop.thorin.core.pricing.orm.po.BkSymbolConfigPo;
import cn.nextop.thorin.core.pricing.orm.po.LpDeviationConfigPo;
import cn.nextop.thorin.test.core.assertj.hard.ThorinAssertions;
import cn.nextop.thorin.test.service.common.AbstractService;
import cn.nextop.thorin.test.service.core.LpQuoteService;
import cn.nextop.thorin.test.vo.id.has.compound.HasLpBkSymbolId;
import cn.nextop.thorin.test.vo.id.has.derivative.HasLpDeviationConfigId;
import cn.nextop.thorin.test.vo.id.obj.derivative.BkPickingConfigId;

@Service
@Lazy
public class PricerService extends AbstractService {

	@Autowired
	private PricingManager pricingManager;
	@Autowired
	private LpQuoteService lpQuoteService;

	public void changeToAlpAndSetMaster(HasLpBkSymbolId lpBkSymbolId, Price bid, Price ask) throws Throwable {
		bkSymbolConfigPoService.changePickingStrategy(lpBkSymbolId, PricingPickingPolicy.ALP);
		this.setToMaster(lpBkSymbolId, bid, ask);}
	
	private void setToMaster(HasLpBkSymbolId lpBkSymbolId, Price bid, Price ask) throws Throwable {
		BkSymbolConfigPo bkSymbolConfigPo = bkSymbolConfigPoService.getByUniqueKey(lpBkSymbolId);
		// Must in ALP PricingPickingPolicy
		assertThat(bkSymbolConfigPo.getPickingPolicy()).isEqualTo(PricingPickingPolicy.ALP);
		// Change to Master
		BkPickingConfigId bkPickingConfigId = BkPickingConfigId.toBkPickingConfigId(lpBkSymbolId, PricingPickingPolicy.ALP);
		bkPickingConfigPoService.updateMasterTo(bkPickingConfigId);
		// 
		lpQuoteService.sendLpQuote(lpBkSymbolId, bid, ask, 5);
		// Assert Master
		BkPickingConfigPo bkPickingConfigPo = bkPickingConfigPoService.getByUniqueKey(bkPickingConfigId);
		assertThat(bkPickingConfigPo.isMaster()).isTrue();
	}

	public void sendManualResumeMessage(HasLpDeviationConfigId lpDevConfigId) throws Throwable {
		// Send Request
		final int lpSymbolId = lpDevConfigId.lpSymbolId();
		final short lpId = CommonGenerator.getLpIdByLpSymbolId(lpSymbolId);
		final short symbolId = CommonGenerator.getSymbolIdByLpSymbolId(lpSymbolId);
		final byte companyId = CommonGenerator.getCompanyIdByLpSymbolId(lpSymbolId);
		ResumeQuotationRequest request = new ResumeQuotationRequest(companyId, symbolId);
		request.setDomain(PricingDomain.LP); request.setCounterparties(Sets.toSet(lpId));
		ResumeQuotationResponse response = Objects.cast(pricingManager.invoke(request, true).get());
		ThorinAssertions.assertThat(response).isSuccessful();
		
		// Assert System Resume
		LpDeviationConfigPo lpDeviationConfigPo = lpDeviationConfigPoService.getByUniqueKey(lpDevConfigId);
		assertThat(lpDeviationConfigPo.getStatus().isBidSuspended()).isFalse();
		assertThat(lpDeviationConfigPo.getStatus().isAskSuspended()).isFalse();
	}

}
