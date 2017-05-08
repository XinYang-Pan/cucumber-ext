package io.github.xinyangpan.cucumber.assertj.hard;

import java.util.List;

import org.junit.Test;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;

import io.github.xinyangpan.cucumber.assertj.hard.AssertjAssertions;
import io.github.xinyangpan.cucumber.element.BaseElement;
import io.github.xinyangpan.models.person.Person;

public class AssertjAssertionsTest {

	@Test
	public void testListAssertThat() {
		// 
		List<Object> actualList = Lists.newArrayList();
		actualList.add(new Person("AA", 10));
		actualList.add(new Person("BB", 20));
		actualList.add(new Person("CC", 30));
		actualList.add(new Person("DD", 40));
		// 
		List<BaseElement> expectedList = Lists.newArrayList();
		expectedList.add(new BaseElement(ImmutableMap.of("name", "AA", "age", "10")));
		expectedList.add(new BaseElement(ImmutableMap.of("name", "BB", "age", "20")));
		expectedList.add(new BaseElement(ImmutableMap.of("name", "CC", "age", "30")));
		expectedList.add(new BaseElement(ImmutableMap.of("name", "DD", "age", "40")));
		// 
		AssertjAssertions.assertThat(actualList).isEachMatchTo(expectedList, true);
	}

	@Test()
	public void testListAssertThat_ignoring_order() {
		// 
		List<Object> actualList = Lists.newArrayList();
		actualList.add(new Person("AA", 10));
		actualList.add(new Person("BB", 20));
		actualList.add(new Person("CC", 30));
		actualList.add(new Person("DD", 40));
		// 
		List<BaseElement> expectedList = Lists.newArrayList();
		expectedList.add(new BaseElement(ImmutableMap.of("name", "AA", "age", "10")));
		expectedList.add(new BaseElement(ImmutableMap.of("name", "CC", "age", "30")));
		expectedList.add(new BaseElement(ImmutableMap.of("name", "BB", "age", "20")));
		expectedList.add(new BaseElement(ImmutableMap.of("name", "DD", "age", "40")));
		// 
		AssertjAssertions.assertThat(actualList).isEachMatchTo(expectedList, false);
	}

	@Test(expected = AssertionError.class)
	public void testListAssertThatError1() {
		// 
		List<Object> actualList = Lists.newArrayList();
		actualList.add(new Person("AA", 10));
		actualList.add(new Person("BB", 20));
		actualList.add(new Person("CC", 30));
		actualList.add(new Person("DD", 40));
		// 
		List<BaseElement> expectedList = Lists.newArrayList();
		expectedList.add(new BaseElement(ImmutableMap.of("name", "AA", "age", "10")));
		expectedList.add(new BaseElement(ImmutableMap.of("name", "CC", "age", "30")));
		expectedList.add(new BaseElement(ImmutableMap.of("name", "BB", "age", "20")));
		expectedList.add(new BaseElement(ImmutableMap.of("name", "DD", "age", "40")));
		// 
		AssertjAssertions.assertThat(actualList).isEachMatchTo(expectedList, true);
	}

	public void testListAssertThat2() {
		// 
		List<Object> actualList = Lists.newArrayList();
		actualList.add(new Person("AA", 10));
		actualList.add(new Person("BB", 20));
		actualList.add(new Person("CC", 30));
		actualList.add(new Person("DD", 40));
		// 
		List<BaseElement> expectedList = Lists.newArrayList();
		expectedList.add(new BaseElement(ImmutableMap.of("name", "AA", "age", "10")));
		expectedList.add(new BaseElement(ImmutableMap.of("name", "BB", "age", "20")));
		expectedList.add(new BaseElement(ImmutableMap.of("name", "CC", "age", "31")));
		expectedList.add(new BaseElement(ImmutableMap.of("name", "DD", "age", "40")));

		// 
		AssertjAssertions.assertThat(actualList).isEachMatchTo(expectedList, true);
	}

	@Test(expected = AssertionError.class)
	public void testListAssertThat3() {
		// 
		List<Object> actualList = Lists.newArrayList();
		actualList.add(new Person("AA", 10));
		actualList.add(new Person("BB", 20));
		actualList.add(new Person("CC", 30));
		actualList.add(new Person("DD", 40));
		// 
		List<BaseElement> expectedList = Lists.newArrayList();
		expectedList.add(new BaseElement(ImmutableMap.of("name", "AA", "age", "10")));
		expectedList.add(new BaseElement(ImmutableMap.of("name", "BB", "age", "20")));
		expectedList.add(new BaseElement(ImmutableMap.of("name", "BB", "age", "20")));
		expectedList.add(new BaseElement(ImmutableMap.of("name", "DD", "age", "40")));

		// 
		AssertjAssertions.assertThat(actualList).isEachMatchTo(expectedList, true);
	}

}
