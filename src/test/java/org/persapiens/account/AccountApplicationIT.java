package org.persapiens.account;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = AccountApplication.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
class AccountApplicationIT {

	@Test
	void contextLoads() {
		// Smoke test to check if application starts up.
	}

}
