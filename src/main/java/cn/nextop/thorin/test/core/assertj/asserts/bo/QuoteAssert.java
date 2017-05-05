package cn.nextop.thorin.test.core.assertj.asserts.bo;

import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.api.SoftAssertions;

import cn.nextop.test.assertj.asserts.AssertjAbstractAssert;
import cn.nextop.thorin.core.common.domain.glossary.decimal.Price;
import cn.nextop.thorin.core.pricing.domain.entity.Quote;
import cn.nextop.thorin.test.vo.pricer.DeviationStatusVo;

public abstract class QuoteAssert<S extends QuoteAssert<S, A>, A extends Quote> extends AssertjAbstractAssert<S, A> {

	public QuoteAssert(A actual, Class<?> selfType) {
		super(actual, selfType);
	}

	public S isPrice(Price bid, Price ask) {
		this.isNotNull();
		// 
		SoftAssertions soft = new SoftAssertions();
		soft.assertThat(actual.getBids().get(0).getPrice()).as("Bid").isEqualTo(bid);
		soft.assertThat(actual.getAsks().get(0).getPrice()).as("Ask").isEqualTo(ask);
		soft.assertAll();
		return myself;
	}

	public S isBand0(Price bid, Price ask) {
		this.isPrice(bid, ask);
		return myself;
	}

	public S isDeviationStatus(DeviationStatusVo deviationStatusVo) {
		assertThat(deviationStatusVo).isNotNull();
		// 
		Boolean quote = deviationStatusVo.getQuote();
		Boolean quoteBid = deviationStatusVo.getQuoteBid();
		Boolean quoteAsk = deviationStatusVo.getQuoteAsk();
		this.isDeviationStatus(quote, quoteBid, quoteAsk);
		return myself;
	}

	public S isDeviationStatus(Boolean quoteIsSuspended, Boolean quoteBidIsSuspended, Boolean quoteAskIsSuspended) {
		this.isNotNull();
		// 
		SoftAssertions soft = new SoftAssertions();
		if (quoteIsSuspended != null) {
			soft.assertThat(actual.getStatus().isSuspended()).as("Quote Dev").isEqualTo(quoteIsSuspended);
		}
		if (quoteBidIsSuspended != null) {
			soft.assertThat(actual.getBids().get(0).getStatus().isSuspended()).as("Bid Dev").isEqualTo(quoteBidIsSuspended);
		}
		if (quoteAskIsSuspended != null) {
			soft.assertThat(actual.getAsks().get(0).getStatus().isSuspended()).as("Ask Dev").isEqualTo(quoteAskIsSuspended);
		}
		return myself;
	}

	public S notBugQuote() {
		return isDeviationStatus(false, false, false);
	}

}
