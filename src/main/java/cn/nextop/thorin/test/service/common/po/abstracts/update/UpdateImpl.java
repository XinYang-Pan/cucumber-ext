package cn.nextop.thorin.test.service.common.po.abstracts.update;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.Consumer;
import java.util.function.Function;

import cn.nextop.thorin.common.glossary.Versionable;
import cn.nextop.thorin.common.persistence.jpa.PersistentObject;
import cn.nextop.thorin.test.service.common.po.abstracts.Id;
import cn.nextop.thorin.test.service.common.po.abstracts.get.Get;
import cn.nextop.thorin.test.util.TestUtils;

public class UpdateImpl<K, P extends Versionable & PersistentObject> implements Update<K, P> {

	protected Id<K, P> id;
	protected Get<K, P> get;
	protected Function<P, P> doUpdate;

	public P update(P input) {
		// update
		P result = this.doUpdate(input);
		// assert return
		assertThat(result).isNotNull();
		this.updateAssert(result, input);
		// assert reselect
		P reselect = get.get(id.getId(input));
		assertThat(reselect).isNotNull();
		this.updateAssert(reselect, input);
		return result;
	}

	public final P update(K id, Consumer<P> changer) {
		P p = get.get(id);
		changer.accept(p);
		return this.update(p);
	}
	
	protected void updateAssert(P result, P input) {
		assertThat(result).isEqualToIgnoringGivenFields(input, TestUtils.IGNORE_FIELDS);
	}

	protected P doUpdate(P input) {
		return doUpdate.apply(input);
	}

}
