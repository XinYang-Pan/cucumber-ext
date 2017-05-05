package cn.nextop.thorin.test.service.common.po.abstracts.update;

import cn.nextop.thorin.common.glossary.Versionable;
import cn.nextop.thorin.common.persistence.jpa.PersistentObject;

public interface Update<K, P extends Versionable & PersistentObject> {

	public P update(P input);

}
