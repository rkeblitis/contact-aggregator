package com.rkeblitis.contact_aggregator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan
@SpringBootApplication
public class ContactAggregatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(ContactAggregatorApplication.class, args);
	}

}
