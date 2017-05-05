package cn.nextop.thorin.test.core.assertj.asserts.bo;

import static cn.nextop.test.assertj.AssertjUtils.assertIgnoringNullEqualTo;

import org.assertj.core.api.SoftAssertions;

import cn.nextop.thorin.core.common.domain.glossary.decimal.Price;
import cn.nextop.thorin.core.pricing.domain.entity.BkQuote;
import cn.nextop.thorin.test.vo.pricer.BkQuoteStateVo;

public class BkQuoteAssert extends QuoteAssert<BkQuoteAssert, BkQuote> {

	public BkQuoteAssert(BkQuote actual) {
		super(actual, BkQuoteAssert.class);
	}

	public BkQuoteAssert rawPriceIs(Price bid, Price ask) {
		SoftAssertions soft = new SoftAssertions();
		soft.assertThat(actual).isNotNull();
		soft.assertThat(actual.getBids().get(0).getRawPrice()).as("Bid").isEqualTo(bid);
		soft.assertThat(actual.getAsks().get(0).getRawPrice()).as("Ask").isEqualTo(ask);
		soft.assertAll();
		return myself;
	}

	public BkQuoteAssert isStates(BkQuoteStateVo bkQuoteStateVo) {
		this.isNotNull();
		// 
		SoftAssertions soft = new SoftAssertions();
		assertIgnoringNullEqualTo(soft.assertThat(actual.getAiSpreadId()).as("SpreadId"), bkQuoteStateVo.getSpreadId());
		assertIgnoringNullEqualTo(soft.assertThat(actual.getAiShadeId()).as("ShadeId"), bkQuoteStateVo.getShadeId());
		assertIgnoringNullEqualTo(soft.assertThat(actual.getTuningStatus().isAiSpread()).as("AiSpread"), bkQuoteStateVo.getAiSpread());
		assertIgnoringNullEqualTo(soft.assertThat(actual.getTuningStatus().isMaSpread()).as("MaSpread"), bkQuoteStateVo.getMaSpread());
		assertIgnoringNullEqualTo(soft.assertThat(actual.getTuningStatus().isAiShade()).as("AiShade"), bkQuoteStateVo.getAiShade());
		assertIgnoringNullEqualTo(soft.assertThat(actual.getTuningStatus().isMaShade()).as("MaShade"), bkQuoteStateVo.getMaShade());
		assertIgnoringNullEqualTo(soft.assertThat(actual.getAiShade1()).as("AiShadeVal"), bkQuoteStateVo.getAiShadeVal());
		assertIgnoringNullEqualTo(soft.assertThat(actual.getMaShade()).as("MaShadeVal"), bkQuoteStateVo.getMaShadeVal());
		assertIgnoringNullEqualTo(soft.assertThat(actual.getBids().get(0).getMaAdjustment()).as("BidMaAdjustment"), bkQuoteStateVo.getBidMaAdjustment());
		assertIgnoringNullEqualTo(soft.assertThat(actual.getAsks().get(0).getMaAdjustment()).as("AskMaAdjustment"), bkQuoteStateVo.getAskMaAdjustment());
		assertIgnoringNullEqualTo(soft.assertThat(actual.getBids().get(0).getPrice()).as("Bid"), bkQuoteStateVo.getBid());
		assertIgnoringNullEqualTo(soft.assertThat(actual.getAsks().get(0).getPrice()).as("Ask"), bkQuoteStateVo.getAsk());
		assertIgnoringNullEqualTo(soft.assertThat(actual.getMaSpread()).as("SpreadPolicy"), bkQuoteStateVo.getSpreadPolicy());
		assertIgnoringNullEqualTo(soft.assertThat(actual.getAiSpread()).as("AiSpreadVal"), bkQuoteStateVo.getAiSpreadVal());
		soft.assertAll();
		return this;
	}

}
