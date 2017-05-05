package cn.nextop.thorin.test.vo.pricer;

import cn.nextop.thorin.common.util.datetime.TradingDate;
import cn.nextop.thorin.core.common.glossary.DealingExecuteResult;
import cn.nextop.thorin.core.common.glossary.DealingExecuteType;

public class SearchDealingExecutionVo {

	private short lpId;
	private short symbolId;
	private DealingExecuteType dealingExecuteType;
	private DealingExecuteResult dealingExecuteResult;
	private TradingDate tradingDate;

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SearchDealingExecutionVo [lpId=");
		builder.append(lpId);
		builder.append(", symbolId=");
		builder.append(symbolId);
		builder.append(", dealingExecuteType=");
		builder.append(dealingExecuteType);
		builder.append(", dealingExecuteResult=");
		builder.append(dealingExecuteResult);
		builder.append(", tradingDate=");
		builder.append(tradingDate);
		builder.append("]");
		return builder.toString();
	}

	public short getLpId() {
		return lpId;
	}

	public void setLpId(short lpId) {
		this.lpId = lpId;
	}

	public short getSymbolId() {
		return symbolId;
	}

	public void setSymbolId(short symbolId) {
		this.symbolId = symbolId;
	}

	public DealingExecuteType getDealingExecuteType() {
		return dealingExecuteType;
	}

	public void setDealingExecuteType(DealingExecuteType dealingExecuteType) {
		this.dealingExecuteType = dealingExecuteType;
	}

	public DealingExecuteResult getDealingExecuteResult() {
		return dealingExecuteResult;
	}

	public void setDealingExecuteResult(DealingExecuteResult dealingExecuteResult) {
		this.dealingExecuteResult = dealingExecuteResult;
	}

	public TradingDate getTradingDate() {
		return tradingDate;
	}

	public void setTradingDate(TradingDate tradingDate) {
		this.tradingDate = tradingDate;
	}

}
