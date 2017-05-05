package cn.nextop.thorin.test.service.dealerapi;

import static org.assertj.core.api.Assertions.assertThat;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.base.Suppliers;

import cn.nextop.thorin.api.dealer.proto.SystemServiceProtoV1.LoginResponse;
import cn.nextop.thorin.core.common.glossary.DealingSessionType;
import cn.nextop.thorin.test.service.common.AbstractService;
import support.shell.dlrapi.importer.RpcImporter;
import support.shell.dlrapi.service.SystemService;

public abstract class DealerApiAbstractService extends AbstractService {

	@Autowired
	private RpcImporter rpcImporter;
	@Autowired
	private SystemService systemService;

	protected void init() {
		Suppliers.memoize(this::doInit).get();
	}

	private Void doInit() {
		try {
			// 
			rpcImporter.start();
			rpcImporter.connect().get();
			// 
			LoginResponse login = systemService.login("ROOT", "SYSTEM", DealingSessionType.PRICE);
			assertThat(login.getResult()).as(login.toString()).isEqualTo(LoginResponse.Result.SUCCESS);
			log.debug("init done.");
			return null;
		} catch (Exception e) {
			log.error("init", e);
			throw new RuntimeException(e);
		}
	}

}
