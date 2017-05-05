package cn.nextop.thorin.test.vo.pricer;

import java.math.BigDecimal;

// BkPickingConfigPo
public class BkPickingStateVo {
	private String itName;
	private Short priority;
	private Boolean active;
	private BigDecimal weight;

	public BkPickingStateVo(){
		super();
	}

	public BkPickingStateVo(String itName, Short priority, Boolean active, BigDecimal weight) {
		this.itName = itName;
		this.priority = priority;
		this.active = active;
		this.weight = weight;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("BkPickingStateVo [itName=");
		builder.append(itName);
		builder.append(", priority=");
		builder.append(priority);
		builder.append(", active=");
		builder.append(active);
		builder.append(", weight=");
		builder.append(weight);
		builder.append("]");
		return builder.toString();
	}

	public String getItName() {
		return itName;
	}

	public void setItName(String itName) {
		this.itName = itName;
	}

	public Short getPriority() {
		return priority;
	}

	public void setPriority(Short priority) {
		this.priority = priority;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public BigDecimal getWeight() {
		return weight;
	}

	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}

}
