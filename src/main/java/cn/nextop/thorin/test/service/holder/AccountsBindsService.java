package cn.nextop.thorin.test.service.holder;

import static com.google.common.collect.Lists.transform;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;

import cn.nextop.thorin.common.util.collection.map.LongHashMap;
import cn.nextop.thorin.core.holding.HoldingStrategyAdministrator;
import cn.nextop.thorin.core.holding.domain.pipeline.request.impl.BindAccountsRequest;
import cn.nextop.thorin.core.holding.domain.pipeline.response.impl.BindAccountsResponse;
import cn.nextop.thorin.core.holding.orm.po.AccountsConfigPo;
import cn.nextop.thorin.core.holding.orm.po.AccountsPo;
import cn.nextop.thorin.test.core.assertj.hard.ThorinAssertions;
import cn.nextop.thorin.test.service.common.AbstractService;
import cn.nextop.thorin.test.util.HolderTestUtils;

@Service
@Lazy
public class AccountsBindsService extends AbstractService {
	@Autowired
	private HoldingStrategyAdministrator admin;

	public List<AccountsConfigPo> getAccountsConfigPos(int accountsId) {
		LongHashMap<AccountsConfigPo> accountsConfigs = admin.getAccountsConfigs(accountsId);
		return Lists.newArrayList(accountsConfigs.values());
	}

	public AccountsPo bindAccounts(AccountsPo accountsPo, List<AccountsConfigPo> accountsConfigPos) {
		List<AccountsConfigPo> configs = Lists.newArrayList(transform(accountsConfigPos, accountsConfigPo -> HolderTestUtils.buildAccountsConfigPo(accountsPo, accountsConfigPo)));
		BindAccountsRequest request = new BindAccountsRequest(accountsPo.getCompanyId());
		request.setAccounts(accountsPo);
		request.setAccountsConfigs(configs);
		log.debug("BindAccountsRequest = {}", request);
		BindAccountsResponse response = admin.bind(request);
		log.debug("BindAccountsResponse = {}", response);
		ThorinAssertions.assertThat(response).isSuccessful();
		return Iterators.getOnlyElement(response.accounts());
	}

}
