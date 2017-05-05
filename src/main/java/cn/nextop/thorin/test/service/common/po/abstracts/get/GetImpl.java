package cn.nextop.thorin.test.service.common.po.abstracts.get;

import java.util.function.Function;

import org.springframework.util.Assert;

import cn.nextop.thorin.common.glossary.Versionable;
import cn.nextop.thorin.common.persistence.jpa.PersistentObject;

public class GetImpl<K, P extends Versionable & PersistentObject> implements Get<K, P>{
	// 
	protected boolean getNullable = false;
	protected Function<K, P> doGet;
	
	public P get(K id) {
		P p = doGet(id);
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

	protected P doGet(K id) {
		return doGet.apply(id);
	}
	
}
