package cn.nextop.thorin.test.core.assertj.delegate;

import org.assertj.core.api.AssertDelegateTarget;

import cn.nextop.test.cucumber.Element;

public interface AssertByElement<A> extends AssertDelegateTarget {

	public A assertBy(Element element);

}
