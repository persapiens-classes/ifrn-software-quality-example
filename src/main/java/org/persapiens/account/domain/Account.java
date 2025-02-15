package org.persapiens.account.domain;

import java.util.Comparator;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@SuppressFBWarnings({ "CT_CONSTRUCTOR_THROW", "NP_NONNULL_FIELD_NOT_INITIALIZED_IN_CONSTRUCTOR",
		"RCN_REDUNDANT_NULLCHECK_OF_NONNULL_VALUE" })
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@SequenceGenerator(sequenceName = "seq_account", name = "ID_SEQUENCE", allocationSize = 1)
@Entity
@EqualsAndHashCode(of = { "description", "category" })
@ToString
@SuperBuilder
@Getter
@Setter
public class Account implements Comparable<Account> {

	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ID_SEQUENCE")
	@Id
	private Long id;

	@Column(nullable = false, unique = true)
	private String description;

	@ManyToOne
	@JoinColumn(nullable = false, foreignKey = @ForeignKey(name = "fk_account_category"))
	@NonNull
	private Category category;

	@Override
	public int compareTo(Account o) {
		return Comparator.comparing(Account::getDescription).thenComparing(Account::getCategory).compare(this, o);
	}

}
