package cn.nextop.thorin.test.emulator.bo;

import java.util.List;

public class GwQuoteIncr extends GwQuote {
	private List<GwGroup> groups;

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("GwQuoteIncr [groups=");
		builder.append(groups);
		builder.append("]");
		return builder.toString();
	}

	public List<GwGroup> getGroups() {
		return groups;
	}

	public void setGroups(List<GwGroup> groups) {
		this.groups = groups;
	}

}
