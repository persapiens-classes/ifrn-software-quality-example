package org.persapiens.account.restclient;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.persapiens.account.AccountApplication;
import org.persapiens.account.common.CategoryConstants;
import org.persapiens.account.common.EquityAccountConstants;
import org.persapiens.account.common.OwnerConstants;
import org.persapiens.account.dto.EquityAccountDTO;
import org.persapiens.account.dto.OwnerDTO;
import org.persapiens.account.dto.TransferDTO;

import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = AccountApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TransferRestClientIT extends RestClientIT {

	private TransferRestClient transferRestClient() {
		return TransferRestClient.builder().restClientHelper(this.<TransferDTO>restClientHelper("")).build();
	}

	@Test
	void transfer50FromCheckingsAuntToInvestimentUncle() {
		OwnerDTO aunt = owner(OwnerConstants.AUNT);
		OwnerDTO uncle = owner(OwnerConstants.UNCLE);

		EquityAccountDTO checkings = equityAccount(EquityAccountConstants.CHECKING,
				category(CategoryConstants.BANK).getDescription());
		EquityAccountDTO investiment = equityAccount(EquityAccountConstants.INVESTIMENT,
				category(CategoryConstants.BANK).getDescription());

		// execute transfer operation
		transferRestClient().transfer(TransferDTO.builder()
			.debitOwner(aunt.getName())
			.creditOwner(uncle.getName())
			.debitAccount(checkings.getDescription())
			.creditAccount(investiment.getDescription())
			.value(new BigDecimal(50))
			.build());

		assertThat(entryRestClient().debitSum(aunt.getName(), checkings.getDescription()))
			.isEqualTo(new BigDecimal(50).setScale(2));

		assertThat(entryRestClient().creditSum(uncle.getName(), investiment.getDescription()))
			.isEqualTo(new BigDecimal(50).setScale(2));
	}

}
