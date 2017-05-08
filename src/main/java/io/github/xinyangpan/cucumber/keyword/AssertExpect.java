package io.github.xinyangpan.cucumber.keyword;

import org.apache.commons.lang3.StringUtils;

import com.google.common.base.Preconditions;

// regex - ([A-Za-z0-9./\\s\\(\\)]*)
// assertExpect
// (expect to fail)
public class AssertExpect {
	private static String[] IGNORE_KEY_WORDS = { "ignore", "ignoring" };
	private static String[] PASS_KEY_WORDS = { "pass", "success" };
	private static String[] FAIL_KEY_WORDS = { "fail" };
	private boolean ignoreToAssert;
	private boolean expectToPass;

	public AssertExpect(String text) {
		if (StringUtils.containsAny(text, IGNORE_KEY_WORDS)) {
			ignoreToAssert = true;
		} else {
			ignoreToAssert = false;
			// 
			if (StringUtils.containsAny(text, PASS_KEY_WORDS)) {
				expectToPass = true;
			} else if (StringUtils.containsAny(text, FAIL_KEY_WORDS)) {
				expectToPass = false;
			} else {
				// default pass
				expectToPass = true;
			}
		}
	}

	public boolean ignoreToAssert() {
		return ignoreToAssert;
	}

	public boolean isToAssert() {
		return !this.ignoreToAssert;
	}

	public boolean toPass() {
		Preconditions.checkArgument(this.isToAssert());
		return expectToPass;
	}

	public boolean toFail() {
		return !this.toPass();
	}

	public void asserting(ThrowableRunnable runnable) throws Throwable {
		// run assert function
		AssertionError error = null;
		try {
			runnable.run();
		} catch (AssertionError e) {
			error = e;
		} catch (Throwable e) {
			throw e;
		}
		// ignore assert
		if (this.ignoreToAssert()) {
			return;
		}
		// expect to pass or fail
		if (this.toFail()) {
			if (error == null) {
				throw new AssertionError("Expected to fail but passed.");
			}
		} else {
			if (error != null) {
				throw error;
			}
		}
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AssertExpect [ignoreToAssert=");
		builder.append(ignoreToAssert);
		builder.append(", expectToPass=");
		builder.append(expectToPass);
		builder.append("]");
		return builder.toString();
	}

	public static interface ThrowableRunnable {

		void run() throws Throwable;

	}

}
