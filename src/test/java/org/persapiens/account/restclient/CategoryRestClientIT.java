package org.persapiens.account.restclient;

import org.persapiens.account.AccountApplication;
import org.persapiens.account.dto.CategoryDTO;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = AccountApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CategoryRestClientIT {

	private final String protocol = "http";

	private final String servername = "localhost";

	@Value(value = "${local.server.port}")
	private int port;

	private CategoryRestClient categoryRestClient() {
		return CategoryRestClientFactory.builder()
			.protocol(protocol)
			.servername(servername)
			.port(port)
			.build()
			.categoryRestClient();
	}

	@Test
	public void saveOne() {
		String description = "Free income";

		CategoryDTO category = CategoryDTO.builder().description(description).build();

		// verify save operation
		assertThat(categoryRestClient().save(category)).isNotNull();

		// verify findByDescription operation
		assertThat(categoryRestClient().findByDescription(description).get().getDescription())
			.isEqualTo(category.getDescription());

		// verify findAll operation
		assertThat(categoryRestClient().findAll()).isNotEmpty();
	}

}