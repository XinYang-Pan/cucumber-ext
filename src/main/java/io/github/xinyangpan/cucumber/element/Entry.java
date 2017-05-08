package io.github.xinyangpan.cucumber.element;
//package org.blueo.cucumber.element;
//
//import java.util.Map;
//
//class Entry {
//	private final String key;
//	private final String value;
//
//	public Entry(String key, String value) {
//		this.key = key;
//		this.value = value;
//	}
//
//	public Entry(Map.Entry<String, String> mapEntry) {
//		this.key = mapEntry.getKey();
//		String value = mapEntry.getValue();
//		if ("<null>".equals(value)) {
//			this.value = null;
//		} else {
//			this.value = value;
//		}
//	}
//
//	// -----------------------------
//	// ----- Get Set ToString HashCode Equals
//	// -----------------------------
//	@Override
//	public String toString() {
//		StringBuilder builder = new StringBuilder();
//		builder.append("Entry [key=");
//		builder.append(key);
//		builder.append(", value=");
//		builder.append(value);
//		builder.append("]");
//		return builder.toString();
//	}
//
//	public String getKey() {
//		return key;
//	}
//
//	public String getValue() {
//		return value;
//	}
//
//}
