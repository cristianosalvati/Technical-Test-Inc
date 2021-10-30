package com.technicaltestinc.pps.kafka;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.technicaltestinc.pps.controller.kafka.PaymentProcessorController;
import com.technicaltestinc.pps.entity.Payment;
import com.technicaltestinc.pps.exception.GenericException;
import com.technicaltestinc.pps.exception.PaymentProcessingSystemException;

@EnableKafka
@Configuration
/*
 * Configurator class for kafka consumer listener both for ONLINE and OFFLINE topic
 **/
public class KafkaConsumerConfig {

	private static final Logger logger = LoggerFactory.getLogger(KafkaConsumerConfig.class);
	
	@Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;
	
	@Value(value = "${spring.kafka.consumer.group-id}")
    private String groupId;
	
	@Autowired
	PaymentProcessorController kafkaPaymentProcessor;

    @Bean
    public ConsumerFactory<String, String> consumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(
          ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, 
          bootstrapAddress);
        props.put(
          ConsumerConfig.GROUP_ID_CONFIG, 
          groupId);
        props.put(
          ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, 
          StringDeserializer.class);
        props.put(
          ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, 
          StringDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(props);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> 
      kafkaListenerContainerFactory() {
   
        ConcurrentKafkaListenerContainerFactory<String, String> factory =
          new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }
    
    @KafkaListener(topics = "online")
    public void listenOnlineMessage(String message) {
    	try {
			kafkaPaymentProcessor.processPaymentMessage(message);
		} catch (PaymentProcessingSystemException e) {
			// TODO Manage the processing error from the online channel recovering the transaction from the PaymentProcessorController 
			kafkaPaymentProcessor.recoverMessage(message);
			e.printStackTrace();
		}
    }
    
    @KafkaListener(topics = "offline")
    public void listenOfflineMessage(String message) {
    	try {
			kafkaPaymentProcessor.processPaymentMessage(message);
		} catch (PaymentProcessingSystemException e) {
			logger.error(e.getMessage());
		}
    }
}