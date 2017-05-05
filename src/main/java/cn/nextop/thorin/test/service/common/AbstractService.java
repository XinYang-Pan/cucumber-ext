package cn.nextop.thorin.test.service.common;

import org.springframework.beans.factory.annotation.Autowired;

import cn.nextop.thorin.common.messaging.Messenger;
import cn.nextop.thorin.common.util.marshaller.Marshallable;

public class AbstractService extends AbstractParent {
	@Autowired
	protected Messenger<Marshallable> messenger;
	@Autowired
	protected ParseService parseService;
	@Autowired
	protected ElementDataService elementDataService;

}
