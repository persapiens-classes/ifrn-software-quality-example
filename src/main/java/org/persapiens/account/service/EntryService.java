package org.persapiens.account.service;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import org.persapiens.account.domain.Entry;
import org.persapiens.account.domain.EquityAccount;
import org.persapiens.account.domain.Owner;
import org.persapiens.account.persistence.EntryRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class EntryService extends CrudService<Entry, Long> {

	private EntryRepository entryRepository;

	@Override
	@Transactional
	public <S extends Entry> S save(S entry) {
		entry.verifyAttributes();

		return super.save(entry);
	}

	public BigDecimal creditSum(Owner owner, EquityAccount equityAccount) {
		return this.entryRepository.creditSum(owner, equityAccount).getValue();
	}

	public BigDecimal debitSum(Owner owner, EquityAccount equityAccount) {
		return this.entryRepository.debitSum(owner, equityAccount).getValue();
	}

}
