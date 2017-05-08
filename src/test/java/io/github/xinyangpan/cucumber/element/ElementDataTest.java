package io.github.xinyangpan.cucumber.element;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Map;

import org.assertj.core.util.Lists;
import org.junit.Test;

import com.google.common.collect.ImmutableMap;

import io.github.xinyangpan.cucumber.element.BaseElement;
import io.github.xinyangpan.cucumber.element.ElementData;

public class ElementDataTest {

	@Test
	public void test() {
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

}
