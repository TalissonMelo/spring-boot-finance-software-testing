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
		validate(launch);
		launch.setStatus(StatusLaunch.PENDING);
		return repository.save(launch);
	}

	@Override
	@Transactional
	public Launch update(Launch launch) {
		Objects.requireNonNull(launch.getId());
		validate(launch);
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
		Example<Launch> example = Example.of(launchFilter,
				ExampleMatcher.matching().withIgnoreCase().withStringMatcher(StringMatcher.CONTAINING));

		return repository.findAll(example);
	}

	@Override
	@Transactional
	public void updateStatus(Launch launch, StatusLaunch status) {
		launch.setStatus(status);
		update(launch);
	}

	@Override
	public void validate(Launch launch) {

		if (launch.getDescription() == null || launch.getDescription().trim().equals("")) {
			throw new businessRuleException("Informe uma Descrição válida!");
		}

		if (launch.getMonth() == null || launch.getMonth() < 1 || launch.getMonth() > 12) {
			throw new businessRuleException("Informe uma Mês válida!");
		}

		if (launch.getYear() == null || launch.getYear().toString().length() != 4) {
			throw new businessRuleException("Informe uma Ano válida!");
		}

		if (launch.getValue() == null || launch.getValue() < 1) {
			throw new businessRuleException("Informe uma Valor válido.");
		}

		if (launch.getType() == null) {
			throw new businessRuleException("Informe um tipo de lançamento.");
		}

		if (launch.getUser() == null || launch.getUser().getId() == null) {
			throw new businessRuleException("Informe uma Usuário!");
		}

	}

}
