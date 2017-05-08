package io.github.xinyangpan.cucumber.assertj.asserts;

import java.util.Map;

import org.assertj.core.api.AbstractObjectAssert;

import io.github.xinyangpan.cucumber.element.BaseElement;

public abstract class AssertjAbstractAssert<S extends AbstractObjectAssert<S, A>, A> extends AbstractObjectAssert<S, A> {

	public AssertjAbstractAssert(A actual, Class<?> selfType) {
		super(actual, selfType);
	}

	// for Comparable - using compare
	// others - using equals
	public S isMatchTo(Map<String, String> keyValueMap) {
		return isMatchTo(new BaseElement(keyValueMap));
	}

	public S isMatchTo(BaseElement baseElement) {
		AssertionError error;
		try {
			if (!baseElement.matches(actual)) {
				failWithMessage("Expected to be <%s> but was <%s>", baseElement, actual);
			}
			error = null;
		} catch (AssertionError e) {
			error = e;
		}
		// 
		if (baseElement.isExpectFail()) {
			if (error == null) {
				failWithMessage("Expected to fail but passed.", baseElement, actual);
			}
		} else {
			if (error != null) {
				throw error;
			}
		}
		return myself;
	}

}
