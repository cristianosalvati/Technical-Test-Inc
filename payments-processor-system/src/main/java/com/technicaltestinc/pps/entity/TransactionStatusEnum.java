package com.technicaltestinc.pps.entity;

public enum TransactionStatusEnum {
	
	REFUSED(1100),
	VALIDATED(1200),
	PROCESSING(1300);
	
	private int id;
	
	TransactionStatusEnum(int i) {
		this.id = i;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
