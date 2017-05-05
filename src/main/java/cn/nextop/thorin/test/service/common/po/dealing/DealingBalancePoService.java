package cn.nextop.thorin.test.service.common.po.dealing;

import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Converter;

import cn.nextop.thorin.common.util.collection.page.Page;
import cn.nextop.thorin.common.util.datetime.TradingDate;
import cn.nextop.thorin.core.common.glossary.SymbolCode;
import cn.nextop.thorin.core.dealing.DealingPositionAdministrator;
import cn.nextop.thorin.core.dealing.domain.DealingGenerator;
import cn.nextop.thorin.core.dealing.orm.po.DealingBalancePo;
import cn.nextop.thorin.core.dealing.orm.query.DealingBalanceQuery;
import cn.nextop.thorin.test.service.common.po.IdCalculableService;
import cn.nextop.thorin.test.vo.id.has.derivative.HasBalanceId;
import cn.nextop.thorin.test.vo.id.obj.compound.LpId;
import cn.nextop.thorin.test.vo.id.obj.derivative.BalanceId;

@Service
public class DealingBalancePoService extends IdCalculableService<Long, DealingBalancePo, HasBalanceId> {

	@Autowired
	private DealingPositionAdministrator dealingPositionAdministrator;

	@PostConstruct
	public void init() {
		getNullable = true;
	}

	@Override
	protected Long getId(DealingBalancePo po) {
		return po.getId();
	}

	@Override
	protected Converter<Long, HasBalanceId> getConverter() {
		return new Converter<Long, HasBalanceId>() {

			@Override
			protected HasBalanceId doForward(Long id) {
				TradingDate tradingDate = DealingGenerator.getTradingDateByBalanceId(id);
				LpId lpId = LpId.toLpId(DealingGenerator.getLpIdByBalanceId(id));
				SymbolCode currency = DealingGenerator.getCurrencyByBalanceId(id);
				return BalanceId.toBalanceId(tradingDate, lpId, currency);
			}

			@Override
			protected Long doBackward(HasBalanceId b) {
				return b.balanceId();
			}
		};
	}

	@Override
	protected DealingBalancePo doGetByUniqueKey(HasBalanceId balanceId) {
		DealingBalanceQuery query = new DealingBalanceQuery();
		query.setLpId(balanceId.lpId());
		query.setCompanyId(balanceId.coId());
		query.setTradingDate(balanceId.tradingDate());
		// 
		Page<DealingBalancePo> paginateBalances = dealingPositionAdministrator.paginateBalances(query);
		for (DealingBalancePo dealingBalancePo : paginateBalances.getRecords()) {
			if (dealingBalancePo.getCurrency().getCurrency().equals(balanceId.currency())) {
				return dealingBalancePo;
			}
		}
		return null;
	}

	@Override
	protected Map<Long, DealingBalancePo> update(Map<Long, DealingBalancePo> valueMap) {
		throw new UnsupportedOperationException();
	}

}
