package com.talissonmelo.finance.services;

import java.util.Date;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.talissonmelo.finance.entity.Launch;
import com.talissonmelo.finance.entity.enums.StatusLaunch;
import com.talissonmelo.finance.exceptions.businessRuleException;
import com.talissonmelo.finance.repository.LaunchRepository;
import com.talissonmelo.finance.repository.LaunchRespositoryTest;
import com.talissonmelo.finance.services.impl.LaunchServiceImpl;

@RunWith(SpringRunner.class)
public class LaunchServiceTest {

	@SpyBean
	LaunchServiceImpl service;

	@MockBean
	LaunchRepository repository;

	@Test
	public void saveLaunchSuccess() {

		// cenário
		Launch launchSave = LaunchRespositoryTest.createLaunch();
		Mockito.doNothing().when(service).validate(launchSave);

		Launch launch = LaunchRespositoryTest.createLaunch();
		launch.setId(1l);
		launch.setStatus(StatusLaunch.PENDING);
		launch.setDate(new Date());
		Mockito.when(repository.save(launchSave)).thenReturn(launch);

		// execução
		Launch launchVerification = service.insert(launchSave);

		// verificação
		Assertions.assertThat(launchVerification.getId()).isEqualTo(launch.getId());
		Assertions.assertThat(launchVerification.getStatus()).isEqualTo(StatusLaunch.PENDING);
	}

	@Test
	public void notSaveLaunchErrorValidate() {

		// cenário
		Launch launch = LaunchRespositoryTest.createLaunch();
		Mockito.doThrow(businessRuleException.class).when(service).validate(launch);
		
		//execução 
		Assertions.catchThrowableOfType(() -> service.insert(launch), businessRuleException.class);
		
		//verificação
		Mockito.verify(repository, Mockito.never()).save(launch);

	}
}
