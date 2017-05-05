package cn.nextop.thorin.test.service.common.po.abstracts.update;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

import com.google.common.base.Converter;
import com.google.common.collect.Maps;

import cn.nextop.thorin.common.glossary.Versionable;
import cn.nextop.thorin.common.persistence.jpa.PersistentObject;

public class UpdateByUniqueKeyImpl<K, P extends Versionable & PersistentObject, U> extends UpdateImpl<K, P> implements UpdateByUniqueKey<K, P, U> {

	protected Converter<K, U> converter;
	protected Function<Map<K, P>, Map<K, P>> update;

	public UpdateByUniqueKeyImpl(Converter<K, U> converter, Function<Map<K, P>, Map<K, P>> update) {
		this.converter = converter;
		this.update = update;
	}
	
	@Override
	public final P updateByUniqueKey(U uniqueKey, Consumer<P> changer) {
		return update(converter.reverse().convert(uniqueKey), changer);
	}

	@Override
	protected final P doUpdate(P input) {
		// update
		Map<K, P> valueMap = Maps.newHashMap();
		valueMap.put(id.getId(input), input);
		Map<K, P> returnMap = this.update(valueMap);
		// assert return
		assertThat(returnMap).hasSize(1);
		P result = returnMap.get(id.getId(input));
		return result;
	}

	protected Map<K, P> update(Map<K, P> valueMap) {
		return update.apply(valueMap);
	}

}
