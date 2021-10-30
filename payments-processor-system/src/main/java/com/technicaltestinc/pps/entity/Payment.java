package com.technicaltestinc.pps.entity;

public class Payment {
	
	private String paymentId;
	private Long accountId;
	private String paymentType;
	private String creditCard;
	private Integer amount;
	private Long delay;
	
	public String getPaymentId() {
		return paymentId;
	}
	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}
	public Long getAccountId() {
		return accountId;
	}
	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}
	public String getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
	public String getCreditCard() {
		return creditCard;
	}
	public void setCreditCard(String creditCard) {
		this.creditCard = creditCard;
	}
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	public Long getDelay() {
		return delay;
	}
	public void setDelay(Long delay) {
		this.delay = delay;
	}
	
	@Override
	public String toString() {
		return "Payment [paymentId=" + paymentId + ", accountId=" + accountId + ", paymentType=" + paymentType
				+ ", creditCard=" + creditCard + ", amount=" + amount + ", delay=" + delay + "]";
	}
	
	
}
