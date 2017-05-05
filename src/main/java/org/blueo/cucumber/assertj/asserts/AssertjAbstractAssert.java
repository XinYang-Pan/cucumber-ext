package org.blueo.cucumber.assertj.asserts;

import java.util.Map;

import org.assertj.core.api.AbstractObjectAssert;
import org.blueo.cucumber.element.Element;

public abstract class AssertjAbstractAssert<S extends AbstractObjectAssert<S, A>, A> extends AbstractObjectAssert<S, A> {

	public AssertjAbstractAssert(A actual, Class<?> selfType) {
		super(actual, selfType);
	}

	// for Comparable - using compare
	// others - using equals
	public S isMatchTo(Map<String, String> keyValueMap) {
		return isMatchTo(new Element(keyValueMap));
	}

	public S isMatchTo(Element element) {
		AssertionError error;
		try {
			if (!element.matches(actual)) {
				failWithMessage("Expected to be <%s> but was <%s>", element, actual);
			}
			error = null;
		} catch (AssertionError e) {
			error = e;
		}
		// 
		if (element.isExpectFail()) {
			if (error == null) {
				failWithMessage("Expected to fail but passed.", element, actual);
			}
		} else {
			if (error != null) {
				throw error;
			}
		}
		return myself;
	}

}
