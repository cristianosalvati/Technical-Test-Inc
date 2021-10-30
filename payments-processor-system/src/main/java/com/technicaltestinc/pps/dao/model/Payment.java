package com.technicaltestinc.pps.dao.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


@Entity
@Table(name="payments")
@NamedQuery(name="Payment.findAll", query="SELECT p FROM Payment p")
public class Payment  implements Serializable {

	@Id
	@Column(name="payment_id")
	private String paymentId;
	
	@Column(name="account_id")
	private int accountId;
	
	@Column(name="payment_type")
	private String paymentType;
	
	@Column(name="credit_card")
	private String creditCart;
	
	@Column(name="amount")
	private int amount;

	@Column(name="created_on")
	private Date createdOn;

	public String getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}

	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public String getCreditCart() {
		return creditCart;
	}

	public void setCreditCart(String creditCart) {
		this.creditCart = creditCart;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	@Override
	public String toString() {
		return "Payment [paymentId=" + paymentId + ", accountId=" + accountId + ", paymentType=" + paymentType
				+ ", creditCart=" + creditCart + ", amount=" + amount + ", createdOn=" + createdOn + "]";
	}

	
}
