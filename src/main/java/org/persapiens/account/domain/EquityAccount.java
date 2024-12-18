package org.persapiens.account.domain;

import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Singular;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@Entity
@EqualsAndHashCode(callSuper = true, exclude = "ownerEquityAccountInitialValues")
@ToString(callSuper = true, exclude = "ownerEquityAccountInitialValues")
@SuperBuilder
@Getter
@Setter
public class EquityAccount extends Account {

	@OneToMany(mappedBy = "equityAccount")
	@Singular
	private Set<OwnerEquityAccountInitialValue> ownerEquityAccountInitialValues;

}
