package cn.nextop.thorin.test.service.common.po.abstracts;

import cn.nextop.thorin.common.glossary.Versionable;
import cn.nextop.thorin.common.persistence.jpa.PersistentObject;

public interface Id<K, P extends Versionable & PersistentObject> {
	
	K getId(P p);
	
}
