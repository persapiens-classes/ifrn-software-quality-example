package org.persapiens.account.restclient;

import org.junit.jupiter.api.Test;
import org.persapiens.account.AccountApplication;
import org.persapiens.account.common.CategoryConstants;
import org.persapiens.account.dto.DebitAccountDTO;

import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = AccountApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DebitAccountRestClientIT extends RestClientIT {

	@Test
	void insertOne() {
		String description = "Uber";
		String categoryDescription = category(CategoryConstants.TRANSPORT).getDescription();

		DebitAccountDTO debitAccount = DebitAccountDTO.builder()
			.description(description)
			.category(categoryDescription)
			.build();

		// verify insert operation
		assertThat(debitAccountRestClient().insert(debitAccount)).isNotNull();

		// verify findByDescription operation
		assertThat(debitAccountRestClient().findByDescription(description).get().getDescription())
			.isEqualTo(debitAccount.getDescription());

		// verify findAll operation
		assertThat(debitAccountRestClient().findAll()).isNotEmpty();
	}

	@Test
	void updateOne() {
		DebitAccountDTO debitAccount = debitAccount("Inserted debitAccount",
				category(CategoryConstants.TRANSPORT).getDescription());

		String originalDescription = debitAccount.getDescription();
		debitAccount.setDescription("Updated debitAccount");

		debitAccountRestClient().update(originalDescription, debitAccount);

		// verify update operation
		assertThat(debitAccountRestClient().findByDescription(originalDescription)).isEmpty();

		// verify update operation
		assertThat(debitAccountRestClient().findByDescription(debitAccount.getDescription()).get().getDescription())
			.isEqualTo(debitAccount.getDescription());
	}

	@Test
	void deleteOne() {
		// create test environment
		String description = "Fantastic debitAccount";

		debitAccountRestClient().insert(DebitAccountDTO.builder()
			.description(description)
			.category(category(CategoryConstants.TRANSPORT).getDescription())
			.build());
		assertThat(debitAccountRestClient().findByDescription(description).get().getDescription())
			.isEqualTo(description);

		// execute deleteByName operation
		debitAccountRestClient().deleteByDescription(description);
		// verify the results
		assertThat(debitAccountRestClient().findByDescription(description)).isEmpty();
	}

}
