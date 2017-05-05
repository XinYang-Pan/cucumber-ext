package org.blueo.cucumber.util;

import java.util.Arrays;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import com.google.common.base.Preconditions;

class FieldLocator {
	private final String[] prefixes;
	private final String fieldName;

	FieldLocator(String rawName) {
		Preconditions.checkArgument(StringUtils.isNotEmpty(rawName));
		//
		String[] names = rawName.split("\\.");
		fieldName = names[names.length - 1];
		if (names.length > 1) {
			prefixes = ArrayUtils.subarray(names, 0, names.length - 1);
		} else {
			prefixes = new String[0];
		}
	}

	public boolean hasPrefixes() {
		if (ArrayUtils.getLength(prefixes) == 0) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("FieldLocator [prefixes=");
		builder.append(Arrays.toString(prefixes));
		builder.append(", fieldName=");
		builder.append(fieldName);
		builder.append("]");
		return builder.toString();
	}

	public String[] getPrefixes() {
		return prefixes;
	}

	public String getFieldName() {
		return fieldName;
	}

}
