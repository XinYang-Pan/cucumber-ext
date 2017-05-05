package cn.nextop.thorin.test.emulator.bo.enums;

import cn.nextop.thorin.test.util.TestUtils;
import quickfix.field.MDUpdateAction;

public enum UpdateAction {
	NEW, CHANGE, DELETE;

	public char toQuickFixValue() {
		switch (this) {
		case NEW:
			return MDUpdateAction.NEW;
		case CHANGE:
			return MDUpdateAction.CHANGE;
		case DELETE:
			return MDUpdateAction.DELETE;
		default:
			throw TestUtils.illegal(this);
		}
	}

}
