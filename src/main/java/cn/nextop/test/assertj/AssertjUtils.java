package cn.nextop.test.assertj;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.function.Function;

import org.assertj.core.api.Assert;

import cn.nextop.thorin.common.util.Lists;

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
		exactlyInAnyOrder(actuals, (T[]) Lists.nullToEmpty(expects).toArray());
	}

	public static <T> void exactlyInAnyOrder(List<T> actuals, T[] expects) {
		assertThat(Lists.nullToEmpty(actuals)).usingFieldByFieldElementComparator().containsExactlyInAnyOrder(expects);
	}

}
