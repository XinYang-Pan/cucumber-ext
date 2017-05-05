package cn.nextop.thorin.test.core.assertj.asserts.po;

import cn.nextop.test.assertj.asserts.AssertjAbstractAssert;
import cn.nextop.thorin.common.persistence.jpa.impl.AbstractPersistentObject;

public abstract class AbstractPoAssert<S extends AbstractPoAssert<S, A>, A extends AbstractPersistentObject> extends AssertjAbstractAssert<S, A> {

	public AbstractPoAssert(A actual, Class<?> selfType) {
		super(actual, selfType);
	}

}
