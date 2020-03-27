package com.talissonmelo.finance.resources;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.talissonmelo.finance.entity.Launch;
import com.talissonmelo.finance.entity.User;
import com.talissonmelo.finance.entity.dto.LaunchDTO;
import com.talissonmelo.finance.entity.enums.StatusLaunch;
import com.talissonmelo.finance.entity.enums.TypeLaunch;
import com.talissonmelo.finance.exceptions.businessRuleException;
import com.talissonmelo.finance.services.LaunchService;
import com.talissonmelo.finance.services.UserService;

@RestController
@RequestMapping(value = "/launches")
public class LaunchResource {

	private LaunchService service;
	private UserService userService;

	public LaunchResource(LaunchService service, UserService userService) {
		super();
		this.service = service;
		this.userService = userService;
	}

	@PostMapping
	public ResponseEntity<Launch> insert(@RequestBody LaunchDTO objDTO) {
		Launch entity = fromDTO(objDTO);
		entity = service.insert(entity);
		return ResponseEntity.ok().body(entity);
	}

	private Launch fromDTO(LaunchDTO dto) {
		
		Launch launch = new Launch();

		launch.setId(dto.getId());
		launch.setDescription(dto.getDescription());
		launch.setMonth(dto.getMonth());
		launch.setYear(dto.getYear());
		launch.setValue(dto.getValue());
		
		if(dto.getType() != null)
		launch.setType(TypeLaunch.valueOf(dto.getType()));
		
		if(dto.getStatus() != null)
		launch.setStatus(StatusLaunch.valueOf(dto.getStatus()));

		User user = userService.findUserById(dto.getUser())
				.orElseThrow(() -> new businessRuleException("Usuário com ID não identificado. Id : " 
		+ dto.getUser()));

		launch.setUser(user);

		return launch;
	}

}
