//package org.blueo.cucumber.util;
//
//import java.math.BigDecimal;
//import java.util.Map;
//import java.util.function.Function;
//
//import org.apache.commons.lang3.EnumUtils;
//import org.blueo.cucumber.keyword.YesOrNo;
//
//import com.google.common.base.Preconditions;
//import com.google.common.base.Strings;
//import com.google.common.collect.Maps;
//
//public class ParseUtils {
//	private static Map<Class<?>, Function<String, ?>> parserMap = Maps.newHashMap();
//
//	static {
//		initParseFunctionMap();
//	}
//
//	private static void initParseFunctionMap() {
//		// primitives and primitives boxing
//		put(int.class, Integer.class, Integer::valueOf);
//		put(short.class, Short.class, Short::valueOf);
//		put(long.class, Long.class, Long::valueOf);
//		put(byte.class, Byte.class, Byte::valueOf);
//		put(boolean.class, Boolean.class, Boolean::valueOf);
//		// Java Type
//		put(BigDecimal.class, BigDecimal::new);
//		put(String.class, Function.identity());
//		// Custom Type
//		put(YesOrNo.class, YesOrNo::new);
//	}
//
//	public static <T> void put(Class<T> clazz, Function<String, T> parseFunction) {
//		parserMap.put(clazz, parseFunction);
//	}
//
//	public static <T> void put(Class<T> clazz1, Class<T> clazz2, Function<String, T> parseFunction) {
//		parserMap.put(clazz1, parseFunction);
//		parserMap.put(clazz2, parseFunction);
//	}
//
//	@SuppressWarnings("unchecked")
//	public static <T> T parseString(String text, T defaultValue) {
//		Preconditions.checkNotNull(defaultValue);
//		Object value = parseString(text, defaultValue.getClass());
//		if (value == null) {
//			return defaultValue;
//		} else {
//			return (T) value;
//		}
//	}
//
//	@SuppressWarnings({ "unchecked", "rawtypes" })
//	public static <T> T parseString(String text, Class<T> type) {
//		if (Strings.isNullOrEmpty(text)) {
//			return null;
//		}
//		Preconditions.checkNotNull(type);
//		// Enum
//		if (Enum.class.isAssignableFrom(type)) {
//			return (T) EnumUtils.getEnum((Class<Enum>) type, text);
//		}
//		// Parser Map
//		Function<String, ?> biFunction = parserMap.get(type);
//		Preconditions.checkNotNull(biFunction, String.format("Not Supported type %s. text=%s", type, text));
//		return (T) biFunction.apply(text);
//	}
//
//}
