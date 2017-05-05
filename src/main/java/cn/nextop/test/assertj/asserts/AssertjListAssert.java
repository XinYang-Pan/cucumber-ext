package cn.nextop.test.assertj.asserts;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Iterator;
import java.util.List;

import org.assertj.core.api.ListAssert;

import com.google.common.collect.Lists;

import cn.nextop.test.assertj.hard.AssertjAssertions;
import cn.nextop.test.cucumber.Element;

public class AssertjListAssert<E> extends ListAssert<E> {

	public AssertjListAssert(List<? extends E> actual) {
		super(actual);
	}

	public AssertjListAssert<E> isEachMatchToIgnoringOrder(List<Element> keyValueMapList) {
		// 
		assertThat(actual).as("size").hasSameSizeAs(keyValueMapList);
		List<Element> expectedElements = Lists.newArrayList(keyValueMapList);
		List<Object> actualLeftList = Lists.newArrayList();
		for (Object actualElement : actual) {
			Iterator<Element> it = expectedElements.iterator();
			boolean hasIt = false;
			while (it.hasNext()) {
				Element expectedElement = it.next();
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

	public AssertjListAssert<E> isEachMatchToInOrder(List<Element> keyValueMapList) {
		// 
		assertThat(actual).as("size").hasSameSizeAs(keyValueMapList);
		// 
		List<Object> actualList = Lists.newArrayList(actual);
		for (int i = 0; i < keyValueMapList.size(); i++) {
			Object actualElement = actualList.get(i);
			Element expectedElement = keyValueMapList.get(i);
			AssertjAssertions.assertThat(actualElement).isMatchTo(expectedElement);
		}
		return this;
	}

	public AssertjListAssert<E> isEachMatchTo(List<Element> keyValueMapList, boolean ordered) {
		if (ordered) {
			return isEachMatchToInOrder(keyValueMapList);
		} else {
			return isEachMatchToIgnoringOrder(keyValueMapList);
		}
	}

}
