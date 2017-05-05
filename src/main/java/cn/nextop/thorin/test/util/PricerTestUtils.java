package cn.nextop.thorin.test.util;

import java.util.Iterator;
import java.util.Map;

import com.google.common.collect.Iterators;
import com.google.common.collect.Maps;

import cn.nextop.thorin.common.util.Objects;
import cn.nextop.thorin.core.pricing.domain.entity.BkQuote;
import cn.nextop.thorin.core.pricing.domain.entity.LpQuote;
import cn.nextop.thorin.core.pricing.domain.entity.impl.LpQuoteImpl;
import cn.nextop.thorin.core.pricing.domain.pipeline.response.impl.FeedLpQuoteResponse;
import cn.nextop.thorin.test.vo.id.has.compound.HasBkId;

public class PricerTestUtils {

	public static BkQuote getOnlyBkQuote(FeedLpQuoteResponse feedLpQuoteResponse, HasBkId bkId) {
		Map<Short, BkQuote> brokerIdMap = Maps.uniqueIndex(feedLpQuoteResponse.bkQuotes(), BkQuote::getBrokerId);
		BkQuote bkQuote = brokerIdMap.get(bkId.bkId());
		return bkQuote;
	}

	public static Map<Long, BkQuote> getBkQuoteMap(FeedLpQuoteResponse feedLpQuoteResponse, HasBkId brokerId) {
		Iterator<BkQuote> bkQuotes = feedLpQuoteResponse.bkQuotes();
		bkQuotes = Iterators.filter(bkQuotes, bkQuote -> bkQuote.getBrokerId() == brokerId.bkId());
		return Maps.uniqueIndex(bkQuotes, BkQuote::getId);
	}

	public static LpQuote getOnlyLpQuote(FeedLpQuoteResponse feedLpQuoteResponse) {
		return Iterators.getOnlyElement(feedLpQuoteResponse.lpQuotes(), null);
	}

	public static String lpQuoteToString(FeedLpQuoteResponse feedLpQuoteResponse) {
		LpQuoteImpl lpQuote = Objects.cast(Iterators.getOnlyElement(feedLpQuoteResponse.lpQuotes()));
		return lpQuote.toCanonicalString();
	}

}
