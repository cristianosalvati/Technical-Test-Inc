package com.technicaltestinc.pps.service;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.technicaltestinc.pps.dao.model.Account;
import com.technicaltestinc.pps.dao.repository.AccountRepository;
import com.technicaltestinc.pps.dao.repository.PaymentRepository;
import com.technicaltestinc.pps.entity.Payment;
import com.technicaltestinc.pps.entity.TransactionCurrentState;
import com.technicaltestinc.pps.exception.DatabaseException;

@Service
//TRANSACTION PERSISTENCE PAYMENTMENT SERVICE 
public class PersistTransactionService {

	@Autowired
	AccountRepository accountRepository;
	
	@Autowired
	PaymentRepository paymentRepository;
	
	public void persistTransaction(TransactionCurrentState transactionState, Payment payment) throws DatabaseException{
		
		Optional<Account> account = accountRepository.findById(payment.getAccountId().intValue());
		if (account != null && account.isPresent()) {
			//STORE TRANSACTION PAYMENT
			com.technicaltestinc.pps.dao.model.Payment paymentEntry = new com.technicaltestinc.pps.dao.model.Payment();
			paymentEntry.setAccountId(payment.getAccountId().intValue());
			paymentEntry.setAmount(payment.getAmount());
			paymentEntry.setCreatedOn(new Date());
			paymentEntry.setCreditCart(payment.getCreditCard());
			paymentEntry.setPaymentId(payment.getPaymentId());
			paymentEntry.setPaymentType(payment.getPaymentType());
			paymentRepository.save(paymentEntry);
			
			//UPDATE ACCOUNT LAST PAYMENT DATE
			account.get().setLastPaymentDate(new Date());
			accountRepository.save(account.get());
		}else {
			//TODO MANAGE ERROR RECOVERING ACCOUNT INFO
		}
		
	}

}
