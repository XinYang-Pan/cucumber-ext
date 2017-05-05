package cn.nextop.test.assertj.hard;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.collections4.comparators.ComparableComparator;

import cn.nextop.test.assertj.asserts.AssertjAssert;
import cn.nextop.test.assertj.asserts.AssertjListAssert;

public class AssertjAssertions {

	@SuppressWarnings("unchecked")
	public static <A> AssertjAssert<A> assertThat(A actual) {
		if (actual instanceof BigDecimal) {
			return (AssertjAssert<A>) assertThat((BigDecimal)actual);
		} else {
			return new AssertjAssert<>(actual).usingComparatorForType(ComparableComparator.comparableComparator(), BigDecimal.class);
		}
	}

	public static <A extends BigDecimal> AssertjAssert<A> assertThat(A actual) {
		return new AssertjAssert<>(actual).usingComparator(ComparableComparator.comparableComparator());
	}

	public static <E> AssertjListAssert<E> assertThat(List<? extends E> actual) {
		return new AssertjListAssert<>(actual);
	}

}
