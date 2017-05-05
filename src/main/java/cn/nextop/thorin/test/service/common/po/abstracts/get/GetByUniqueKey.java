package cn.nextop.thorin.test.service.common.po.abstracts.get;

import cn.nextop.thorin.common.glossary.Versionable;
import cn.nextop.thorin.common.persistence.jpa.PersistentObject;

public interface GetByUniqueKey<K, P extends Versionable & PersistentObject, U> extends Get<K, P> {

	public P getByUniqueKey(U id);

}
