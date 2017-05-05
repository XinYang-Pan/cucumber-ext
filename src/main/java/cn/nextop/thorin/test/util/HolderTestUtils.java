package cn.nextop.thorin.test.util;

import org.springframework.util.Assert;

import cn.nextop.test.cucumber.Element;
import cn.nextop.thorin.core.common.glossary.SideConfig;
import cn.nextop.thorin.core.common.glossary.TradeTypeConfig;
import cn.nextop.thorin.core.common.glossary.TradingExecuteTypeConfig;
import cn.nextop.thorin.core.holding.orm.po.AccountsConfigPo;
import cn.nextop.thorin.core.holding.orm.po.AccountsPo;
import cn.nextop.thorin.core.holding.orm.po.AllocationAccountsConfigPo;
import cn.nextop.thorin.core.holding.orm.po.AllocationBookConfigPo;
import cn.nextop.thorin.core.holding.orm.po.AllocationBrokerConfigPo;
import cn.nextop.thorin.core.holding.orm.po.AllocationPo;
import cn.nextop.thorin.core.holding.orm.po.AllocationSymbolConfigPo;
import cn.nextop.thorin.core.holding.orm.po.BookPo;
import cn.nextop.thorin.core.holding.orm.po.BookQuoteConfigPo;
import cn.nextop.thorin.core.holding.orm.po.BooksConfigPo;
import cn.nextop.thorin.core.holding.orm.po.BooksPo;

public class HolderTestUtils {

	public static AllocationPo newAllocationPo(Element element) {
		AllocationPo allocationPo = new AllocationPo();
		allocationPo.setExecuteType(new TradingExecuteTypeConfig());
		allocationPo.setSide(new SideConfig());
		allocationPo.setTradeType(new TradeTypeConfig());
		// 
		element.putValuesTo(allocationPo);
		return allocationPo;
	}

	public static BooksConfigPo buildBooksConfigPo(BooksPo booksPo, int bookPoId) {
		Assert.notNull(booksPo);
		BooksConfigPo booksConfigPo = TestUtils.setCommonForInsert(new BooksConfigPo());
		booksConfigPo.setBookId(bookPoId);
		booksConfigPo.setBooksId(booksPo.getId());
		booksConfigPo.setCompanyId(booksPo.getCompanyId());
		booksConfigPo.setDeleted(false);
		return booksConfigPo;
	}

	public static BookQuoteConfigPo buildBookQuoteConfigPo(BookPo bookPo, Short lpId) {
		BookQuoteConfigPo po = TestUtils.setCommonForInsert(new BookQuoteConfigPo());
		po.setBookId(bookPo.getId());
		po.setCompanyId(bookPo.getCompanyId());
		po.setDeleted(false);
		po.setLpId(lpId);
		return po;
	}

	public static AccountsConfigPo buildAccountsConfigPo(AccountsPo accountsPo, AccountsConfigPo accountsConfigPo) {
		accountsConfigPo = TestUtils.setCommonForInsert(accountsConfigPo);
		accountsConfigPo.setAccountsId(accountsPo.getId());
		return accountsConfigPo;
	}

	public static AllocationBookConfigPo buildAllocationBookConfigPo(AllocationPo allocationPo) {
		AllocationBookConfigPo allocationBookConfigPo = TestUtils.setCommonForInsert(new AllocationBookConfigPo());
		allocationBookConfigPo.setAllocationId(allocationPo.getId());
		allocationBookConfigPo.setCompanyId(allocationPo.getCompanyId());
		allocationBookConfigPo.setDeleted(false);
		return allocationBookConfigPo;
	}

	public static AllocationAccountsConfigPo buildAllocationAccountsConfigPo(AllocationPo allocationPo, int accountsId) {
		AllocationAccountsConfigPo allocationAccountsConfigPo = TestUtils.setCommonForInsert(new AllocationAccountsConfigPo());
		allocationAccountsConfigPo.setAllocationId(allocationPo.getId());
		allocationAccountsConfigPo.setCompanyId(allocationPo.getCompanyId());
		allocationAccountsConfigPo.setAccountsId(accountsId);
		allocationAccountsConfigPo.setDeleted(false);
		return allocationAccountsConfigPo;
	}

	public static AllocationBrokerConfigPo buildAllocationBrokerConfigPo(AllocationPo allocationPo, short brokerId) {
		AllocationBrokerConfigPo allocationBrokerConfigPo = TestUtils.setCommonForInsert(new AllocationBrokerConfigPo());
		allocationBrokerConfigPo.setAllocationId(allocationPo.getId());
		allocationBrokerConfigPo.setCompanyId(allocationPo.getCompanyId());
		allocationBrokerConfigPo.setBrokerId(brokerId);
		allocationBrokerConfigPo.setDeleted(false);
		return allocationBrokerConfigPo;
	}

	public static AllocationSymbolConfigPo buildAllocationSymbolConfigPo(AllocationPo allocationPo, short symbolId) {
		AllocationSymbolConfigPo allocationSymbolConfigPo = TestUtils.setCommonForInsert(new AllocationSymbolConfigPo());
		allocationSymbolConfigPo.setAllocationId(allocationPo.getId());
		allocationSymbolConfigPo.setCompanyId(allocationPo.getCompanyId());
		allocationSymbolConfigPo.setSymbolId(symbolId);
		allocationSymbolConfigPo.setDeleted(false);
		return allocationSymbolConfigPo;
	}

}
