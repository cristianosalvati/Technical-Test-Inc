package com.technicaltestinc.pps.service;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.technicaltestinc.pps.entity.TransactionError;
import com.technicaltestinc.pps.exception.ErrorLogException;

import io.swagger.client.ApiClient;
import io.swagger.client.ApiException;
import io.swagger.client.api.LogApi;
import io.swagger.client.model.Log;

@Service
//LOG ERROR SERVICE 
public class LogErrorService {
 
	private static final Logger logger = LoggerFactory.getLogger(LogErrorService.class);
	
	LogApi client;
	
	@Value(value = "${application.delivery.rest.basepath}")
    private String restBasepath;
	
	@PostConstruct
	private void postContructorInit(){
		client = new LogApi();
		ApiClient apiClient = new ApiClient();
		
		//TODO set client configuration here
		apiClient.setBasePath(restBasepath);
		client.setApiClient(apiClient);
	}
	
	public void sendLogError(TransactionError error) throws ErrorLogException {
		try {
			Log logRequest = new Log();
			logRequest.setErrorDescription(error.getDescription());
			logRequest.setErrorType(error.getErrorType().name());
			logRequest.setPaymentId(error.getPaymentId());
			client.createLogEntity(logRequest);
			logger.info("  - Log sent");
		} catch (ApiException e) {
			throw new ErrorLogException(e);
		}
		
	}

}
