package cn.nextop.thorin.test.emulator.bo;

import java.math.BigDecimal;

import cn.nextop.thorin.core.common.glossary.BidAsk;
import cn.nextop.thorin.test.emulator.bo.enums.UpdateAction;
import cn.nextop.thorin.test.util.TestUtils;
import quickfix.Group;
import quickfix.field.MDEntryID;
import quickfix.field.MDEntryPx;
import quickfix.field.MDEntrySize;
import quickfix.field.MDEntryType;
import quickfix.field.MDUpdateAction;
import quickfix.field.NumberOfOrders;
import quickfix.field.Symbol;
import quickfix.fix44.MarketDataIncrementalRefresh;

public class GwGroup {
	private UpdateAction updateAction;
	private BidAsk bidAsk;
	private String entryId;
	private String symbol;

	// No use when delete
	private BigDecimal price;
	private int volumn;

	public Group toGroup() {
		Group group = new MarketDataIncrementalRefresh.NoMDEntries();
		group.setString(MDEntryID.FIELD, entryId);
		TestUtils.run(bidAsk, () -> group.setChar(MDEntryType.FIELD, MDEntryType.BID), () -> group.setChar(MDEntryType.FIELD, MDEntryType.OFFER));
		group.setChar(MDUpdateAction.FIELD, updateAction.toQuickFixValue());
		group.setString(Symbol.FIELD, symbol);
		// No use when delete
		if (updateAction != UpdateAction.DELETE) {
			group.setDecimal(MDEntryPx.FIELD, price);
			group.setInt(MDEntrySize.FIELD, volumn);
		}
		// default
		group.setInt(NumberOfOrders.FIELD, 1);
		return group;
	}

	// -----------------------------
	// ----- Get Set ToString HashCode Equals
	// -----------------------------

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("GwGroupIncr [updateAction=");
		builder.append(updateAction);
		builder.append(", bidAsk=");
		builder.append(bidAsk);
		builder.append(", entryId=");
		builder.append(entryId);
		builder.append(", symbol=");
		builder.append(symbol);
		builder.append(", price=");
		builder.append(price);
		builder.append(", volumn=");
		builder.append(volumn);
		builder.append("]");
		return builder.toString();
	}

	public UpdateAction getUpdateAction() {
		return updateAction;
	}

	public void setUpdateAction(UpdateAction updateAction) {
		this.updateAction = updateAction;
	}

	public BidAsk getBidAsk() {
		return bidAsk;
	}

	public void setBidAsk(BidAsk bidAsk) {
		this.bidAsk = bidAsk;
	}

	public String getEntryId() {
		return entryId;
	}

	public void setEntryId(String entryId) {
		this.entryId = entryId;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public int getVolumn() {
		return volumn;
	}

	public void setVolumn(int volumn) {
		this.volumn = volumn;
	}

}
