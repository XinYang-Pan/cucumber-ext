package io.github.xinyangpan.cucumber.util;
//package org.blueo.cucumber.util;
//
//import static java.util.Objects.requireNonNull;
//import static org.assertj.core.api.Assertions.assertThat;
//
//import java.beans.PropertyDescriptor;
//import java.util.Iterator;
//import java.util.List;
//
//import org.apache.commons.collections4.CollectionUtils;
//import org.apache.commons.lang3.math.NumberUtils;
//import org.javatuples.Pair;
//import org.springframework.beans.BeanUtils;
//
//import com.google.common.base.Preconditions;
//import com.google.common.collect.Iterators;
//
//public class FieldUtils {
//
//	/**
//	 * To get value and type of rawName from obj
//	 * 
//		// prepare
//		LpBandImpl ask = new LpBandImpl();
//		ask.setPrice(new Price("100.000"));
//		List<LpBand> list = Lists.newArrayList(ask);
//		// test target
//		Pair<Object, Class<?>> target = TestUtils.getTarget(list, "0.price");
//		// assert
//		assertThat(target.getValue0()).isEqualTo(new Price("100.000"));
//		assertThat(target.getValue1()).isEqualTo((Object) Price.class);
//	 * 
//	 * 
//	 * @param obj
//	 * @param rawName
//	 * @return
//	 */
//	public static Pair<Object, Class<?>> getFieldValueAndType(Object obj, String rawName) {
//		return getFieldValueAndType(obj, rawName.split("\\."));
//	}
//
//	static Pair<Object, Class<?>> getFieldValueAndType(Object obj, FieldLocator fieldLocator) {
//		//
//		Preconditions.checkNotNull(fieldLocator);
//		Preconditions.checkNotNull(obj);
//		//
//		if (fieldLocator.hasPrefixes()) {
//			return getFieldValueAndType(obj, fieldLocator.getPrefixes());
//		} else {
//			return Pair.with(obj, obj.getClass());
//		}
//	}
//
//	private static Pair<Object, Class<?>> getFieldValueAndType(Object obj, String[] fieldNames) {
//		try {
//			Iterator<String> iterator = Iterators.forArray(fieldNames);
//			assertThat(iterator.hasNext()).isTrue();
//			Object target = obj;
//			Class<?> targetType = obj.getClass();
//			while (iterator.hasNext()) {
//				String name = (String) iterator.next();
//				if (isList(targetType, name)) {
//					int index = Integer.valueOf(name);
//					target = CollectionUtils.get(target, index);
//					if (target != null) {
//						targetType = target.getClass();
//					} else {
//						targetType = null;
//					}
//				} else {
//					//
//					PropertyDescriptor pd = BeanUtils.getPropertyDescriptor(target.getClass(), name);
//					requireNonNull(pd, String.format("Not found for field %s", name));
//					target = pd.getReadMethod().invoke(target);
//					targetType = pd.getPropertyType();
//				}
//			}
//			return Pair.with(target, targetType);
//		} catch (Exception e) {
//			throw new RuntimeException(e);
//		}
//	}
//
//	private static boolean isList(Class<?> propertyType, String index) {
//		if (List.class.isAssignableFrom(propertyType) && NumberUtils.isDigits(index)) {
//			return true;
//		} else {
//			return false;
//		}
//	}
//}
