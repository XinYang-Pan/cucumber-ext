package io.github.xinyangpan.cucumber.assertj;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.function.Function;

import org.apache.commons.collections4.CollectionUtils;
import org.assertj.core.api.Assert;

public class AssertjUtils {

	public static <S extends Assert<S, A>, A> Assert<S, A> assertIgnoringNullEqualTo(Assert<S, A> asserts, Object expected) {
		return assertIgnoringNull(asserts, asserts::isEqualTo, expected);
	}

	public static <S extends Assert<S, A>, A, T> Assert<S, A> assertIgnoringNull(Assert<S, A> asserts, Function<T, Assert<S, A>> assertMethod, T expected) {
		if (expected == null) {
			return asserts;
		} else {
			return assertMethod.apply(expected);
		}
	}

	@SuppressWarnings("unchecked")
	public static <T> void exactlyInAnyOrder(List<T> actuals, List<T> expects) {
		exactlyInAnyOrder(actuals, (T[]) CollectionUtils.emptyIfNull(expects).toArray());
	}

	public static <T> void exactlyInAnyOrder(List<T> actuals, T[] expects) {
		assertThat(CollectionUtils.emptyIfNull(actuals)).usingFieldByFieldElementComparator().containsExactlyInAnyOrder(expects);
	}

}