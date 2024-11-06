package org.persapiens.account.persistence;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.persapiens.account.AccountApplication;
import org.persapiens.account.domain.Category;
import org.persapiens.account.domain.DebitAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.persapiens.account.common.CategoryConstants.TRANSPORT;
import static org.persapiens.account.common.DebitAccountConstants.GASOLINE;


@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = AccountApplication.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class DebitAccountRepositoryIT {

	@Autowired
	private DebitAccountRepository debitAccountRepository;

	@Autowired
	private DebitAccountFactory debitAccountFactory;

	@Test
	public void repositoryNotNull() {
		assertThat(this.debitAccountRepository).isNotNull();
	}

	@Test
	public void findOneByExample() {
		// create test environment
		DebitAccount debitAccount = this.debitAccountFactory.gasoline();

		DebitAccount debitAccountExemplo = DebitAccount.builder().description(GASOLINE)
			.category(Category.builder().description(TRANSPORT).build())
			.build();

		// execute the operation to be tested
		// verify the results
		assertThat(this.debitAccountRepository.findOne(Example.of(debitAccountExemplo)).get())
			.isEqualTo(debitAccount);
	}

}