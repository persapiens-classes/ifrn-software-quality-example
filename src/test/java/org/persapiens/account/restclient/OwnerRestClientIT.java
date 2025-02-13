package org.persapiens.account.restclient;

import org.junit.jupiter.api.Test;
import org.persapiens.account.AccountApplication;
import org.persapiens.account.dto.OwnerDTO;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@SpringBootTest(classes = AccountApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OwnerRestClientIT extends RestClientIT {

	@Test
	void insertOne() {
		String name = "Free income";

		OwnerDTO owner = OwnerDTO.builder().name(name).build();

		// verify insert operation
		assertThat(ownerRestClient().insert(owner)).isNotNull();

		// verify findByName operation
		assertThat(ownerRestClient().findByName(name).get().getName()).isEqualTo(owner.getName());

		// verify findAll operation
		assertThat(ownerRestClient().findAll()).isNotEmpty();
	}

	private void insertInvalid(String name, HttpStatus httpStatus) {
		OwnerDTO ownerDto = OwnerDTO.builder().name(name).build();

		// verify insert operation
		// verify status code error
		assertThatExceptionOfType(HttpClientErrorException.class).isThrownBy(() -> ownerRestClient().insert(ownerDto))
			.satisfies((ex) -> assertThat(ex.getStatusCode()).isEqualTo(httpStatus));
	}

	@Test
	void insertEmpty() {
		// verify insert operation
		// verify status code error
		insertInvalid("", HttpStatus.BAD_REQUEST);
	}

	@Test
	void insertSameOwnerTwice() {
		String name = "repeated owner";

		OwnerDTO ownerDto = OwnerDTO.builder().name(name).build();

		ownerRestClient().insert(ownerDto);

		// verify insert operation
		// verify status code error
		insertInvalid(name, HttpStatus.CONFLICT);
	}

	@Test
	void updateOne() {
		OwnerDTO owner = owner("Inserted owner");

		String originalName = owner.getName();
		owner.setName("Updated owner");

		ownerRestClient().update(originalName, owner);

		// verify update operation
		assertThat(ownerRestClient().findByName(originalName)).isEmpty();

		// verify update operation
		assertThat(ownerRestClient().findByName(owner.getName()).get().getName()).isEqualTo(owner.getName());
	}

	@Test
	void deleteOne() {
		// create test environment
		String name = "Fantastic owner";
		ownerRestClient().insert(OwnerDTO.builder().name(name).build());
		assertThat(ownerRestClient().findByName(name).get().getName()).isEqualTo(name);

		// execute deleteByName operation
		ownerRestClient().deleteByName(name);
		// verify the results
		assertThat(ownerRestClient().findByName(name)).isEmpty();
	}

	private void deleteInvalid(String name, HttpStatus httpStatus) {
		// verify delete operation
		// verify status code error
		assertThatExceptionOfType(HttpClientErrorException.class).isThrownBy(() -> ownerRestClient().deleteByName(name))
			.satisfies((ex) -> assertThat(ex.getStatusCode()).isEqualTo(httpStatus));
	}

	@Test
	void deleteInvalid() {
		String name = "Invalid name";
		deleteInvalid("", HttpStatus.FORBIDDEN);
		deleteInvalid(name, HttpStatus.NOT_FOUND);
	}

}
