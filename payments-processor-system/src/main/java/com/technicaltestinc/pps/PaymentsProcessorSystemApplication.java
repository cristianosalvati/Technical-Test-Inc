package com.technicaltestinc.pps;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"com.technicaltestinc.pps"})
@EnableJpaRepositories(basePackages = "com.technicaltestinc.pps.dao.repository")
@EntityScan(basePackages = "com.technicaltestinc.pps.dao.model")
public class PaymentsProcessorSystemApplication {

	/*APPLICATION ENTRY POINT - 
	// @see PaymentProcessorController as main controller
	 **/
	public static void main(String[] args) {
		SpringApplication.run(PaymentsProcessorSystemApplication.class, args);
	}

}
