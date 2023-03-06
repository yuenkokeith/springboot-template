package com.microservices.accountservice;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AccountServiceApplicationTests {

	Logger LOGGER = LoggerFactory.getLogger(AccountServiceApplicationTests.class);

	@Test
	void contextLoads() {
		/*
		JSONArray jsonArray =  new JSONArray();
		jsonArray.put(10);
		jsonArray.put(20);
		jsonArray.put(30);
		LOGGER.info("Output Result: jsonArray {}"  , jsonArray.toString());
		*/

		String formatted = String.format("%07d", 9);
		LOGGER.info("Leading Zero Result: {}"  , formatted);

		formatted = String.format("%07d", 15);
		LOGGER.info("Leading Zero Result: {}"  , formatted);

		formatted = String.format("%07d", 259);
		LOGGER.info("Leading Zero Result: {}"  , formatted);

		formatted = String.format("%07d", 3259);
		LOGGER.info("Leading Zero Result: {}"  , formatted);

		formatted = String.format("%07d", 72593);
		LOGGER.info("Leading Zero Result: {}"  , formatted);

		formatted = String.format("%07d", 515430);
		LOGGER.info("Leading Zero Result: {}"  , formatted);

		formatted = String.format("%07d", 1145430);
		LOGGER.info("Leading Zero Result: {}"  , formatted);



		Integer currentRollingNumber = 8;
		String newMemberId = "SHKP" + String.format("%07d", currentRollingNumber);
		LOGGER.info("Formatted MemberId: {}"  , newMemberId);

		currentRollingNumber = 20;
		newMemberId = "SHKP" + String.format("%07d", currentRollingNumber);
		LOGGER.info("Formatted MemberId: {}"  , newMemberId);

		currentRollingNumber = 301;
		newMemberId = "SHKP" + String.format("%07d", currentRollingNumber);
		LOGGER.info("Formatted MemberId: {}"  , newMemberId);

		currentRollingNumber = 2890;
		newMemberId = "SHKP" + String.format("%07d", currentRollingNumber);
		LOGGER.info("Formatted MemberId: {}"  , newMemberId);

		currentRollingNumber = 64890;
		newMemberId = "SHKP" + String.format("%07d", currentRollingNumber);
		LOGGER.info("Formatted MemberId: {}"  , newMemberId);

		currentRollingNumber = 499621;
		newMemberId = "SHKP" + String.format("%07d", currentRollingNumber);
		LOGGER.info("Formatted MemberId: {}"  , newMemberId);

		currentRollingNumber = 1457017;
		newMemberId = "SHKP" + String.format("%07d", currentRollingNumber);
		LOGGER.info("Formatted MemberId: {}"  , newMemberId);

	}

}
