package org.persapiens.account.service;

import org.junit.jupiter.api.Test;
import org.persapiens.account.AccountApplication;
import org.persapiens.account.dto.CategoryDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = AccountApplication.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
class CategoryServiceIT {

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private CategoryDTOFactory categoryDTOFactory;

	@Test
	void repositoryNotNull() {
		assertThat(this.categoryService).isNotNull();
	}

	@Test
	void saveOne() {
		// create test environment
		CategoryDTO categoryDTO = this.categoryDTOFactory.bank();

		// verify the results
		assertThat(this.categoryService.findByDescription(categoryDTO.getDescription()).get()).isEqualTo(categoryDTO);
	}

	@Test
	void deleteOne() {
		// create test environment
		CategoryDTO categoryDTO = this.categoryDTOFactory.categoryDTO("UNIQUE CATEGORY");

		// execute the operation to be tested
		this.categoryService.deleteByDescription(categoryDTO.getDescription());

		// verify the results
		assertThat(this.categoryService.findByDescription(categoryDTO.getDescription()).isPresent()).isFalse();
	}

}
