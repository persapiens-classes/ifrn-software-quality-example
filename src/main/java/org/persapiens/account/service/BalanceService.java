package org.persapiens.account.service;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import org.persapiens.account.domain.EquityAccount;
import org.persapiens.account.domain.Owner;

import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class BalanceService {

	private final EntryService entryService;

	private final OwnerEquityAccountInitialValueService ownerEquityAccountInitialValueService;

	public BigDecimal balance(Owner owner, EquityAccount equityAccount) {
		// get initial value of owner and equity account
		BigDecimal result = this.ownerEquityAccountInitialValueService.findByOwnerAndEquityAccount(owner, equityAccount)
			.get()
			.getValue();

		// sum credits of owner and equity account
		BigDecimal credits = this.entryService.creditSum(owner, equityAccount);

		// subtract debits of owner and equity account
		BigDecimal debits = this.entryService.debitSum(owner, equityAccount);

		return result.add(credits).subtract(debits);
	}

}
