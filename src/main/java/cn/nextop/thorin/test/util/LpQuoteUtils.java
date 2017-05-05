package cn.nextop.thorin.test.util;

import java.util.List;

import org.apache.commons.lang3.ObjectUtils;

import com.google.common.collect.Lists;

import cn.nextop.thorin.common.util.datetime.TradingDate;
import cn.nextop.thorin.core.common.domain.glossary.decimal.Point;
import cn.nextop.thorin.core.common.domain.glossary.decimal.Price;
import cn.nextop.thorin.core.common.glossary.BidAsk;
import cn.nextop.thorin.core.common.glossary.PricingBandStatus;
import cn.nextop.thorin.core.common.glossary.PricingBandType;
import cn.nextop.thorin.core.common.glossary.PricingQuoteStatus;
import cn.nextop.thorin.core.common.glossary.PricingQuoteType;
import cn.nextop.thorin.core.common.orm.po.LpSymbolPo;
import cn.nextop.thorin.core.pricing.domain.Pricers;
import cn.nextop.thorin.core.pricing.domain.PricingGenerator;
import cn.nextop.thorin.core.pricing.domain.entity.LpBand;
import cn.nextop.thorin.core.pricing.domain.entity.LpQuote;
import cn.nextop.thorin.core.pricing.domain.entity.impl.LpBandImpl;
import cn.nextop.thorin.core.pricing.domain.entity.impl.LpQuoteImpl;

public class LpQuoteUtils {

	public static LpQuoteImpl buildLpQuoteImpl(LpSymbolPo lpSymbolPo, TradingDate tradingDate) {
		LpQuoteImpl lpQuoteImpl = new LpQuoteImpl();
		lpQuoteImpl.setId(PricingGenerator.getDefault().next());
		//
		lpQuoteImpl.setLpSymbolId(lpSymbolPo.getId());
		lpQuoteImpl.setDate(tradingDate);
		lpQuoteImpl.setStatus(PricingQuoteStatus.ACTIVE);
		lpQuoteImpl.setVersion(1);
		lpQuoteImpl.setType(PricingQuoteType.SNAPSHOT);
		lpQuoteImpl.setScale(lpSymbolPo.getScale());
		lpQuoteImpl.setAsks(Lists.newArrayList());
		lpQuoteImpl.setBids(Lists.newArrayList());
		return lpQuoteImpl;
	}

	public static LpBandImpl buildLpBandImpl(LpQuote lpQuote, LpSymbolPo lpSymbolPo, BidAsk side, PricingBandType type, Price price, Integer volume, PricingBandStatus status, int messageId) {
		LpBandImpl lpBandImpl = new LpBandImpl();
		lpBandImpl.setBandId(PricingGenerator.getDefault().next());
		lpBandImpl.setMessageId(messageId);
		lpBandImpl.setQuote(lpQuote);
		// 
		Pricers.execute(side, () -> lpQuote.getBids().add(lpBandImpl), () -> lpQuote.getAsks().add(lpBandImpl));
		int ladder = Pricers.execute(side, () -> lpQuote.getBids().size() - 1, () -> lpQuote.getAsks().size() - 1);
		//
		lpBandImpl.setQuoteId(String.format("Q-%s-%s-%s", side, ladder, lpQuote.getId()));
//		lpBandImpl.setEntryId(String.format("E-%s-%s-%s", side, ladder, lpQuote.getId()));
		long quoteExpiration = lpSymbolPo.getQuoteExpiration();
		if (quoteExpiration == 0L) {
			lpBandImpl.setExpireTime(0L);
		} else {
			lpBandImpl.setExpireTime(System.currentTimeMillis() + quoteExpiration);
		}
		lpBandImpl.setVolume(ObjectUtils.defaultIfNull(volume, 10000));
		lpBandImpl.setType(ObjectUtils.defaultIfNull(type, PricingBandType.NEW));
		lpBandImpl.setStatus(status);
		if (!status.isActive()) {
			lpQuote.getStatus().setActive(false);
		}
		if (status.isSuspended()) {
			lpQuote.getStatus().setSuspended(true);
		}
		if (status.isDuplicate()) {
			lpQuote.getStatus().setDuplicate(true);
		}
		if (status.isRecovered()) {
			lpQuote.getStatus().setRecovered(true);
		}
		//
		lpBandImpl.setBidAsk(side);
		lpBandImpl.setLadder((byte) ladder);
		lpBandImpl.setWeight(lpBandImpl.getBidAsk() == BidAsk.BID ? new Point(lpSymbolPo.getWeight().negate()) :  new Point(lpSymbolPo.getWeight()));
		lpBandImpl.setPrice(price.add(lpBandImpl.getWeight(), lpSymbolPo.getScale()));
		return lpBandImpl;
	}

	public static LpQuoteImpl buildLpQuoteImpl(LpSymbolPo lpSymbol, TradingDate tradingDate, Price bid, Price ask, boolean tradable) {
		LpQuoteImpl lpQuoteImpl = new LpQuoteImpl();
		lpQuoteImpl.setId(PricingGenerator.getDefault().next());
		//
		lpQuoteImpl.setLpSymbolId(lpSymbol.getId());
		lpQuoteImpl.setDate(tradingDate);
		if (tradable) {
			lpQuoteImpl.setStatus(PricingQuoteStatus.ACTIVE);
		} else {
			lpQuoteImpl.setStatus(new PricingQuoteStatus((byte) 0));
		}
		lpQuoteImpl.setVersion(1);
		lpQuoteImpl.setType(PricingQuoteType.SNAPSHOT);
		lpQuoteImpl.setScale(lpSymbol.getScale());
		// BID
		List<LpBand> bids = Lists.newArrayList();
		lpQuoteImpl.setBids(bids);
		if (bid != null) {
			for (int i = 0; i < 5; i++) {
				bids.add(LpQuoteUtils.buildLpBandImpl(lpQuoteImpl, lpSymbol, BidAsk.BID, i, bid.add(-i, lpSymbol.getScale()), tradable));
			}
		}
		// ASK
		List<LpBand> asks = Lists.newArrayList();
		lpQuoteImpl.setAsks(asks);
		if (ask != null) {
			for (int i = 0; i < 5; i++) {
				asks.add(LpQuoteUtils.buildLpBandImpl(lpQuoteImpl, lpSymbol, BidAsk.ASK, i, ask.add(i, lpSymbol.getScale()), tradable));
			}
		}
		return lpQuoteImpl;
	}

	public static LpBandImpl buildLpBandImpl(LpQuote lpQuote, LpSymbolPo lpSymbolPo, BidAsk side, int ladder, Price price, boolean tradable) {
		LpBandImpl lpBandImpl = new LpBandImpl();
		lpBandImpl.setQuote(lpQuote);
		//
		lpBandImpl.setQuoteId("SourceId");
		long quoteExpiration = lpSymbolPo.getQuoteExpiration();
		if (quoteExpiration == 0L) {
			lpBandImpl.setExpireTime(0L);
		} else {
			lpBandImpl.setExpireTime(System.currentTimeMillis() + quoteExpiration);
		}
		lpBandImpl.setReserved((short) 0);
		lpBandImpl.setVolume(1000);
		lpBandImpl.setType(PricingBandType.NEW);
		if (tradable) {
			lpBandImpl.setStatus(PricingBandStatus.ACTIVE);
		} else {
			lpBandImpl.setStatus(new PricingBandStatus((byte) 0));
		}
		//
		lpBandImpl.setBidAsk(side);
		lpBandImpl.setLadder((byte) ladder);
		lpBandImpl.setWeight(lpBandImpl.getBidAsk() == BidAsk.BID ? new Point(lpSymbolPo.getWeight().negate()) :  new Point(lpSymbolPo.getWeight()));
		lpBandImpl.setPrice(price.add(lpBandImpl.getWeight(), lpSymbolPo.getScale()));
		return lpBandImpl;
	}

	public static String lpQuoteToString(LpQuote lpQuote) {
		return ((LpQuoteImpl) lpQuote).toCanonicalString();
	}

}
