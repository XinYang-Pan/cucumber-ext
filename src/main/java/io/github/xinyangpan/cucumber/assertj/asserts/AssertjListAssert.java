package io.github.xinyangpan.cucumber.assertj.asserts;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Iterator;
import java.util.List;

import org.assertj.core.api.ListAssert;

import com.google.common.collect.Lists;

import io.github.xinyangpan.cucumber.assertj.hard.AssertjAssertions;
import io.github.xinyangpan.cucumber.element.BaseElement;

public class AssertjListAssert<E> extends ListAssert<E> {

	public AssertjListAssert(List<? extends E> actual) {
		super(actual);
	}

	public AssertjListAssert<E> isEachMatchToIgnoringOrder(List<? extends BaseElement> keyValueMapList) {
		// 
		assertThat(actual).as("size").hasSameSizeAs(keyValueMapList);
		List<BaseElement> expectedElements = Lists.newArrayList(keyValueMapList);
		List<Object> actualLeftList = Lists.newArrayList();
		fillList(expectedElements, actualLeftList);
		// 
		if (actualLeftList.isEmpty() && expectedElements.isEmpty()) {
			// success
			return this;
		} else {
			// fail
			failWithMessage("expected left elements - %s, actual left elements - %s", expectedElements, actualLeftList);
			return this;
		}
	}

	private void fillList(List<BaseElement> expectedElements, List<Object> actualLeftList) {
		for (Object actualElement : actual) {
			Iterator<BaseElement> it = expectedElements.iterator();
			boolean hasIt = false;
			while (it.hasNext()) {
				BaseElement expectedElement = it.next();
				if (expectedElement.matches(actualElement)) {
					it.remove();
					hasIt = true;
					break;
				}
			}
			if (!hasIt) {
				actualLeftList.add(actualElement);
			}
		}
	}

	public AssertjListAssert<E> isEachMatchToInOrder(List<? extends BaseElement> keyValueMapList) {
		// 
		assertThat(actual).as("size").hasSameSizeAs(keyValueMapList);
		// 
		List<Object> actualList = Lists.newArrayList(actual);
		for (int i = 0; i < keyValueMapList.size(); i++) {
			Object actualElement = actualList.get(i);
			BaseElement expectedElement = keyValueMapList.get(i);
			AssertjAssertions.assertThat(actualElement).isMatchTo(expectedElement);
		}
		return this;
	}

	public AssertjListAssert<E> isEachMatchTo(List<? extends BaseElement> keyValueMapList, boolean ordered) {
		if (ordered) {
			return isEachMatchToInOrder(keyValueMapList);
		} else {
			return isEachMatchToIgnoringOrder(keyValueMapList);
		}
	}

	public AssertjListAssert<E> isContaining(List<? extends BaseElement> keyValueMapList) {
		// 
		List<BaseElement> expectedElements = Lists.newArrayList(keyValueMapList);
		List<Object> actualLeftList = Lists.newArrayList();
		fillList(expectedElements, actualLeftList);
		// 
		if (expectedElements.isEmpty()) {
			// success
			return this;
		} else {
			// fail
			failWithMessage("expected left elements - %s, actual left elements - %s", expectedElements, actualLeftList);
			return this;
		}
	}

}
