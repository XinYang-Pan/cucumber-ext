package org.blueo.cucumber.util;

import static org.assertj.core.api.Assertions.fail;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.javatuples.Pair;
import org.springframework.beans.BeanUtils;
import org.springframework.util.Assert;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;

public class PopulateUtils {

	public static <T> T populateFields(Map<String, String> map, T t) {
		return populateFields(map, t, null);
	}

	public static <T> T populateFields(Map<String, String> map, T t, String prefix) {
		//
		Preconditions.checkNotNull(map);
		Preconditions.checkArgument(!map.isEmpty());
		Preconditions.checkNotNull(t);
		//
		for (Entry<String, String> e : map.entrySet()) {
			String rawName = e.getKey();
			Preconditions.checkArgument(StringUtils.isNotEmpty(rawName));
			Assert.hasText(rawName, "rawName cannot be empty.");
			//
			if (prefix != null) {
				if (!rawName.startsWith(prefix)) {
					continue;
				} else {
					rawName = StringUtils.removeStart(rawName, prefix);
				}
			} else {
				// ignore prefix with "_"
				if (rawName.startsWith("_")) {
					continue;
				}
			}
			String value = e.getValue();
			if (Strings.isNullOrEmpty(value)) {
				continue;
			}
			populateField(t, rawName, value);
		}
		return t;
	}

	public static void populateField(Object t, String rawName, String value) {
		populateField(t, rawName, value, false);
	}

	@SuppressWarnings("unchecked")
	private static void populateField(Object t, String rawName, String value, boolean ignoreNoneMatches) {
		try {
			FieldLocator fieldLocator = new FieldLocator(rawName);
			Pair<Object, Class<?>> target = FieldUtils.getFieldValueAndType(t, fieldLocator);
			if (List.class.isAssignableFrom(target.getValue1())) {
				((List<String>) target.getValue0()).add(value);
			} else if (Map.class.isAssignableFrom(target.getValue1())) {
				fail("Not finished yet.");
			} else {
				//
				PropertyDescriptor pd = BeanUtils.getPropertyDescriptor(target.getValue1(), fieldLocator.getFieldName());
				if (pd != null) {
					Method m = pd.getWriteMethod();
					if (m != null) {
						Object parseString = ParseUtils.parseString(value, pd.getPropertyType());
						Preconditions.checkNotNull(target.getValue0(), String.format("Inner Object is null, you have to create it yourself. target=%s, rawName=%s", target, rawName));
						m.invoke(target.getValue0(), parseString);
					} else {
						if (!ignoreNoneMatches) {
							fail(String.format("No field found for %s.", rawName));
						}
					}
				} else {
					if (!ignoreNoneMatches) {
						fail(String.format("No field found for %s.", rawName));
					}
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
