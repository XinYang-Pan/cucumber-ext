package io.github.xinyangpan.cucumber.tool;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.google.common.collect.Lists;

public enum PropertyCollectingType {

	DECLARED_FIELD() {
		public List<PropertyDescriptor> getPropertyDescriptors(Class<?> targetClass) {
			try {
				List<PropertyDescriptor> list = Lists.newArrayList();
				Class<?> current = targetClass;
				while (current != Object.class) {
					Field[] declaredFields = targetClass.getDeclaredFields();
					for (Field field : declaredFields) {
						list.add(new PropertyDescriptor(field.getName(), targetClass));
					}
					current = current.getSuperclass();
				}
				return list;
			} catch (IntrospectionException e) {
				throw new RuntimeException(e);
			}
		}
	},

	PROPERTY_DESC {
		public List<PropertyDescriptor> getPropertyDescriptors(Class<?> targetClass) {
			return Lists.newArrayList(BeanUtils.getPropertyDescriptors(targetClass));
		}
	};

	public abstract List<PropertyDescriptor> getPropertyDescriptors(Class<?> targetClass);

}
