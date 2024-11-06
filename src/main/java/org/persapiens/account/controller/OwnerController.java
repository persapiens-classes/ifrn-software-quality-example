package org.persapiens.account.controller;

import org.persapiens.account.dto.OwnerDTO;
import org.persapiens.account.domain.Owner;
import org.persapiens.account.service.OwnerService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/owner")
public class OwnerController extends CrudController<OwnerDTO, Owner, Long> {

	@Autowired
	private OwnerService ownerService;

	@Override
	protected Owner toEntity(OwnerDTO dto) {
		return Owner.builder().name(dto.getName()).build();
	}

	@Override
	protected OwnerDTO toDTO(Owner entity) {
		return OwnerDTO.builder().name(entity.getName()).build();
	}

	@GetMapping("/findByName")
	public Optional<OwnerDTO> findByName(@RequestParam String name) {
		return toDTOOptional(ownerService.findByName(name));
	}

	@DeleteMapping("/deleteByName")
	public void deleteByName(@RequestParam String name) {
		ownerService.deleteByName(name);
	}

}