package cn.nextop.thorin.test.vo.pricer;

import java.math.BigDecimal;

import cn.nextop.thorin.core.dealing.orm.po.DealingLadderPo;

public class DealingLadderDelta {
    
    private BigDecimal farVolume;
    private BigDecimal farAmount;
    private BigDecimal nearVolume;
    private BigDecimal nearAmount;
    private BigDecimal spotVolume;
    private BigDecimal spotAmount;
    private BigDecimal forwardVolume;
    private BigDecimal forwardAmount;

	public static DealingLadderDelta delta(DealingLadderPo old, DealingLadderPo now) {
		DealingLadderDelta delta = new DealingLadderDelta();
		if (old == null && now == null) {
			return delta;
		}
		if (old == null) {
			delta.setFarVolume(now.getFarVolume());
			delta.setFarAmount(now.getFarAmount());
			delta.setNearVolume(now.getNearVolume());
			delta.setNearAmount(now.getNearAmount());
			delta.setSpotVolume(now.getSpotVolume());
			delta.setSpotAmount(now.getSpotAmount());
			delta.setForwardVolume(now.getForwardVolume());
			delta.setForwardAmount(now.getForwardAmount());
		} else {
			delta.setFarVolume(now.getFarVolume().subtract(old.getFarVolume()));
			delta.setFarAmount(now.getFarAmount().subtract(old.getFarAmount()));
			delta.setNearVolume(now.getNearVolume().subtract(old.getNearVolume()));
			delta.setNearAmount(now.getNearAmount().subtract(old.getNearAmount()));
			delta.setSpotVolume(now.getSpotVolume().subtract(old.getSpotVolume()));
			delta.setSpotAmount(now.getSpotAmount().subtract(old.getSpotAmount()));
			delta.setForwardVolume(now.getForwardVolume().subtract(old.getForwardVolume()));
			delta.setForwardAmount(now.getForwardAmount().subtract(old.getForwardAmount()));
		}
		return delta;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DealingLadderDelta [farVolume=");
		builder.append(farVolume);
		builder.append(", farAmount=");
		builder.append(farAmount);
		builder.append(", nearVolume=");
		builder.append(nearVolume);
		builder.append(", nearAmount=");
		builder.append(nearAmount);
		builder.append(", spotVolume=");
		builder.append(spotVolume);
		builder.append(", spotAmount=");
		builder.append(spotAmount);
		builder.append(", forwardVolume=");
		builder.append(forwardVolume);
		builder.append(", forwardAmount=");
		builder.append(forwardAmount);
		builder.append("]");
		return builder.toString();
	}

	public BigDecimal getFarVolume() {
		return farVolume;
	}

	public void setFarVolume(BigDecimal farVolume) {
		this.farVolume = farVolume;
	}

	public BigDecimal getFarAmount() {
		return farAmount;
	}

	public void setFarAmount(BigDecimal farAmount) {
		this.farAmount = farAmount;
	}

	public BigDecimal getNearVolume() {
		return nearVolume;
	}

	public void setNearVolume(BigDecimal nearVolume) {
		this.nearVolume = nearVolume;
	}

	public BigDecimal getNearAmount() {
		return nearAmount;
	}

	public void setNearAmount(BigDecimal nearAmount) {
		this.nearAmount = nearAmount;
	}

	public BigDecimal getSpotVolume() {
		return spotVolume;
	}

	public void setSpotVolume(BigDecimal spotVolume) {
		this.spotVolume = spotVolume;
	}

	public BigDecimal getSpotAmount() {
		return spotAmount;
	}

	public void setSpotAmount(BigDecimal spotAmount) {
		this.spotAmount = spotAmount;
	}

	public BigDecimal getForwardVolume() {
		return forwardVolume;
	}

	public void setForwardVolume(BigDecimal forwardVolume) {
		this.forwardVolume = forwardVolume;
	}

	public BigDecimal getForwardAmount() {
		return forwardAmount;
	}

	public void setForwardAmount(BigDecimal forwardAmount) {
		this.forwardAmount = forwardAmount;
	}
	
}
