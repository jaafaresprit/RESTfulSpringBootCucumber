package com.jaa.ham.entities;

public enum EnumCondition {
	SDE("sans date d''effet"),ADE("avec date d''effet");

	String value;
	private EnumCondition(String value) {
		this.value =value;
	}
	public String getValue() {
		return value;
	}
}
