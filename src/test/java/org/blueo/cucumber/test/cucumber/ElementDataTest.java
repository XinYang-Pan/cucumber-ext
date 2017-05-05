package org.blueo.cucumber.test.cucumber;

import java.util.List;
import java.util.Map;

import org.assertj.core.util.Lists;
import org.blueo.cucumber.element.ElementData;
import org.junit.Test;

import com.google.common.collect.ImmutableMap;

public class ElementDataTest {

	@Test
	public void test() {
		List<Map<String, String>> valueMaps = Lists.newArrayList();
		valueMaps.add(ImmutableMap.of("name", "aa", "age", "1"));
		valueMaps.add(ImmutableMap.of("name", "aaa", "age", "1"));
		ElementData elementData = ElementData.of(valueMaps).convert("name", "size", t -> t.length());
		System.out.println(elementData);
	}

}
