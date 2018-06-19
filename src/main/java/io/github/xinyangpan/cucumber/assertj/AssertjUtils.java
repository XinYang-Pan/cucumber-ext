package io.github.xinyangpan.cucumber.assertj;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;

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
		expects = expects != null ? expects : Collections.emptyList();
		exactlyInAnyOrder(actuals, (T[]) expects.toArray());
	}

	public static <T> void exactlyInAnyOrder(List<T> actuals, T[] expects) {
		actuals = actuals != null ? actuals : Collections.emptyList();
		assertThat(actuals).usingFieldByFieldElementComparator().containsExactlyInAnyOrder(expects);
	}

}
