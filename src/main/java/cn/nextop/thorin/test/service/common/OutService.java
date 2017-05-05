package cn.nextop.thorin.test.service.common;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.nextop.thorin.core.dealing.orm.service.DealingPositionService;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@Service
public class OutService {
	private static final Logger LOGGER = LoggerFactory.getLogger(OutService.class);
	private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
	// 
	@Autowired
	private CommonService commonService;
	@Autowired
	private DealingPositionService dealingPositionService;

	public void jenkinsBuild() throws Throwable {
		Set<Byte> allCompanyIds = commonService.getAllCompanyIds();
		long count = allCompanyIds.stream().map(dealingPositionService::findPositions).flatMap(List::stream).count();
		if (count > 0) {
			this.doBuild();
		}
	}

	private void doBuild() throws IOException, InterruptedException {
		LOGGER.info("Starting Jenkins Build ...");
		OkHttpClient client = new OkHttpClient();
		RequestBody body = RequestBody.create(JSON, "");
		// @formatter:off
		Request request = new Request.Builder()
			.url("http://192.168.1.212:8080/view/11.THORIN.TEST/job/04.DEPLOY.THORIN.TEST.CLEAN.RESTART/build")
			.header("Authorization", "Basic cGFueHk6cGFueHkxMjM=")
			.post(body)
			.build();
		// @formatter:on
		Response response = client.newCall(request).execute();
		LOGGER.info("Jenkins Job Response - {}", response);
		// 5 minutes sleep
		Thread.sleep(5 * 60 * 1000L);
		LOGGER.info("Jenkins Build completed.");
	}

}
