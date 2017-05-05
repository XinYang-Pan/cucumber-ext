package cn.nextop.thorin.test.service.batch;

import cn.nextop.thorin.core.batch.BatchListener;

public interface BatchListenerAdaptor extends BatchListener {

	@Override
	public default void beforeJob(long id, String job) {

	}

	@Override
	public default void afterJob(long id, String job, String status) {

	}

	@Override
	public default void beforeStep(long id, String job, String step) {

	}

	@Override
	public default void afterStep(long id, String job, String step, String status) {

	}

}
