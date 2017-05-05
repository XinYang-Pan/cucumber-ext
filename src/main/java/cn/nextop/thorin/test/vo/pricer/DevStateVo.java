package cn.nextop.thorin.test.vo.pricer;

import java.math.BigDecimal;

// LpDeviationConfigPo
public class DevStateVo {
	private BigDecimal gap1;
	private BigDecimal gap2;
	private Short depth;
	private Boolean active;
	private Boolean intraday;

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("LpDevStateVo [gap1=");
		builder.append(gap1);
		builder.append(", gap2=");
		builder.append(gap2);
		builder.append(", depth=");
		builder.append(depth);
		builder.append(", active=");
		builder.append(active);
		builder.append(", intraday=");
		builder.append(intraday);
		builder.append("]");
		return builder.toString();
	}

	public BigDecimal getGap1() {
		return gap1;
	}

	public void setGap1(BigDecimal gap1) {
		this.gap1 = gap1;
	}

	public BigDecimal getGap2() {
		return gap2;
	}

	public void setGap2(BigDecimal gap2) {
		this.gap2 = gap2;
	}

	public Short getDepth() {
		return depth;
	}

	public void setDepth(Short depth) {
		this.depth = depth;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Boolean getIntraday() {
		return intraday;
	}

	public void setIntraday(Boolean intraday) {
		this.intraday = intraday;
	}

}
