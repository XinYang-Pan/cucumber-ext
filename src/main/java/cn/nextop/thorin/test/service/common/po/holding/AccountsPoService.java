package cn.nextop.thorin.test.service.common.po.holding;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.google.common.collect.Collections2;
import com.google.common.collect.Iterables;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;

import cn.nextop.thorin.common.util.Objects;
import cn.nextop.thorin.common.util.collection.map.IntHashMap;
import cn.nextop.thorin.core.holding.domain.entity.HoldingStrategy;
import cn.nextop.thorin.core.holding.domain.pipeline.request.impl.DeleteAccountsRequest;
import cn.nextop.thorin.core.holding.domain.pipeline.request.impl.ModifyStrategyRequest;
import cn.nextop.thorin.core.holding.domain.pipeline.response.impl.DeleteAccountsResponse;
import cn.nextop.thorin.core.holding.domain.pipeline.response.impl.ModifyStrategyResponse;
import cn.nextop.thorin.core.holding.domain.pipeline.response.impl.result.DeleteAccountsResult;
import cn.nextop.thorin.core.holding.orm.po.AccountsPo;
import cn.nextop.thorin.test.core.assertj.hard.ThorinAssertions;

@Service
public class AccountsPoService extends AbstractHoldingService<AccountsPo> {

	@Override
	protected Integer getId(AccountsPo po) {
		return po.getId();
	}

	@Override
	protected AccountsPo doGet(Integer id) {
		return admin.getAccounts(id);
	}

	@Override
	protected AccountsPo doUpdate(AccountsPo input) {
		ModifyStrategyRequest request = new ModifyStrategyRequest(input.getCompanyId());
		request.setAccounts(Lists.newArrayList(input));
		ModifyStrategyResponse response = admin.modify(request);
		ThorinAssertions.assertThat(response).isSuccessful();
		return Iterators.getOnlyElement(response.accounts());
	}

	public void delete(AccountsPo input) {
		Assert.notNull(input);
		// 
		DeleteAccountsRequest deleteAccountsRequest = new DeleteAccountsRequest(input.getCompanyId());
		deleteAccountsRequest.setAccounts(input);
		DeleteAccountsResponse deleteBookResponse = admin.delete(deleteAccountsRequest);
		ThorinAssertions.assertThat(deleteBookResponse).isSuccessful();
	}

	public void deleteRejected(AccountsPo accountsPo) {
		Assert.notNull(accountsPo);
		// 
		DeleteAccountsRequest request = new DeleteAccountsRequest(accountsPo.getCompanyId());
		request.setAccounts(accountsPo);
		DeleteAccountsResponse response = admin.delete(request);
		assertThat(response.getResult()).isEqualTo(DeleteAccountsResult.REJECTED);
	}
	
	public void deleteAll(byte coId) {
		HoldingStrategy strategy = admin.getStrategy(coId);
		IntHashMap<AccountsPo> map = strategy.getAccounts();
		// 
		for (AccountsPo input : map.values()) {
			this.delete(input);
		}
	}

	public AccountsPo get(byte coId, String abbr) {
		HoldingStrategy strategy = admin.getStrategy(coId);
		IntHashMap<AccountsPo> map = strategy.getAccounts();
		// 
		Collection<AccountsPo> collection = Collections2.filter(map.values(), t -> Objects.isEquals(t.getAbbreviation(), abbr));
		if (CollectionUtils.isEmpty(collection)) {
			return null;
		} else {
			return Iterables.getOnlyElement(collection);
		}
	}

}
