package cn.nextop.thorin.test.vo.dealer;

import static com.google.common.collect.Iterables.getOnlyElement;

import java.math.BigDecimal;
import java.util.List;

import com.google.common.collect.Iterables;

import cn.nextop.thorin.core.common.glossary.DealingPlaceType;
import cn.nextop.thorin.core.common.glossary.HedgingExecuteResult;
import cn.nextop.thorin.core.dealing.orm.po.DealingOrderPo;

public class LpExecuteDetail {

	public enum MatchStrategy {
		LPID, LPID_VOL, LPID_ROUTING_VOL, ;

		public DealingOrderPo match(LpExecuteDetail lpExecuteDetail, List<DealingOrderPo> dealingOrderPos) {
			switch (this) {
			case LPID:
				return getOnlyElement(Iterables.filter(dealingOrderPos, dealingOrderPo -> lpExecuteDetail.getLpId() == dealingOrderPo.getLpId()));
			case LPID_VOL:
				return getOnlyElement(Iterables.filter(dealingOrderPos, dealingOrderPo -> lpExecuteDetail.getLpId() == dealingOrderPo.getLpId() && lpExecuteDetail.getVolume().compareTo(dealingOrderPo.getOrderVolume()) == 0));
			case LPID_ROUTING_VOL:
				return getOnlyElement(Iterables.filter(dealingOrderPos, dealingOrderPo -> lpExecuteDetail.getLpId() == dealingOrderPo.getLpId() && dealingOrderPo.getPlaceType() == DealingPlaceType.ROUTING && lpExecuteDetail.getVolume().compareTo(dealingOrderPo.getOrderVolume()) == 0));
			default:
				throw new RuntimeException();
			}
		}

	}

	private short lpId;
	private BigDecimal price;
	private BigDecimal volume;
	private BigDecimal amount;
	private int tday = 0;
	private HedgingExecuteResult result;
	private MatchStrategy matchStrategy;

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("LpExecuteDetail [lpId=");
		builder.append(lpId);
		builder.append(", price=");
		builder.append(price);
		builder.append(", volume=");
		builder.append(volume);
		builder.append(", amount=");
		builder.append(amount);
		builder.append(", tday=");
		builder.append(tday);
		builder.append(", result=");
		builder.append(result);
		builder.append(", matchStrategy=");
		builder.append(matchStrategy);
		builder.append("]");
		return builder.toString();
	}

	public short getLpId() {
		return lpId;
	}

	public void setLpId(short lpId) {
		this.lpId = lpId;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getVolume() {
		return volume;
	}

	public void setVolume(BigDecimal volume) {
		this.volume = volume;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public HedgingExecuteResult getResult() {
		return result;
	}

	public void setResult(HedgingExecuteResult result) {
		this.result = result;
	}

	public int getTday() {
		return tday;
	}

	public void setTday(int tday) {
		this.tday = tday;
	}

	public MatchStrategy getMatchStrategy() {
		return matchStrategy;
	}

	public void setMatchStrategy(MatchStrategy matchStrategy) {
		this.matchStrategy = matchStrategy;
	}

}
