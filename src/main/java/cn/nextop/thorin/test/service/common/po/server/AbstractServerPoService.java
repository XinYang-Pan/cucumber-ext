package cn.nextop.thorin.test.service.common.po.server;

import cn.nextop.thorin.common.glossary.Versionable;
import cn.nextop.thorin.common.persistence.jpa.PersistentObject;
import cn.nextop.thorin.test.service.common.po.IdCalculableService;

public abstract class AbstractServerPoService<K, P extends Versionable & PersistentObject, U> extends IdCalculableService<K, P, U> {
	
	// common cache need client to add version
	@Override
	public P update(P input) {
		input.increaseVersion();
		return super.update(input);
	}

}
