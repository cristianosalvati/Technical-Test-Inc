package com.technicaltestinc.pps.entity;

public enum ErrorTypeEnum {

	DATABASE(9100),
	NETWORK(9200),
	OTHER(9300);
	
	private int id;
	
	ErrorTypeEnum(int i) {
		this.id = i;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
