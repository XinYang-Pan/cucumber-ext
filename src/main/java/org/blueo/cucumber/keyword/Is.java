package org.blueo.cucumber.keyword;

// regex (NOT |not |)
public class Is {
	private final boolean affirmative;

	private Is(boolean affirmative) {
		this.affirmative = affirmative;
	}

	public Is(String text) {
		affirmative = toAffirmative(text);
	}

	public static Is fromYesNo(YesOrNo yesOrNo) {
		return new Is(yesOrNo.isYes());
	}

	private boolean toAffirmative(String text) {
		if (text == null) {
			return true;
		} else {
			text = text.trim();
			if (text.equalsIgnoreCase("not")) {
				return false;
			} else {
				return true;
			}
		}
	}

	public boolean affirmative() {
		return affirmative;
	}

	public boolean negative() {
		return !this.affirmative();
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Is [affirmative=");
		builder.append(affirmative);
		builder.append("]");
		return builder.toString();
	}

}
