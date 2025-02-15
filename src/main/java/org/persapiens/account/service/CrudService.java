package org.persapiens.account.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public abstract class CrudService<I extends Object, U extends Object, F extends Object, B extends Object, E extends Object, K extends Object> {

	private CrudRepository<E, K> repository;

	protected abstract F toDTO(E entity);

	protected abstract E insertDtoToEntity(I dto);

	protected abstract E updateDtoToEntity(U dto);

	protected abstract Optional<E> findByUpdateKey(B updateKey);

	protected abstract E setIdToUpdate(E t, E updateEntity);

	protected Iterable<F> toDTOs(Iterable<? extends E> entities) {
		List<F> result = new ArrayList<>();
		for (E entity : entities) {
			result.add(toDTO(entity));
		}
		return result;
	}

	@Autowired
	public void setRepository(CrudRepository<E, K> repository) {
		this.repository = repository;
	}

	@Transactional
	public F insert(I insertDto) {
		return toDTO(this.repository.save(insertDtoToEntity(insertDto)));
	}

	@Transactional
	public F update(B updateKey, U updateDto) {
		Optional<E> byUpdateKey = findByUpdateKey(updateKey);
		if (byUpdateKey.isPresent()) {
			E updateEntity = updateDtoToEntity(updateDto);
			updateEntity = setIdToUpdate(byUpdateKey.get(), updateEntity);
			return toDTO(this.repository.save(updateEntity));
		}
		else {
			throw new BeanNotFoundException("Bean not found by: " + updateKey);
		}
	}

	public Iterable<F> findAll() {
		return toDTOs(this.repository.findAll());
	}

}
