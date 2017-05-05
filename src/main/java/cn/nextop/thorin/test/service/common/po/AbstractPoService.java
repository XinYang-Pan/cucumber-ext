package cn.nextop.thorin.test.service.common.po;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import cn.nextop.test.cucumber.Element;
import cn.nextop.thorin.common.glossary.Versionable;
import cn.nextop.thorin.common.persistence.jpa.PersistentObject;
import cn.nextop.thorin.test.service.common.CommonService;
import cn.nextop.thorin.test.util.TestUtils;

public abstract class AbstractPoService<K, P extends Versionable & PersistentObject> {
	// 
	protected boolean getNullable = false;
	
	@Autowired
	protected CommonService commonService;

	public P get(K id) {
		P p = this.doGet(id);
		return this.validateGet(p, id);
	}

	@SuppressWarnings("unchecked")
	protected P validateGet(P p, Object ref) {
		if (p == null) {
			Assert.state(getNullable, String.format("Not Allow Nullable. ref=%s", ref));
			return p;
		} else {
			return (P) p.copy();
		}
	}

	public final P update(K id, Consumer<P> changer) {
		P p = this.get(id);
		changer.accept(p);
		return this.update(p);
	}

	public final P update(K id, Element element) {
		return this.update(id, element::putValuesTo);
	}

	public P update(P input) {
		// update
		P result = this.doUpdate(input);
		// assert return
		assertThat(result).isNotNull();
		this.updateAssert(result, input);
		// assert reselect
		P reselect = this.get(this.getId(input));
		assertThat(reselect).isNotNull();
		this.updateAssert(reselect, input);
		return result;
	}

	protected void updateAssert(P result, P input) {
		assertThat(result).isEqualToIgnoringGivenFields(input, TestUtils.IGNORE_FIELDS);
	}
	
	protected abstract K getId(P po);

	protected abstract P doGet(K id);

	protected abstract P doUpdate(P input);

}
