package org.blueo.cucumber.element;

import java.util.Map;
import java.util.Objects;

import org.blueo.cucumber.parse.ParseUtils;
import org.javatuples.Pair;

class Entry {
	private final String key;
	private final String value;

	public Entry(String key, String value) {
		this.key = key;
		this.value = value;
	}

	public Entry(Map.Entry<String, String> mapEntry) {
		this.key = mapEntry.getKey();
		String value = mapEntry.getValue();
		if ("<null>".equals(value)) {
			this.value = null;
		} else {
			this.value = value;
		}
	}

	public void putValueTo(Object target) {
		ParseUtils.populateField(target, key, value);
	}

	public <T> T getValue(Class<T> type) {
		return ParseUtils.parseString(value, type);
	}

	/**
	 * To Evaluate if self value to Target Obejct's key field
	 * 
	 * For Comparable -> using compare
	 * For None Comparable -> using equals
	 * 
	 * @param target
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public boolean matchesFieldByKey(Object target) {
		// 
		Pair<Object, Class<?>> fieldValueAndType = ParseUtils.getFieldValueAndType(target, key);
		Class<?> type = fieldValueAndType.getValue1();
		Object actualValue = fieldValueAndType.getValue0();
		Object expectedValue = this.getValue(type);
		// 
		if (actualValue instanceof Comparable<?> && expectedValue instanceof Comparable<?>) {
			if (((Comparable<Object>) actualValue).compareTo(expectedValue) != 0) {
				return false;
			}
		} else {
			if (!Objects.equals(actualValue, expectedValue)) {
				return false;
			}
		}
		return true;
	}

	// -----------------------------
	// ----- Get Set ToString HashCode Equals
	// -----------------------------
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Entry [key=");
		builder.append(key);
		builder.append(", value=");
		builder.append(value);
		builder.append("]");
		return builder.toString();
	}

	public String getKey() {
		return key;
	}

	public String getValue() {
		return value;
	}

}
