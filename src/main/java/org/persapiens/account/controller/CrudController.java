package org.persapiens.account.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.persapiens.account.service.CrudService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public abstract class CrudController<N extends Object, D extends Object, T extends Object, I> {

	private CrudService<T, I> crudService;

	protected abstract T toEntity(D dto);

	protected abstract T insertDtoToEntity(N dto);

	protected abstract D toDTO(T entity);

	@Autowired
	public void setService(CrudService<T, I> service) {
		this.crudService = service;
	}

	@PostMapping
	public D insert(@RequestBody N insertDto) {
		T saved = this.crudService.save(insertDtoToEntity(insertDto));
		return toDTO(saved);
	}

	@GetMapping
	public Iterable<D> findAll() {
		List<D> result = new ArrayList<>();
		for (T entity : this.crudService.findAll()) {
			result.add(toDTO(entity));
		}
		return result;
	}

	public Optional<D> toDTOOptional(Optional<T> entity) {
		if (entity.isEmpty()) {
			return Optional.empty();
		}
		else {
			return Optional.of(toDTO(entity.get()));
		}
	}

}
