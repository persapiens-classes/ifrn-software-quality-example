package org.persapiens.account.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.persapiens.account.AccountApplication;
import org.persapiens.account.domain.Entry;
import org.persapiens.account.persistence.CreditAccountFactory;
import org.persapiens.account.persistence.DebitAccountFactory;
import org.persapiens.account.persistence.EquityAccountFactory;
import org.persapiens.account.persistence.OwnerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = AccountApplication.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
class EntryServiceIT {

	@Autowired
	private EntryService entryService;

	@Autowired
	private OwnerFactory ownerFactory;

	@Autowired
	private EquityAccountFactory equityAccountFactory;

	@Autowired
	private CreditAccountFactory creditAccountFactory;

	@Autowired
	private DebitAccountFactory debitAccountFactory;

	@Test
	void repositoryNotNull() {
		assertThat(this.entryService).isNotNull();
	}

	@BeforeEach
	void deletarTodos() {
		this.entryService.deleteAll();
		assertThat(this.entryService.findAll()).isEmpty();
	}

	@Test
	void entryWithInvalidInAccount() {
		Assertions.assertThatThrownBy(() -> {
			Entry entry = Entry.builder()
				.inAccount(this.creditAccountFactory.internship())
				.outAccount(this.equityAccountFactory.savings())
				.value(BigDecimal.TEN)
				.date(LocalDateTime.now())
				.owner(this.ownerFactory.father())
				.build();

			this.entryService.save(entry);
		}).isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	void entryWithInvalidOutAccount() {
		Assertions.assertThatThrownBy(() -> {
			Entry entry = Entry.builder()
				.inAccount(this.equityAccountFactory.savings())
				.outAccount(this.debitAccountFactory.gasoline())
				.value(BigDecimal.ZERO)
				.date(LocalDateTime.now())
				.owner(this.ownerFactory.father())
				.build();

			this.entryService.save(entry);
		}).isInstanceOf(IllegalArgumentException.class);
	}

}
