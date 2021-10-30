package com.technicaltestinc.pps.service;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.technicaltestinc.pps.entity.Payment;
import com.technicaltestinc.pps.exception.NetworkException;

import io.swagger.client.ApiClient;
import io.swagger.client.ApiException;
import io.swagger.client.api.PaymentApi;

@Service
//BANK PAYMENTMENT SERVICE 
public class ThirdPartyBankService {

	private static final Logger logger = LoggerFactory.getLogger(ThirdPartyBankService.class);

	PaymentApi client;
	
	@Value(value = "${application.delivery.rest.basepath}")
    private String restBasepath;
	
	@PostConstruct
	private void postContructorInit(){
		client = new PaymentApi();
		ApiClient apiClient = new ApiClient();
		
		//TODO set client configuration here
		apiClient.setBasePath(restBasepath);
		client.setApiClient(apiClient);
	}
	
	public void sendPayment(Payment payment) throws NetworkException {
		io.swagger.client.model.Payment paymentRequest = new io.swagger.client.model.Payment();
		paymentRequest.setAccountId(payment.getAccountId().intValue());
		paymentRequest.setAmount(payment.getAmount());
		paymentRequest.setCreditCard(payment.getCreditCard());
		paymentRequest.setPaymentId(payment.getPaymentId());
		paymentRequest.setPaymentId(payment.getPaymentId());
		try {
			client.sendPaymentEntity(paymentRequest);
			logger.info("  - Payment sent");
		} catch (ApiException e) {
			throw new NetworkException(e);
		}
		
	}

}
