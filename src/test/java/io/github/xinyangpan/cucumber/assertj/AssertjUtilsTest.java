package io.github.xinyangpan.cucumber.assertj;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.failBecauseExceptionWasNotThrown;

import org.junit.ComparisonFailure;
import org.junit.Test;

import io.github.xinyangpan.cucumber.assertj.AssertjUtils;

public class AssertjUtilsTest {

	@Test
	public void testAssertEqualToIgnoringNull_null() {
		String expected = null;
		AssertjUtils.assertIgnoringNullEqualTo(assertThat("123"), expected);
	}

	@Test
	public void testAssertEqualToIgnoringNull_equal() {
		String expected = "123";
		AssertjUtils.assertIgnoringNullEqualTo(assertThat("123"), expected);
	}

	@Test
	public void testAssertEqualToIgnoringNull_not_equal() {
		String expected = "1234";
		try {
			AssertjUtils.assertIgnoringNullEqualTo(assertThat("123"), expected);
			failBecauseExceptionWasNotThrown(ComparisonFailure.class);
		} catch (ComparisonFailure e) {
			assertThat(e).isInstanceOf(ComparisonFailure.class).hasMessage("expected:<\"123[4]\"> but was:<\"123[]\">");
		}
	}

}
