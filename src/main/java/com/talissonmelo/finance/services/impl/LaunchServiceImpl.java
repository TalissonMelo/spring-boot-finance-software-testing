package com.talissonmelo.finance.services.impl;

import java.util.List;
import java.util.Objects;

import javax.transaction.Transactional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.stereotype.Service;

import com.talissonmelo.finance.entity.Launch;
import com.talissonmelo.finance.entity.enums.StatusLaunch;
import com.talissonmelo.finance.exceptions.ErrorAuthenticateException;
import com.talissonmelo.finance.exceptions.businessRuleException;
import com.talissonmelo.finance.repository.LaunchRepository;
import com.talissonmelo.finance.services.LaunchService;

@Service
public class LaunchServiceImpl implements LaunchService {

	private LaunchRepository repository;

	public LaunchServiceImpl(LaunchRepository repository) {
		this.repository = repository;
	}

	@Override
	@Transactional
	public Launch insert(Launch launch) {
		return repository.save(launch);
	}

	@Override
	@Transactional
	public Launch update(Launch launch) {
		Objects.requireNonNull(launch.getId());
		return repository.save(launch);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		try {
			repository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ErrorAuthenticateException("Id não encontrado : " + id);
		} catch (DataIntegrityViolationException e) {
			throw new businessRuleException(
					"Não pode deletar esses dados por terem persistencia com outros dados no banco");
		}

	}

	@Override
	public List<Launch> findAll(Launch launchFilter) {
		Example<Launch> example = Example.of(launchFilter, ExampleMatcher
				.matching()
				.withIgnoreCase()
				.withStringMatcher(StringMatcher.CONTAINING));
		
		return repository.findAll(example);
	}

	@Override
	@Transactional
	public void updateStatus(Launch launch, StatusLaunch status) {
		launch.setStatus(status);
		update(launch);
	}

}
