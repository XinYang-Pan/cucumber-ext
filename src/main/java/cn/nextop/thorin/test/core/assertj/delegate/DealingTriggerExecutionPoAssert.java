package cn.nextop.thorin.test.core.assertj.delegate;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import cn.nextop.test.cucumber.Element;
import cn.nextop.thorin.core.trigger.orm.po.DealingTriggerExecutionPo;
import cn.nextop.thorin.test.core.assertj.hard.ThorinAssertions;

public class DealingTriggerExecutionPoAssert implements AssertByElement<DealingTriggerExecutionPoAssert> {
	private DealingTriggerExecutionPo actual;

	public DealingTriggerExecutionPoAssert(DealingTriggerExecutionPo actual) {
		super();
		this.actual = actual;
	}

	@Override
	public DealingTriggerExecutionPoAssert assertBy(Element element) {
		// Assert
		ThorinAssertions.assertThat(actual).isMatchTo(element);
		assertExecuteQuote(element);
		assertExecuteComment(element);
		return this;
	}

	private void assertExecuteComment(Element element) {
		Optional<String> executeCommentHas = element.getValueOpt("_executeComment_has", String.class);
		if (executeCommentHas.isPresent()) {
			assertThat(actual.getExecuteComment()).contains(executeCommentHas.get());
		}
	}

	private void assertExecuteQuote(Element element) {
		Optional<Boolean> executeQuote = element.getValueOpt("_executeQuote_is_zero", boolean.class);
		if (executeQuote.isPresent()) {
			if (executeQuote.get()) {
				ThorinAssertions.assertThat(actual.getExecuteQuote()).isEqualTo(0L);
			} else {
				ThorinAssertions.assertThat(actual.getExecuteQuote()).isNotEqualTo(0L);
			}
		}
	}

}
