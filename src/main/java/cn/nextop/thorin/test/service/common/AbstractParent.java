package cn.nextop.thorin.test.service.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import cn.nextop.thorin.core.common.MarketManager;
import cn.nextop.thorin.test.service.common.po.dealing.DealingBalancePoService;
import cn.nextop.thorin.test.service.common.po.dealing.DealingCounterpartyPoService;
import cn.nextop.thorin.test.service.common.po.dealing.DealingLadderPoService;
import cn.nextop.thorin.test.service.common.po.dealing.DealingPositionPoService;
import cn.nextop.thorin.test.service.common.po.holding.AccountsPoService;
import cn.nextop.thorin.test.service.common.po.holding.AllocationPoService;
import cn.nextop.thorin.test.service.common.po.holding.BookPoService;
import cn.nextop.thorin.test.service.common.po.holding.BooksPoService;
import cn.nextop.thorin.test.service.common.po.holding.HoldingSummaryPoService;
import cn.nextop.thorin.test.service.common.po.pricing.BkDeviationConfigPoService;
import cn.nextop.thorin.test.service.common.po.pricing.BkPickingConfigPoService;
import cn.nextop.thorin.test.service.common.po.pricing.BkSymbolConfigPoService;
import cn.nextop.thorin.test.service.common.po.pricing.BkTuningConfigPoService;
import cn.nextop.thorin.test.service.common.po.pricing.LpDeviationConfigPoService;
import cn.nextop.thorin.test.service.common.po.server.BkSymbolPoService;
import cn.nextop.thorin.test.service.common.po.server.CoSymbolPoService;
import cn.nextop.thorin.test.service.common.po.server.LiquidityProviderPoService;
import cn.nextop.thorin.test.service.common.po.server.LpOrderConfigPoService;
import cn.nextop.thorin.test.service.common.po.server.LpSymbolPoService;
import cn.nextop.thorin.test.service.common.po.trigger.MulCoverTriggerInstancePoService;
import cn.nextop.thorin.test.service.common.po.trigger.MulSpreadTriggerInstancePoService;
import cn.nextop.thorin.test.service.common.po.trigger.SmaShadeTriggerInstancePoService;
import cn.nextop.thorin.test.service.common.po.trigger.TvrShadeTriggerInstancePoService;
import cn.nextop.thorin.test.service.core.MessengerService;

public class AbstractParent {
	protected final Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	protected MarketManager marketManager;
	// -----------------------------
	// ----- Service 
	// -----------------------------
	@Autowired
	protected MessengerService messengerService;
	@Autowired
	protected CommonService commonService;
	@Autowired
	protected ParseService parseService;
	@Autowired
	protected ElementDataService elementDataService;
	// -----------------------------
	// ----- PO Service 
	// -----------------------------
	// Common
	@Autowired
	protected LpSymbolPoService lpSymbolPoService;
	@Autowired
	protected LpOrderConfigPoService lpOrderConfigPoService;
	@Autowired
	protected CoSymbolPoService coSymbolPoService;
	@Autowired
	protected LiquidityProviderPoService liquidityProviderPoService;
	@Autowired
	protected BkSymbolPoService bkSymbolPoService;
	// Pricing
	@Autowired
	protected BkDeviationConfigPoService bkDeviationConfigPoService;
	@Autowired
	protected BkPickingConfigPoService bkPickingConfigPoService;
	@Autowired
	protected BkSymbolConfigPoService bkSymbolConfigPoService;
	@Autowired
	protected BkTuningConfigPoService bkTuningConfigPoService;
	@Autowired
	protected LpDeviationConfigPoService lpDeviationConfigPoService;
	// Holding
	@Autowired
	protected AccountsPoService accountsPoService;
	@Autowired
	protected AllocationPoService allocationPoService;
	@Autowired
	protected BookPoService bookPoService;
	@Autowired
	protected BooksPoService booksPoService;
	@Autowired
	protected HoldingSummaryPoService holdingSummaryPoService;
	// Dealing
	@Autowired
	protected DealingCounterpartyPoService dealingCounterpartyPoService;
	@Autowired
	protected DealingPositionPoService dealingPositionPoService;
	@Autowired
	protected DealingBalancePoService dealingBalancePoService;
	@Autowired
	protected DealingLadderPoService dealingLadderPoService;
	// Trigger
	@Autowired
	protected MulCoverTriggerInstancePoService mulCoverTriggerInstancePoService;
	@Autowired
	protected MulSpreadTriggerInstancePoService mulSpreadTriggerInstancePoService;
	@Autowired
	protected SmaShadeTriggerInstancePoService smaShadeTriggerInstancePoService;
	@Autowired
	protected TvrShadeTriggerInstancePoService tvrShadeTriggerInstancePoService;
	
}
