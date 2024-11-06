package org.persapiens.account.domain;

import java.util.Set;
import java.util.TreeSet;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.persapiens.account.common.DebitAccountConstants;

import static org.assertj.core.api.Assertions.assertThat;

public class DebitAccountTests {

	@Test
	public void equalsDescriptionAndCategory() {
		assertThat(DebitAccount.builder()
			.description(DebitAccountConstants.GASOLINE)
			.category(Category.builder().description(DebitAccountConstants.TRANSPORT).build())
			.build())
			.isEqualTo(DebitAccount.builder()
				.description(DebitAccountConstants.GASOLINE)
				.category(Category.builder().description(DebitAccountConstants.TRANSPORT).build())
				.build());
	}

	@Test
	public void differentDescriptionAndCategory() {
		assertThat(DebitAccount.builder()
			.description(DebitAccountConstants.GASOLINE)
			.category(Category.builder().description(DebitAccountConstants.TRANSPORT).build())
			.build())
			.isNotEqualTo(DebitAccount.builder()
				.description(DebitAccountConstants.GASOLINE)
				.category(Category.builder().description(DebitAccountConstants.AIRPLANE).build())
				.build());
	}

	@Test
	public void differentDescriptionAndEqualCategory() {
		assertThat(DebitAccount.builder()
			.description(DebitAccountConstants.GASOLINE)
			.category(Category.builder().description(DebitAccountConstants.TRANSPORT).build())
			.build())
			.isNotEqualTo(DebitAccount.builder()
				.description(DebitAccountConstants.BUS)
				.category(Category.builder().description(DebitAccountConstants.TRANSPORT).build())
				.build());
	}

	@Test
	public void equalDescriptionWithoutCategory() {
		Assertions
			.assertThatThrownBy(() -> DebitAccount.builder()
				.description(DebitAccountConstants.GASOLINE)
				.build()
				.equals(DebitAccount.builder().description(DebitAccountConstants.GASOLINE).build()))
			.isInstanceOf(NullPointerException.class);
	}

	@Test
	public void compareTo() {
		Set<DebitAccount> debitAccounts = new TreeSet<>();

		DebitAccount bus = DebitAccount.builder()
			.description(DebitAccountConstants.BUS)
			.category(Category.builder().description(DebitAccountConstants.TRANSPORT).build())
			.build();
		debitAccounts.add(bus);
		DebitAccount airplane = DebitAccount.builder()
			.description(DebitAccountConstants.AIRPLANE)
			.category(Category.builder().description(DebitAccountConstants.TRANSPORT).build())
			.build();
		debitAccounts.add(airplane);
		DebitAccount gasoline = DebitAccount.builder()
			.description(DebitAccountConstants.GASOLINE)
			.category(Category.builder().description(DebitAccountConstants.TRANSPORT).build())
			.build();
		debitAccounts.add(gasoline);

		assertThat(debitAccounts.iterator().next()).isEqualTo(airplane);
	}

}
