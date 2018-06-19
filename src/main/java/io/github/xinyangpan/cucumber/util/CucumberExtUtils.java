package io.github.xinyangpan.cucumber.util;

import java.util.Objects;

public class CucumberExtUtils {

	public static boolean contains(final Object[] array, final Object objectToFind) {
		if (array == null || array.length == 0) {
			return false;
		}
		Objects.requireNonNull(objectToFind);
		for (Object object : array) {
			if (Objects.equals(object, objectToFind)) {
				return true;
			}
		}
		return false;
	}

	public static boolean containsAny(String cs, String... searchCharSequences) {
		if (cs == null || cs.length() == 0) {
			return false;
		}
		if (searchCharSequences == null || searchCharSequences.length == 0) {
			return false;
		}
		for (String searchCharSequence : searchCharSequences) {
			if (cs.contains(searchCharSequence)) {
				return true;
			}
		}
		return false;
	}

}