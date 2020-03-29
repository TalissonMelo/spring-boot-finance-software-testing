package com.talissonmelo.finance.services;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.domain.Example;
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

		// execução
		Assertions.catchThrowableOfType(() -> service.insert(launch), businessRuleException.class);

		// verificação
		Mockito.verify(repository, Mockito.never()).save(launch);

	}

	@Test
	public void updateLaunchSuccess() {

		// cenário
		Launch launch = LaunchRespositoryTest.createLaunch();
		launch.setId(1l);
		launch.setStatus(StatusLaunch.PENDING);
		launch.setDate(new Date());
		Mockito.doNothing().when(service).validate(launch);

		Mockito.when(repository.save(launch)).thenReturn(launch);

		// execução
		service.insert(launch);

		// verificação
		Mockito.verify(repository, Mockito.times(1)).save(launch);
	}

	@Test
	public void notUpdateLaunchErrorValidate() {

		// cenário
		Launch launch = LaunchRespositoryTest.createLaunch();

		// execução
		Assertions.catchThrowableOfType(() -> service.update(launch), NullPointerException.class);

		// verificação
		Mockito.verify(repository, Mockito.never()).save(launch);

	}

	@Test
	public void deleteLaunchSuccess() {
		// cenário
		Launch launch = LaunchRespositoryTest.createLaunch();
		launch.setId(1l);

		// execução
		service.delete(1l);

		// verificação
		Mockito.verify(repository).deleteById(1l);
	}

	@Test
	public void findAllLaunch() {
		// cenário
		Launch launch = LaunchRespositoryTest.createLaunch();
		launch.setId(1l);
		launch.setDate(new Date());

		List<Launch> list = Arrays.asList(launch);
		Mockito.when(repository.findAll(Mockito.any(Example.class))).thenReturn(list);

		// execução
		List<Launch> result = service.findAll(launch);

		// verificação
		Assertions.assertThat(result).isNotEmpty().hasSize(1).contains(launch);

	}

	@Test
	public void updateStatusLaunch() {
		// cenário
		Launch launch = LaunchRespositoryTest.createLaunch();
		launch.setId(1l);
		launch.setStatus(StatusLaunch.PENDING);

		StatusLaunch statusNew = StatusLaunch.EFFECTIVE;
		Mockito.doReturn(launch).when(service).update(launch);

		// execução
		service.updateStatus(launch, statusNew);

		// verificação
		Assertions.assertThat(launch.getStatus()).isEqualTo(statusNew);
		Mockito.verify(service).update(launch);
	}
}
