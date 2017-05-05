package cn.nextop.thorin.test.core.assertj.asserts.response;

import static org.assertj.core.api.Assertions.assertThat;

import cn.nextop.test.assertj.asserts.AssertjAbstractAssert;
import cn.nextop.thorin.core.common.domain.pipeline.Response;

public class ResponseAssert<S extends ResponseAssert<S, A>, A extends Response> extends AssertjAbstractAssert<S, A> {

	public ResponseAssert(A actual, Class<?> selfType) {
		super(actual, selfType);
	}

	public S isSuccessful() {
		this.isNotNull();
		assertThat(this.actual.isSuccessful()).as("Expected to be successful. ref - %s", actual).isTrue();
		return myself;
	}

	public S isNotSuccessful() {
		this.isNotNull();
		assertThat(this.actual.isSuccessful()).as("Expected to be NOT successful. ref - %s", actual).isFalse();
		return myself;
	}

}
