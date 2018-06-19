package io.github.xinyangpan.cucumber.assertj.hard;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

import io.github.xinyangpan.cucumber.assertj.asserts.AssertjAssert;
import io.github.xinyangpan.cucumber.assertj.asserts.AssertjListAssert;

public class AssertjAssertions {

	@SuppressWarnings("unchecked")
	public static <A> AssertjAssert<A> assertThat(A actual) {
		if (actual instanceof BigDecimal) {
			return (AssertjAssert<A>) assertThat((BigDecimal)actual);
		} else {
			return new AssertjAssert<>(actual).usingComparatorForType(Comparator.naturalOrder(), BigDecimal.class);
		}
	}

	public static <A extends BigDecimal> AssertjAssert<A> assertThat(A actual) {
		return new AssertjAssert<>(actual).usingComparator(Comparator.naturalOrder());
	}

	public static <E> AssertjListAssert<E> assertThat(List<? extends E> actual) {
		return new AssertjListAssert<>(actual);
	}

}
