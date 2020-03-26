package com.talissonmelo.finance.services;

import java.util.List;

import com.talissonmelo.finance.entity.Launch;
import com.talissonmelo.finance.entity.enums.StatusLaunch;

public interface LaunchService {

	public Launch insert(Launch launch);

	public Launch update(Launch launch);

	public void delete(Long id);

	public List<Launch> findAll(Launch launchFilter);

	public void updateStatus(Launch launch, StatusLaunch status);
	
	public void validate(Launch launch);

}
