package com.talissonmelo.finance.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.talissonmelo.finance.entity.Launch;
import com.talissonmelo.finance.entity.enums.StatusLaunch;
import com.talissonmelo.finance.repository.LaunchRepository;
import com.talissonmelo.finance.services.LaunchService;

@Service
public class LaunchServiceImpl implements LaunchService {
	
	private LaunchRepository repository;
	
	public LaunchServiceImpl(LaunchRepository repository) {
		this.repository = repository;
	}

	@Override
	public Launch insert(Launch launch) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Launch update(Launch launch) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Launch> findAll(Launch launchFilter) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateStatus(Launch launch, StatusLaunch status) {
		// TODO Auto-generated method stub
		
	}

}
