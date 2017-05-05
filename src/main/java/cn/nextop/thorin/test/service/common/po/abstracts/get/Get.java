package cn.nextop.thorin.test.service.common.po.abstracts.get;

import cn.nextop.thorin.common.glossary.Versionable;
import cn.nextop.thorin.common.persistence.jpa.PersistentObject;

public interface Get<K, P extends Versionable & PersistentObject> {

	public P get(K id);

}
