package com.technicaltestinc.pps.entity;

public class TransactionError {

	private String paymentId;
	private ErrorTypeEnum errorType;
	private String description;
	
	public TransactionError(String paymentId, ErrorTypeEnum errorType, String description) {
		super();
		this.paymentId = paymentId;
		this.errorType = errorType;
		this.description = description;
	}

	public String getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}

	public ErrorTypeEnum getErrorType() {
		return errorType;
	}

	public void setErrorType(ErrorTypeEnum errorType) {
		this.errorType = errorType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "Error [paymentId=" + paymentId + ", errorType=" + errorType + ", description=" + description + "]";
	}
	
	
}
