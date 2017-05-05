package cn.nextop.thorin.test.service.common.po.abstracts.update;

import java.util.function.Consumer;

import cn.nextop.thorin.common.glossary.Versionable;
import cn.nextop.thorin.common.persistence.jpa.PersistentObject;

public interface UpdateByUniqueKey<K, P extends Versionable & PersistentObject, U> {

	public P updateByUniqueKey(U uniqueKey, Consumer<P> changer);

}
