package com.technicaltestinc.pps.controller.kafka;

import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.technicaltestinc.pps.entity.ErrorTypeEnum;
import com.technicaltestinc.pps.entity.Payment;
import com.technicaltestinc.pps.entity.TransactionCurrentState;
import com.technicaltestinc.pps.entity.TransactionError;
import com.technicaltestinc.pps.entity.TransactionStatusEnum;
import com.technicaltestinc.pps.exception.DatabaseException;
import com.technicaltestinc.pps.exception.ErrorLogException;
import com.technicaltestinc.pps.exception.GenericException;
import com.technicaltestinc.pps.exception.NetworkException;
import com.technicaltestinc.pps.exception.PaymentProcessingSystemException;
import com.technicaltestinc.pps.service.LogErrorService;
import com.technicaltestinc.pps.service.PersistTransactionService;
import com.technicaltestinc.pps.service.ThirdPartyBankService;

@Controller
//MAIN CONTROLLER
public class PaymentProcessorController {
	
	private static final Logger logger;
	private static final HashMap<String, TransactionStatusEnum> transactionDatagrid;
	private static final ObjectMapper mapper;
	
	static {
		transactionDatagrid = new HashMap<String, TransactionStatusEnum>();	
		logger = LoggerFactory.getLogger(PaymentProcessorController.class);
		mapper = new ObjectMapper();
        mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
	}
	
	@Autowired
	ThirdPartyBankService thirdPartyBankService;
	
	@Autowired
	PersistTransactionService persistTransactionService;
	
	@Autowired
	LogErrorService logErrorService;
	
	private TransactionCurrentState transactionState;
	
	public PaymentProcessorController() {
		super();
	}
	
	@PostConstruct
    private void postConstruct() {
		if (transactionDatagrid != null && transactionDatagrid.size() == 0) {
			//TODO check for stored pending transactions due to a previous application shutdown
		}
	}
	
	//PROCESS TRANSACTION AND PAYMENT
	public void processPaymentMessage(String message) throws PaymentProcessingSystemException{
	   
        try {
	        try {
	        	//RETRIEVE MESSAGE
				Payment payment = readPaymentMessage(message);
				logger.info("Received Online Message: {}" , payment);
				
				//START PROCESSING TRANSACTION
				transactionState = new TransactionCurrentState(payment.getPaymentId());
				updateTransactionInDatagrid(TransactionStatusEnum.PROCESSING);
				logger.info("  - {}" , transactionState);
				
				thirdPartyBankService.sendPayment(payment);
				//TODO: MANAGE HERE BANK ERROR RETURN CODE
						
				persistTransactionService.persistTransaction(transactionState, payment);
				updateTransactionInDatagrid(TransactionStatusEnum.VALIDATED);
				logger.info("  - {}" , transactionState);
				
			} catch (IOException e) {
				GenericException gEx = new GenericException(e);
				TransactionError error = buildError(gEx);
				logErrorService.sendLogError(error);
				throw new PaymentProcessingSystemException(gEx);
				
			} catch (NetworkException nE) {
				TransactionError error = buildError(nE);
				logErrorService.sendLogError(error);
				throw new PaymentProcessingSystemException(nE);
				
			} catch (DatabaseException dE) {
				TransactionError error = buildError(dE);
				logErrorService.sendLogError(error);
				throw new PaymentProcessingSystemException(dE);
			}

        } catch(ErrorLogException lE ) {
        	logger.error(lE.getErrorType() +": " + lE.getDescription());
        	throw new PaymentProcessingSystemException(lE);
        }finally {
        	removeTransactionInDatagrid();
        }
    
	}
	
	public void recoverMessage(String message) {
		//TODO RECOVER HERE A TRANSACTION NOT PROCESSED, CHECK IF payment_id is in transactionDatagrid
	}
	
	public Set<String> unprocessedTransactionList(String message) {
		//TODO RECOVER HERE A TRANSACTION LIST NOT PROCESSED
		return transactionDatagrid.keySet();
	}

	private Payment readPaymentMessage(String message) throws JsonMappingException, JsonProcessingException {
		Payment payment = mapper.readValue(message, Payment.class);
		return payment ;
	}

	private void updateTransactionInDatagrid(TransactionStatusEnum e) {
		if (transactionState != null) {
			transactionState.setTransactionState(e);
			transactionDatagrid.put(transactionState.getCurrentTransactionId(), transactionState.getTransactionState());
		}
	}
	
	private void removeTransactionInDatagrid() {
		if (transactionState != null && transactionState.getTransactionState() != TransactionStatusEnum.PROCESSING) {
			transactionDatagrid.remove(transactionState.getCurrentTransactionId());
		}
	}
	
	private TransactionError buildError(GenericException e) {
		transactionState.setErrorType(e.getErrorType());
		transactionState.setTransactionState(TransactionStatusEnum.REFUSED);
		TransactionError error = new TransactionError(
				transactionState.getCurrentTransactionId(),
				e.getErrorType(),
				e.getDescription()
				);
		logger.info(" Error - {}" , error);
		return error;
	}

}
