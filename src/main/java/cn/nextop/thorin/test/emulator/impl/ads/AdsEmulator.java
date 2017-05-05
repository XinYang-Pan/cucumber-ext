package cn.nextop.thorin.test.emulator.impl.ads;

import static org.apache.commons.lang3.ObjectUtils.defaultIfNull;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.apache.commons.io.IOUtils;

import cn.nextop.thorin.core.common.domain.CommonGenerator;
import cn.nextop.thorin.core.common.glossary.BidAsk;
import cn.nextop.thorin.test.emulator.AbstractEmulator;
import cn.nextop.thorin.test.emulator.bo.GwBand;
import cn.nextop.thorin.test.emulator.bo.GwQuoteFull;
import cn.nextop.thorin.test.util.TestUtils;
import quickfix.Group;
import quickfix.Message;
import quickfix.field.Currency;
import quickfix.field.ExpireDate;
import quickfix.field.MDEntryPx;
import quickfix.field.MDEntrySize;
import quickfix.field.MDEntryType;
import quickfix.field.QuoteCondition;
import quickfix.field.QuoteEntryID;
import quickfix.field.Symbol;
import quickfix.fix44.MarketDataSnapshotFullRefresh;

public class AdsEmulator extends AbstractEmulator<GwQuoteFull> {

	public AdsEmulator() {
		super("ADS", AdsEmulator.class.getResourceAsStream("ads-server-session.cfg"));
	}

	@Override
	protected void doStart() throws Exception {
		super.doStart();
		this.messageTemplate = IOUtils.toString(this.getClass().getResourceAsStream("ads_fix_FULL_1.log"), "UTF8");
	}

	@Override
	protected Message buildQuoteMessage(GwQuoteFull gwQuoteFull) throws Exception {
		// 
		String currencyPair = gwQuoteFull.getSymbolName();
		// 
		MarketDataSnapshotFullRefresh message = new MarketDataSnapshotFullRefresh();
		message.setField(request.getMDReqID());
		message.setField(new Symbol(currencyPair));
		// 
		for (GwBand gwBand : gwQuoteFull.getGwBands()) {
			Group group = new MarketDataSnapshotFullRefresh.NoMDEntries();
			BidAsk side = gwBand.getBidAsk();
			switch (side) {
			case ASK:
				group.setField(new MDEntryType(MDEntryType.OFFER));
				break;
			case BID:
				group.setField(new MDEntryType(MDEntryType.BID));
				break;
			default:
				TestUtils.illegal(side);
			}
			group.setField(new MDEntryPx(gwBand.getPrice()));
			group.setField(new Currency(currencyPair.substring(0, 3)));
			group.setField(new ExpireDate(LocalDate.now().plusDays(1).format(DateTimeFormatter.BASIC_ISO_DATE)));
			// default if null
			group.setField(new MDEntrySize(defaultIfNull(gwBand.getVolumn(), 10000)));
			group.setField(new QuoteCondition(defaultIfNull(gwBand.getQuoteCondition(), QuoteCondition.OPEN_ACTIVE)));
			group.setField(new QuoteEntryID(defaultIfNull(gwBand.getQuoteEntryId(), String.valueOf(CommonGenerator.getDefault().next()))));
			// 
			message.addGroup(group);
		}
		return message;
	}

}