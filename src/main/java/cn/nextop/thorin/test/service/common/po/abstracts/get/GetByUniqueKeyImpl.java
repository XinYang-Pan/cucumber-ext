package cn.nextop.thorin.test.service.common.po.abstracts.get;

import java.util.function.Function;

import com.google.common.base.Converter;

import cn.nextop.thorin.common.glossary.Versionable;
import cn.nextop.thorin.common.persistence.jpa.PersistentObject;

public class GetByUniqueKeyImpl<K, P extends Versionable & PersistentObject, U> extends GetImpl<K, P> implements GetByUniqueKey<K, P, U> {

	protected Converter<K, U> converter;
	protected Function<U, P> doGetByUniqueKey;

	public GetByUniqueKeyImpl() {
	}
	
	public GetByUniqueKeyImpl(Converter<K, U> converter, Function<U, P> doGetByUniqueKey) {
		this.converter = converter;
		this.doGetByUniqueKey = doGetByUniqueKey;
	}
	
	@Override
	protected final P doGet(K id) {
		U uniqueKey = this.converter.convert(id);
		return this.doGetByUniqueKey(uniqueKey);
	}

	@Override
	public final P getByUniqueKey(U uniqueKey) {
		P p = this.doGetByUniqueKey(uniqueKey);
		return this.validateGet(p, uniqueKey);
	}

	private P doGetByUniqueKey(U uniqueKey) {
		return doGetByUniqueKey.apply(uniqueKey);
	}

}
