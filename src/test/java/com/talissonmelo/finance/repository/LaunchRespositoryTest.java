package com.talissonmelo.finance.repository;

import java.util.Date;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.talissonmelo.finance.entity.Launch;
import com.talissonmelo.finance.entity.enums.StatusLaunch;
import com.talissonmelo.finance.entity.enums.TypeLaunch;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class LaunchRespositoryTest {

	@Autowired
	LaunchRepository repository;

	@Autowired
	TestEntityManager entityManager;

	@Test
	public void LaunchSave() {
		Launch launch = Launch.builder().description("Lancamento Teste 2020").year(2020).month(3).value((double) 80)
				.type(TypeLaunch.EXPENSE).status(StatusLaunch.PENDING).date(new Date()).build();

		launch = repository.save(launch);

		Assertions.assertThat(launch.getId()).isNotNull();
	}

	@Test
	public void deleteLaunch() {
		Launch launch = createAndPersistLaunch();
		
		launch = entityManager.find(Launch.class, launch.getId() );
		repository.delete(launch);
		
		Launch launchDelete = entityManager.find(Launch.class, launch.getId());
		Assertions.assertThat(launchDelete).isNull();
	}
	
	@Test
	public void updateLaunch() {
		Launch launch = createAndPersistLaunch();
		
		launch.setDescription("Lançamento Teste Atualizar ");
		launch.setMonth(12);
		launch.setYear(2019);
		launch.setType(TypeLaunch.EXPENSE);
		repository.save(launch);
		
		Launch updateLaunch = entityManager.find(Launch.class, launch.getId());
		
		Assertions.assertThat(updateLaunch.getYear().equals(2019));
		Assertions.assertThat(updateLaunch.getDescription().equals("Lançamento Teste Atualizar "));
		Assertions.assertThat(updateLaunch.getMonth().equals(12));
		Assertions.assertThat(updateLaunch.getType().equals(TypeLaunch.EXPENSE));
		
	}
	
	@Test
	public void launchFindById() {
		Launch launch = createAndPersistLaunch();
		
		Optional<Launch> resultFindByIdLaunch = repository.findById(launch.getId());
		
		Assertions.assertThat(resultFindByIdLaunch.isPresent()).isTrue();
	}
	
	private Launch createAndPersistLaunch() {
		Launch launch = createLaunch();
		entityManager.persist(launch);
		return launch;
	}

	private Launch createLaunch() {
		return Launch.builder().description("Lançamento Teste 2020").year(2020).month(3).value((double) 80)
				.type(TypeLaunch.EXPENSE).status(StatusLaunch.PENDING).date(new Date()).build();
	}
}
