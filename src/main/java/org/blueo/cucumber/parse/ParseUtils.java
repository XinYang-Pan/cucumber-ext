package org.blueo.cucumber.parse;

import static java.util.Objects.requireNonNull;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.blueo.cucumber.keyword.YesOrNo;
import org.javatuples.Pair;
import org.springframework.beans.BeanUtils;
import org.springframework.util.Assert;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.Iterators;
import com.google.common.collect.Maps;

public class ParseUtils {
	private static Map<Class<?>, Function<String, ?>> parserMap = Maps.newHashMap();

	static {
		initParseFunctionMap();
	}

	private static void initParseFunctionMap() {
		// primitives and primitives boxing
		put(int.class, Integer.class, Integer::valueOf);
		put(short.class, Short.class, Short::valueOf);
		put(long.class, Long.class, Long::valueOf);
		put(byte.class, Byte.class, Byte::valueOf);
		put(boolean.class, Boolean.class, Boolean::valueOf);
		// Java Type
		put(BigDecimal.class, BigDecimal::new);
		put(String.class, Function.identity());
		// Custom Type
		put(YesOrNo.class, YesOrNo::new);
	}

	public static <T> void put(Class<T> clazz, Function<String, T> parseFunction) {
		parserMap.put(clazz, parseFunction);
	}

	public static <T> void put(Class<T> clazz1, Class<T> clazz2, Function<String, T> parseFunction) {
		parserMap.put(clazz1, parseFunction);
		parserMap.put(clazz2, parseFunction);
	}

	@SuppressWarnings("unchecked")
	public static <T> T parseString(String text, T defaultValue) {
		Preconditions.checkNotNull(defaultValue);
		Object value = parseString(text, defaultValue.getClass());
		if (value == null) {
			return defaultValue;
		} else {
			return (T) value;
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T> T parseString(String text, Class<T> type) {
		if (Strings.isNullOrEmpty(text)) {
			return null;
		}
		Preconditions.checkNotNull(type);
		// Enum
		if (Enum.class.isAssignableFrom(type)) {
			return (T) EnumUtils.getEnum((Class<Enum>) type, text);
		}
		// Parser Map
		Function<String, ?> biFunction = parserMap.get(type);
		Preconditions.checkNotNull(biFunction, String.format("Not Supported type %s. text=%s", type, text));
		return (T) biFunction.apply(text);
	}

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
			Pair<Object, Class<?>> target = getFieldValueAndType(t, fieldLocator);
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
						Object parseString = parseString(value, pd.getPropertyType());
						Preconditions.checkNotNull(target.getValue0(), String.format("Inner Object is null, you have to create it yourself. ref=%s", rawName));
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

	/**
	 * To get value and type of rawName from obj
	 * 
		// prepare
		LpBandImpl ask = new LpBandImpl();
		ask.setPrice(new Price("100.000"));
		List<LpBand> list = Lists.newArrayList(ask);
		// test target
		Pair<Object, Class<?>> target = TestUtils.getTarget(list, "0.price");
		// assert
		assertThat(target.getValue0()).isEqualTo(new Price("100.000"));
		assertThat(target.getValue1()).isEqualTo((Object) Price.class);
	 * 
	 * 
	 * @param obj
	 * @param rawName
	 * @return
	 */
	public static Pair<Object, Class<?>> getFieldValueAndType(Object obj, String rawName) {
		return getFieldValueAndType(obj, rawName.split("\\."));
	}

	private static Pair<Object, Class<?>> getFieldValueAndType(Object obj, FieldLocator fieldLocator) {
		//
		Preconditions.checkNotNull(fieldLocator);
		Preconditions.checkNotNull(obj);
		//
		if (fieldLocator.hasPrefixes()) {
			return getFieldValueAndType(obj, fieldLocator.getPrefixes());
		} else {
			return Pair.with(obj, obj.getClass());
		}
	}

	private static Pair<Object, Class<?>> getFieldValueAndType(Object obj, String[] fieldNames) {
		try {
			Iterator<String> iterator = Iterators.forArray(fieldNames);
			assertThat(iterator.hasNext()).isTrue();
			Object target = obj;
			Class<?> targetType = obj.getClass();
			while (iterator.hasNext()) {
				String name = (String) iterator.next();
				if (isList(targetType, name)) {
					int index = Integer.valueOf(name);
					target = CollectionUtils.get(target, index);
					if (target != null) {
						targetType = target.getClass();
					} else {
						targetType = null;
					}
				} else {
					//
					PropertyDescriptor pd = BeanUtils.getPropertyDescriptor(target.getClass(), name);
					requireNonNull(pd, String.format("Not found for field %s", name));
					target = pd.getReadMethod().invoke(target);
					targetType = pd.getPropertyType();
				}
			}
			return Pair.with(target, targetType);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static boolean isList(Class<?> propertyType, String index) {
		if (List.class.isAssignableFrom(propertyType) && NumberUtils.isDigits(index)) {
			return true;
		} else {
			return false;
		}
	}

}
