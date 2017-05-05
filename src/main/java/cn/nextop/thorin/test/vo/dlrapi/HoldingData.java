package cn.nextop.thorin.test.vo.dlrapi;

import static com.google.common.collect.Lists.transform;

import java.util.List;

import cn.nextop.thorin.api.dealer.proto.HoldingServiceProtoV1.HoldingEvent;
import cn.nextop.thorin.api.dealer.service.holding.v1.model.AccountsConfigV1Factory;
import cn.nextop.thorin.api.dealer.service.holding.v1.model.AccountsV1Factory;
import cn.nextop.thorin.api.dealer.service.holding.v1.model.AllocationAccountsConfigV1Factory;
import cn.nextop.thorin.api.dealer.service.holding.v1.model.AllocationBookConfigV1Factory;
import cn.nextop.thorin.api.dealer.service.holding.v1.model.AllocationBrokerConfigV1Factory;
import cn.nextop.thorin.api.dealer.service.holding.v1.model.AllocationSymbolConfigV1Factory;
import cn.nextop.thorin.api.dealer.service.holding.v1.model.AllocationV1Factory;
import cn.nextop.thorin.api.dealer.service.holding.v1.model.BookQuoteConfigV1Factory;
import cn.nextop.thorin.api.dealer.service.holding.v1.model.BookV1Factory;
import cn.nextop.thorin.api.dealer.service.holding.v1.model.BooksConfigV1Factory;
import cn.nextop.thorin.api.dealer.service.holding.v1.model.BooksV1Factory;
import cn.nextop.thorin.api.dealer.service.holding.v1.model.HoldingSummaryV1Factory;
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
import cn.nextop.thorin.core.holding.orm.po.HoldingSummaryPo;

public class HoldingData {
	private List<BookPo> bookPos;
	private List<BooksPo> booksPos;
	private List<AccountsPo> accountsPos;
	private List<AllocationPo> allocationPos;
	private List<HoldingSummaryPo> holdingStrategyPos;

	private List<BooksConfigPo> booksConfigPos;
	private List<AccountsConfigPo> accountsConfigPos;
	private List<BookQuoteConfigPo> bookQuoteConfigPos;
	
	private List<AllocationBookConfigPo> allocationBookConfigPos;
	private List<AllocationBrokerConfigPo> allocationBrokerConfigPos;
	private List<AllocationSymbolConfigPo> allocationSymbolConfigPos;
	private List<AllocationAccountsConfigPo> allocationAccountsConfigPos;
	
	public static HoldingData from(HoldingEvent holdingEvent) {
		HoldingData holdingData = new HoldingData();
		// 
		holdingData.setBookPos(transform(holdingEvent.getBookList(), BookV1Factory::toBook));
		holdingData.setBooksPos(transform(holdingEvent.getBooksList(), BooksV1Factory::toBooks));
		holdingData.setAccountsPos(transform(holdingEvent.getAccountsList(), AccountsV1Factory::toAccounts));
		holdingData.setAllocationPos(transform(holdingEvent.getAllocationList(), AllocationV1Factory::toAllocation));
		holdingData.setHoldingStrategyPos(transform(holdingEvent.getSummariesList(), HoldingSummaryV1Factory::toSummary));

		holdingData.setBooksConfigPos(transform(holdingEvent.getBooksConfigList(), BooksConfigV1Factory::toBooksConfig));
		holdingData.setAccountsConfigPos(transform(holdingEvent.getAccountsConfigList(), AccountsConfigV1Factory::toAccountsConfig));
		holdingData.setBookQuoteConfigPos(transform(holdingEvent.getBookQuoteConfigList(), BookQuoteConfigV1Factory::toBookQuoteConfig));
		
		holdingData.setAllocationBookConfigPos(transform(holdingEvent.getAllocationBookConfigList(), AllocationBookConfigV1Factory::toAllocationBookConfig));
		holdingData.setAllocationBrokerConfigPos(transform(holdingEvent.getAllocationBrokerConfigList(), AllocationBrokerConfigV1Factory::toAllocationBrokerConfig));
		holdingData.setAllocationSymbolConfigPos(transform(holdingEvent.getAllocationSymbolConfigList(), AllocationSymbolConfigV1Factory::toAllocationSymbolConfig));
		holdingData.setAllocationAccountsConfigPos(transform(holdingEvent.getAllocationAccountsConfigList(), AllocationAccountsConfigV1Factory::toAllocationAccountsConfig));
		return holdingData;
	}

	// -----------------------------
	// ----- Get Set ToString HashCode Equals
	// -----------------------------
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("HoldingData [bookPos=");
		builder.append(bookPos);
		builder.append(", booksPos=");
		builder.append(booksPos);
		builder.append(", accountsPos=");
		builder.append(accountsPos);
		builder.append(", allocationPos=");
		builder.append(allocationPos);
		builder.append(", holdingStrategyPos=");
		builder.append(holdingStrategyPos);
		builder.append(", booksConfigPos=");
		builder.append(booksConfigPos);
		builder.append(", accountsConfigPos=");
		builder.append(accountsConfigPos);
		builder.append(", bookQuoteConfigPos=");
		builder.append(bookQuoteConfigPos);
		builder.append(", allocationBookConfigPos=");
		builder.append(allocationBookConfigPos);
		builder.append(", allocationBrokerConfigPos=");
		builder.append(allocationBrokerConfigPos);
		builder.append(", allocationSymbolConfigPos=");
		builder.append(allocationSymbolConfigPos);
		builder.append(", allocationAccountsConfigPos=");
		builder.append(allocationAccountsConfigPos);
		builder.append("]");
		return builder.toString();
	}

	public List<BookPo> getBookPos() {
		return bookPos;
	}

	public void setBookPos(List<BookPo> bookPos) {
		this.bookPos = bookPos;
	}

	public List<BooksPo> getBooksPos() {
		return booksPos;
	}

	public void setBooksPos(List<BooksPo> booksPos) {
		this.booksPos = booksPos;
	}

	public List<AccountsPo> getAccountsPos() {
		return accountsPos;
	}

	public void setAccountsPos(List<AccountsPo> accountsPos) {
		this.accountsPos = accountsPos;
	}

	public List<AllocationPo> getAllocationPos() {
		return allocationPos;
	}

	public void setAllocationPos(List<AllocationPo> allocationPos) {
		this.allocationPos = allocationPos;
	}

	public List<HoldingSummaryPo> getHoldingStrategyPos() {
		return holdingStrategyPos;
	}

	public void setHoldingStrategyPos(List<HoldingSummaryPo> holdingStrategyPos) {
		this.holdingStrategyPos = holdingStrategyPos;
	}

	public List<BooksConfigPo> getBooksConfigPos() {
		return booksConfigPos;
	}

	public void setBooksConfigPos(List<BooksConfigPo> booksConfigPos) {
		this.booksConfigPos = booksConfigPos;
	}

	public List<AccountsConfigPo> getAccountsConfigPos() {
		return accountsConfigPos;
	}

	public void setAccountsConfigPos(List<AccountsConfigPo> accountsConfigPos) {
		this.accountsConfigPos = accountsConfigPos;
	}

	public List<BookQuoteConfigPo> getBookQuoteConfigPos() {
		return bookQuoteConfigPos;
	}

	public void setBookQuoteConfigPos(List<BookQuoteConfigPo> bookQuoteConfigPos) {
		this.bookQuoteConfigPos = bookQuoteConfigPos;
	}

	public List<AllocationBookConfigPo> getAllocationBookConfigPos() {
		return allocationBookConfigPos;
	}

	public void setAllocationBookConfigPos(List<AllocationBookConfigPo> allocationBookConfigPos) {
		this.allocationBookConfigPos = allocationBookConfigPos;
	}

	public List<AllocationBrokerConfigPo> getAllocationBrokerConfigPos() {
		return allocationBrokerConfigPos;
	}

	public void setAllocationBrokerConfigPos(List<AllocationBrokerConfigPo> allocationBrokerConfigPos) {
		this.allocationBrokerConfigPos = allocationBrokerConfigPos;
	}

	public List<AllocationSymbolConfigPo> getAllocationSymbolConfigPos() {
		return allocationSymbolConfigPos;
	}

	public void setAllocationSymbolConfigPos(List<AllocationSymbolConfigPo> allocationSymbolConfigPos) {
		this.allocationSymbolConfigPos = allocationSymbolConfigPos;
	}

	public List<AllocationAccountsConfigPo> getAllocationAccountsConfigPos() {
		return allocationAccountsConfigPos;
	}

	public void setAllocationAccountsConfigPos(List<AllocationAccountsConfigPo> allocationAccountsConfigPos) {
		this.allocationAccountsConfigPos = allocationAccountsConfigPos;
	}

}
