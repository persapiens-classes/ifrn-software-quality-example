package org.persapiens.account.controller;

import java.util.Optional;

import lombok.AllArgsConstructor;
import org.persapiens.account.domain.DebitAccount;
import org.persapiens.account.dto.DebitAccountDTO;
import org.persapiens.account.service.CategoryService;
import org.persapiens.account.service.DebitAccountService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/debitAccount")
public class DebitAccountController extends CrudController<DebitAccountDTO, DebitAccount, Long> {

	private DebitAccountService debitAccountService;

	private CategoryService categoryService;

	@Override
	protected DebitAccount toEntity(DebitAccountDTO dto) {
		return DebitAccount.builder()
			.description(dto.getDescription())
			.category(this.categoryService.findByDescription(dto.getCategory().getDescription()).get())
			.build();
	}

	@Override
	protected DebitAccountDTO toDTO(DebitAccount entity) {
		return DebitAccountDTO.builder().description(entity.getDescription()).build();
	}

	@GetMapping("/findByDescription")
	public Optional<DebitAccountDTO> findByDescription(@RequestParam String description) {
		return toDTOOptional(this.debitAccountService.findByDescription(description));
	}

}
