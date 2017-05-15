package io.github.xinyangpan.cucumber.element;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.assertj.core.util.Lists;
import org.junit.Test;

import com.google.common.collect.ImmutableMap;

public class ElementDataTest {

	@Test
	public void test_common_and_function() {
		List<Map<String, String>> valueMaps = Lists.newArrayList();
		valueMaps.add(ImmutableMap.of("name", "aa", "age", "1"));
		valueMaps.add(ImmutableMap.of("name", "aaa", "age", "1"));
		ElementData<BaseElement> elementData = new ElementData<>(valueMaps, null, BaseElement::new).convert("name", "size", t -> t.length());
		// 
		List<BaseElement> baseElements = elementData.getElements();
		// 
		BaseElement element1 = new BaseElement(ImmutableMap.of("size", "2", "age", "1"));
		BaseElement element2 = new BaseElement(ImmutableMap.of("size", "3", "age", "1"));
		assertThat(baseElements).hasSize(2).containsExactly(element1, element2);
	}

	@Test
	public void test_keyset1() {
		List<Map<String, String>> valueMaps = Lists.newArrayList();
		valueMaps.add(ImmutableMap.of("name", "", "age", "1"));
		ElementData<BaseElement> elementData = new ElementData<>(valueMaps, null, BaseElement::new);
		// 
		Set<String> elementKeys = elementData.getElementKeys();
		// 
		assertThat(elementKeys).hasSize(1).containsExactlyInAnyOrder("age");
	}

	@Test
	public void test_keyset2() {
		List<Map<String, String>> valueMaps = Lists.newArrayList();
		valueMaps.add(ImmutableMap.of("name", "", "age", "1"));
		valueMaps.add(ImmutableMap.of("name", "aaa", "age", "1"));
		ElementData<BaseElement> elementData = new ElementData<>(valueMaps, null, BaseElement::new);
		// 
		Set<String> elementKeys = elementData.getElementKeys();
		// 
		assertThat(elementKeys).hasSize(2).containsExactlyInAnyOrder("name", "age");
	}

}
