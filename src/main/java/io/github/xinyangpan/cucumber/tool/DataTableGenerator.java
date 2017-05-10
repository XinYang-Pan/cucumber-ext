package io.github.xinyangpan.cucumber.tool;

import java.beans.PropertyDescriptor;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;

import com.google.common.base.Defaults;
import com.google.common.base.Preconditions;

public class DataTableGenerator {

	public static void generate(Class<?> targetClass, PropertyCollectingType propertyCollectingType, String... ignores) {
		Preconditions.checkNotNull(propertyCollectingType);
		List<PropertyDescriptor> propertyDescriptors = propertyCollectingType.getPropertyDescriptors(targetClass);
		// 
		propertyDescriptors.stream()
			.filter(pd -> !ArrayUtils.contains(ignores, pd.getName()))
			.forEach(pd -> System.out.printf("|%s", pd.getName()));
		System.out.println("|");
		propertyDescriptors.stream()
			.filter(pd -> !ArrayUtils.contains(ignores, pd.getName()))
			.forEach(pd -> System.out.printf("|%s", Defaults.defaultValue(pd.getPropertyType())));
		System.out.println("|");
	}

	public static void generateMap(Class<?> targetClass, PropertyCollectingType propertyCollectingType, String... ignores) {
		List<PropertyDescriptor> propertyDescriptors = propertyCollectingType.getPropertyDescriptors(targetClass);
		// 
		propertyDescriptors.stream()
			.filter(pd -> !ArrayUtils.contains(ignores, pd.getName()))
			.forEach(pd -> {
				System.out.printf("|%s|%s|%n", pd.getName(), Defaults.defaultValue(pd.getPropertyType()));
			});
	}

}
