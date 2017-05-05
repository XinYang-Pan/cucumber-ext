package cn.nextop.thorin.test.util;

import java.util.Collection;
import java.util.Date;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Stream;

import org.springframework.util.Assert;

import cn.nextop.thorin.common.glossary.Versionable;
import cn.nextop.thorin.common.persistence.jpa.PersistentObject;
import cn.nextop.thorin.common.persistence.jpa.impl.AbstractPersistentObject;
import cn.nextop.thorin.common.util.assertion.AssertionException;
import cn.nextop.thorin.core.common.domain.glossary.OperatorAware;
import cn.nextop.thorin.core.common.glossary.BidAsk;

public class TestUtils {
	public final static String[] IGNORE_FIELDS = new String[] { "updateDatetime", "insertDatetime", "version" };
	public final static String[] IGNORE_FIELDS_WITH_ID = new String[] { "updateDatetime", "insertDatetime", "version", "id" };
	
	public static <T> void setIfNotNull(Supplier<T> getter, Consumer<T> setter) {
		T t = getter.get();
		if (t != null) {
			setter.accept(t);
		}
	}
	
	public static <E extends Enum<E>> IllegalArgumentException illegal(E e) {
		Assert.notNull(e);
		throw illegal(e.getDeclaringClass(), e);
	}

	public static <E extends Enum<E>> IllegalArgumentException illegal(Class<E> clazz, E e) {
		throw new IllegalArgumentException(String.format("Enum %s doesn't support value %s here.", clazz.getSimpleName(), e));
	}

	public static void run(BidAsk side, Runnable bid, Runnable ask) {
		switch (side) {
		case BID: bid.run(); break; case ASK: ask.run(); break;
		default: throw new AssertionException("assertion failed, invalid side: " + side);
		}
	}

	public static <T> Stream<T> stream(Collection<T> c) {
		if (c == null) {
			return Stream.empty();
		} else {
			return c.stream();
		}
	}

	public static <T extends PersistentObject> T setCommonForInsert(T po) {
		if (po instanceof Versionable) {
			((Versionable) po).setVersion(1);
		}
		if (po instanceof OperatorAware) {
			((OperatorAware) po).setInsertOperator((short) 0);
			((OperatorAware) po).setUpdateOperator((short) 0);
		}
		if (po instanceof AbstractPersistentObject) {
			((AbstractPersistentObject) po).setInsertDatetime(new Date());
			((AbstractPersistentObject) po).setUpdateDatetime(new Date());
		}
		return po;
	}

}
