package cn.nextop.thorin.test.vo.dealer;

import java.math.BigDecimal;
import java.math.RoundingMode;

import cn.nextop.thorin.common.util.DateTimes;
import cn.nextop.thorin.common.util.Lists;
import cn.nextop.thorin.common.util.datetime.TradingDate;
import cn.nextop.thorin.common.util.type.Pair;
import cn.nextop.thorin.core.common.glossary.Side;
import cn.nextop.thorin.core.dealing.domain.entity.OvernightPosition;
import cn.nextop.thorin.core.dealing.domain.pipeline.request.impl.InputSwapRequest;
import cn.nextop.thorin.test.vo.id.obj.compound.LpSymbolId;

public class LpSwapExecution {
	private short lpId;
	private short symbolId;
	private BigDecimal executeVolume;
	private Side nearSide;
	private BigDecimal nearAmount;
	private BigDecimal farAmount;
	private int nearTday;
	private int farTday;

	public InputSwapRequest toInputSwapRequest(TradingDate tradingDate) {
		OvernightPosition overnightPosition = this.toOvernightPosition(tradingDate);
		InputSwapRequest inputSwapRequest = new InputSwapRequest(overnightPosition);
		inputSwapRequest.setComment(this.comment());
		return inputSwapRequest;
	}
	
	private OvernightPosition toOvernightPosition(TradingDate tradingDate) {
		// Near
		OvernightPosition.Entry near = new OvernightPosition.Entry();
		near.setPrice(this.calNearPrice());
		near.setVolume(this.getExecuteVolume());
		near.setAmount(this.getNearAmount());
		// Far
		OvernightPosition.Entry far = new OvernightPosition.Entry();
		far.setPrice(this.calFarPrice());
		far.setVolume(this.getExecuteVolume());
		far.setAmount(this.getFarAmount());
		// 
		LpSymbolId lpSymbolId = LpSymbolId.toLpSymbolId(this.getLpId(), this.getSymbolId());
		OvernightPosition r = new OvernightPosition();
		r.setOccurDate(tradingDate);
		r.setOccurTime(DateTimes.currentTimeMillis());
		r.setNearValueDate(tradingDate.add(nearTday));
		r.setFarValueDate(tradingDate.add(farTday));
		r.setSide(this.calFarSide());
		r.setLpSymbolId(lpSymbolId.lpSymbolId());
		r.setEntries(Lists.toList(new Pair<>(near, far)));
		return r;
	}

	public Side calFarSide() {
		return nearSide.flip();
	}

	public BigDecimal calNearPrice() {
		return nearAmount.divide(executeVolume, 8, RoundingMode.HALF_UP);
	}

	public BigDecimal calFarPrice() {
		return farAmount.divide(executeVolume, 8, RoundingMode.HALF_UP);
	}

	public String comment() {
		return "cucumber test";
	}

	// -----------------------------
	// ----- Get Set ToString HashCode Equals
	// -----------------------------
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("LpSwapExecution [lpId=");
		builder.append(lpId);
		builder.append(", symbolId=");
		builder.append(symbolId);
		builder.append(", executeVolume=");
		builder.append(executeVolume);
		builder.append(", nearSide=");
		builder.append(nearSide);
		builder.append(", nearAmount=");
		builder.append(nearAmount);
		builder.append(", farAmount=");
		builder.append(farAmount);
		builder.append(", nearTday=");
		builder.append(nearTday);
		builder.append(", farTday=");
		builder.append(farTday);
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

	public BigDecimal getExecuteVolume() {
		return executeVolume;
	}

	public void setExecuteVolume(BigDecimal executeVolume) {
		this.executeVolume = executeVolume;
	}

	public Side getNearSide() {
		return nearSide;
	}

	public void setNearSide(Side nearSide) {
		this.nearSide = nearSide;
	}

	public BigDecimal getNearAmount() {
		return nearAmount;
	}

	public void setNearAmount(BigDecimal nearAmount) {
		this.nearAmount = nearAmount;
	}

	public BigDecimal getFarAmount() {
		return farAmount;
	}

	public void setFarAmount(BigDecimal farAmount) {
		this.farAmount = farAmount;
	}

	public int getNearTday() {
		return nearTday;
	}

	public void setNearTday(int nearTday) {
		this.nearTday = nearTday;
	}

	public int getFarTday() {
		return farTday;
	}

	public void setFarTday(int farTday) {
		this.farTday = farTday;
	}

}
