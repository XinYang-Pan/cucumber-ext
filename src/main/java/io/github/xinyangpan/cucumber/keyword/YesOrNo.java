package io.github.xinyangpan.cucumber.keyword;

import com.google.common.base.Preconditions;

public class YesOrNo {
	public static final YesOrNo YES = new YesOrNo(true);
	public static final YesOrNo NO = new YesOrNo(false);
	private final boolean yes;

	private YesOrNo(boolean yes) {
		this.yes = yes;
	}

	public YesOrNo(String yesOrNo) {
		Preconditions.checkNotNull(yesOrNo);
		switch (yesOrNo.trim().toLowerCase()) {
		case "y":
		case "yes":
			yes = true;
			break;
		case "n":
		case "no":
			yes = false;
			break;
		default:
			throw new IllegalArgumentException();
		}
	}

	public boolean isYes() {
		return yes;
	}

	public boolean isNo() {
		return !this.isYes();
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("YesOrNo [yes=");
		builder.append(yes);
		builder.append("]");
		return builder.toString();
	}

}
