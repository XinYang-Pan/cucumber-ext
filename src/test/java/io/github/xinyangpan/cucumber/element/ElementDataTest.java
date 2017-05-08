package io.github.xinyangpan.cucumber.element;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Map;

import org.assertj.core.util.Lists;
import org.junit.Test;

import com.google.common.collect.ImmutableMap;

import io.github.xinyangpan.cucumber.element.Element;
import io.github.xinyangpan.cucumber.element.ElementData;

public class ElementDataTest {

	@Test
	public void test() {
		List<Map<String, String>> valueMaps = Lists.newArrayList();
		valueMaps.add(ImmutableMap.of("name", "aa", "age", "1"));
		valueMaps.add(ImmutableMap.of("name", "aaa", "age", "1"));
		ElementData elementData = ElementData.of(valueMaps).convert("name", "size", t -> t.length());
		// 
		List<Element> elements = elementData.getElements();
		// 
		Element element1 = new Element(ImmutableMap.of("size", "2", "age", "1"));
		Element element2 = new Element(ImmutableMap.of("size", "3", "age", "1"));
		assertThat(elements).hasSize(2).containsExactly(element1, element2);
	}

}
