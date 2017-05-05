package cn.nextop.thorin.test.service.common.po;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;
import java.util.function.Consumer;

import com.google.common.base.Converter;
import com.google.common.collect.Maps;

import cn.nextop.test.cucumber.Element;
import cn.nextop.thorin.common.glossary.Versionable;
import cn.nextop.thorin.common.persistence.jpa.PersistentObject;

public abstract class IdCalculableService<K, P extends Versionable & PersistentObject, U> extends AbstractPoService<K, P> {

	protected final P doGet(K id) {
		U uniqueKey = this.getConverter().convert(id);
		return this.doGetByUniqueKey(uniqueKey);
	}

	public final P getByUniqueKey(U uniqueKey) {
		P p = this.doGetByUniqueKey(uniqueKey);
		return this.validateGet(p, uniqueKey);
	}

	public final P updateByUniqueKey(U uniqueKey, Consumer<P> changer) {
		Converter<K, U> converter = this.getConverter();
		return super.update(converter.reverse().convert(uniqueKey), changer);
	}

	public final P updateByUniqueKey(U uniqueKey, Element element) {
		return this.updateByUniqueKey(uniqueKey, element::putValuesTo);
	}

	protected final P doUpdate(P input) {
		// update
		Map<K, P> valueMap = Maps.newHashMap();
		valueMap.put(this.getId(input), input);
		Map<K, P> returnMap = this.update(valueMap);
		// assert return
		assertThat(returnMap).hasSize(1);
		P result = returnMap.get(this.getId(input));
		return result;
	}
	
	protected abstract K getId(P po);
	
	protected abstract Converter<K, U> getConverter();

	protected abstract P doGetByUniqueKey(U uniqueKey);

	protected abstract Map<K, P> update(Map<K, P> valueMap);
	
	
}
