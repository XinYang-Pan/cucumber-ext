package org.blueo.cucumber.element;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.blueo.cucumber.keyword.YesOrNo;
import org.blueo.cucumber.parse.ParseUtils;
import org.springframework.util.Assert;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

public class Element {
	public static final String IGNORE_ROW = "_ignoreRow";
	public static final String EXPECT_FAIL = "_expectFail";
	// 
	protected final Map<String, String> keyValueMap;

	public Element(Map<String, String> keyValueMap) {
		this.keyValueMap = Maps.newHashMap(keyValueMap);
	}

	public <T> Optional<T> getValueOpt(String key, Class<T> type) {
		return Optional.ofNullable(ParseUtils.parseString(keyValueMap.get(key), type));
	}

	public <T> T getValue(String key, Class<T> type) {
		String value = keyValueMap.get(key);
		Assert.notNull(value, String.format("Not value found for key[%s]", key));
		return ParseUtils.parseString(value, type);
	}

	public <T> T getValue(String key, T defaultValue) {
		return ParseUtils.parseString(keyValueMap.get(key), defaultValue);
	}

	public void putValuesTo(Object target) {
		for (Entry e : this.getEligibleEntries()) {
			e.putValueTo(target);
		}
	}

	public void putValuesTo(Map<String, String> target) {
		for (Entry e : this.getEligibleEntries()) {
			target.put(e.getKey(), e.getValue());
		}
	}

	// -----------------------------
	// ----- built-in
	// -----------------------------

	public boolean isIgnoreRow() {
		YesOrNo yesOrNo = this.getValue(IGNORE_ROW, YesOrNo.NO);
		return yesOrNo.isYes();
	}

	public boolean isNotIgnoreRow() {
		return !this.isIgnoreRow();
	}

	public Map<String, String> dataMap() {
		return keyValueMap;
	}

	/**
	 * To Evaluate if self values matches Target Obejct's fields' values according to keys
	 * 
	 * Field Match Logic
	 * 
	 * For Comparable -> using compare
	 * For None Comparable -> using equals
	
	 * @param target
	 * @return
	 */
	public boolean matches(Object target) {
		// 
		for (Entry e : this.getEligibleEntries()) {
			if (!e.matchesFieldByKey(target)) {
				return false;
			}
		}
		return true;
	}

	public void convert(String oldKey, String newKey, Function<String, ?> valueConverter) {
		if (!keyValueMap.containsKey(oldKey)) {
			return;
		}
		String oldValue = keyValueMap.remove(oldKey);
		Object newValue = valueConverter.apply(oldValue);
		keyValueMap.put(newKey, String.valueOf(newValue));
	}

	public Set<String> getKeySet() {
		return Sets.newHashSet(keyValueMap.keySet());
	}

	public boolean isExpectFail() {
		YesOrNo yesOrNo = this.getValue(EXPECT_FAIL, YesOrNo.NO);
		return yesOrNo.isYes();
	}

	/**
	 * Get entries, ignoring followings
	 * <li> key start with "_"
	 * <li> value is empty
	 * <br>
	 * 
	 * @return
	 */
	private List<Entry> getEligibleEntries() {
		// @formatter:off
		return keyValueMap.entrySet().stream()
			.filter(e -> !e.getKey().startsWith("_"))
			.filter(e -> StringUtils.isNotEmpty(e.getValue()))
			.map(Entry::new)
			.collect(Collectors.toList());
		// @formatter:on
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(this.getClass().getSimpleName());
		builder.append(" [keyValueMap=");
		builder.append(keyValueMap);
		builder.append("]");
		return builder.toString();
	}

}
