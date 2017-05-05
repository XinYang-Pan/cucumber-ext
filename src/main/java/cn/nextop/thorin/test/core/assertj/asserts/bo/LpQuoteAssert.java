package cn.nextop.thorin.test.core.assertj.asserts.bo;

import static org.assertj.core.api.Assertions.assertThat;

import org.apache.commons.collections.CollectionUtils;

import cn.nextop.thorin.core.pricing.domain.entity.LpQuote;

public class LpQuoteAssert extends QuoteAssert<LpQuoteAssert, LpQuote> {

	public LpQuoteAssert(LpQuote actual) {
		super(actual, LpQuoteAssert.class);
	}

	public LpQuoteAssert isExpired() {
		this.isNotNull();

		Long expiredTime = this.getExpiredTime();
		if (expiredTime != null) {
			assertThat(expiredTime).isLessThan(System.currentTimeMillis());
		}
		
		return myself;
	}

	public LpQuoteAssert isNotExpired() {
		this.isNotNull();
		Long expiredTime = this.getExpiredTime();
		if (expiredTime == null || expiredTime == 0L) {
			return myself;
		} else {
			assertThat(expiredTime).isGreaterThan(System.currentTimeMillis());
		}
		return myself;
	}

	private Long getExpiredTime() {
		if (CollectionUtils.isNotEmpty(actual.getBids())) {
			return actual.getBids().get(0).getExpireTime();
		} else if (CollectionUtils.isNotEmpty(actual.getAsks())) {
			return actual.getAsks().get(0).getExpireTime();
		} else {
			return null;
		}
	}
}
