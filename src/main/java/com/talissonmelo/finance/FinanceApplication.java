package com.talissonmelo.finance;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.talissonmelo.finance.entity.Launch;
import com.talissonmelo.finance.entity.User;
import com.talissonmelo.finance.entity.enums.StatusLaunch;
import com.talissonmelo.finance.entity.enums.TypeLaunch;
import com.talissonmelo.finance.repository.LaunchRepository;
import com.talissonmelo.finance.repository.UserRepository;

@SpringBootApplication
public class FinanceApplication implements CommandLineRunner{
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private LaunchRepository launchRepository;

	public static void main(String[] args) {
		SpringApplication.run(FinanceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		
		User user = new User(null, "Talisson Melo", "talisson.cursos@gmail.com", "123456");
		User user1 = new User(null, "Joao", "joao@gmail.com", "123456");
		
		userRepository.saveAll(Arrays.asList(user,user1));
		
		Launch launch = new Launch(null, "Salario Mensal", 3, 2020, (double) 600,sdf.parse("01/03/2020 00:00:00"), TypeLaunch.RECIPE, StatusLaunch.EFFECTIVE, user1);
		
		launchRepository.saveAll(Arrays.asList(launch));
	}

}
