package com.technicaltestinc.pps.entity;

import java.util.Date;

public class TransactionCurrentState {

	private String currentTransactionId;
	private TransactionStatusEnum transactionState;
	private ErrorTypeEnum errorType;
	private Date createdAt;
	
	public TransactionCurrentState(String currentTransactionId) {
		super();
	    this.setCurrentTransactionId(currentTransactionId);
		this.setTransactionState(TransactionStatusEnum.PROCESSING);
		this. setCreatedAt() ;
		errorType = null;
	}
	
	public boolean isValidated() {
		return checkState();
	}
	
	private boolean checkState() {
		if (transactionState != TransactionStatusEnum.VALIDATED || errorType != null) {
			transactionState = transactionState.REFUSED;
			return false;
		}
		
		return true;
	}

	public String getCurrentTransactionId() {
		return currentTransactionId;
	}

	private void setCurrentTransactionId(String currentTransactionId) {
		this.currentTransactionId = currentTransactionId;
	}

	public TransactionStatusEnum getTransactionState() {
		return transactionState;
	}

	public void setTransactionState(TransactionStatusEnum tsEnum) {
		if (this.transactionState != null && tsEnum.getId() <= this.transactionState.getId())
			this.transactionState = tsEnum;
		else this.transactionState = TransactionStatusEnum.PROCESSING; //OPTIMISTIC POINT OF VIEW, SET TO REFUSED OTHERWISE
	}

	public ErrorTypeEnum getErrorType() {
		return errorType;
	}

	public void setErrorType(ErrorTypeEnum errorType) {
		this.errorType = errorType;
	}

	@Override
	public String toString() {
		return "TransactionCurrentState [currentTransactionId=" + currentTransactionId + ", transactionState="
				+ transactionState + ", errorType=" + errorType + "]";
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	private void setCreatedAt() {
		this.createdAt = new Date();
	}
	
	
}
