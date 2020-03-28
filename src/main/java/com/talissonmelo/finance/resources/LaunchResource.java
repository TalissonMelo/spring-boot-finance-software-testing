package com.talissonmelo.finance.resources;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.talissonmelo.finance.entity.Launch;
import com.talissonmelo.finance.entity.User;
import com.talissonmelo.finance.entity.dto.LaunchDTO;
import com.talissonmelo.finance.entity.enums.StatusLaunch;
import com.talissonmelo.finance.entity.enums.TypeLaunch;
import com.talissonmelo.finance.exceptions.ErrorAuthenticateException;
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
	public ResponseEntity<?> insert(@RequestBody LaunchDTO objDTO) {
		try {
			Launch entity = fromDTO(objDTO);
			entity = service.insert(entity);
			return ResponseEntity.ok().body(entity);
		} catch (businessRuleException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<?> update(@PathVariable Long id, @RequestBody LaunchDTO objDTO) {

		return service.findLaunchId(id).map(entity -> {
			try {
				Launch launch = fromDTO(objDTO);
				launch.setId(entity.getId());
				service.update(launch);
				return ResponseEntity.ok().body(launch);
			} catch (businessRuleException e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		}).orElseGet(() -> new ResponseEntity<String>("Lançamento não encontrado", HttpStatus.BAD_REQUEST));
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		try {
			service.delete(id);
			return ResponseEntity.noContent().build();
		} catch (ErrorAuthenticateException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		} catch (businessRuleException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping
	public ResponseEntity<?> findAll(
			@RequestParam(value = "description", required = false) String description,
			@RequestParam(value = "month", required = false) Integer month,
			@RequestParam(value = "year", required = false) Integer year, 
			@RequestParam("user") Long user) {

		Launch launchFilter = new Launch();
		launchFilter.setDescription(description);
		launchFilter.setMonth(month);
		launchFilter.setYear(year);

		Optional<User> use = userService.findUserById(user);
		if (use.isPresent()) {
			return ResponseEntity.badRequest().body("Usuário não encontrado com Id " + user);
		} else {
			launchFilter.setUser(use.get());
		}

		List<Launch> list = service.findAll(launchFilter);
		return ResponseEntity.ok().body(list);

	}

	private Launch fromDTO(LaunchDTO dto) {

		Launch launch = new Launch();

		launch.setId(dto.getId());
		launch.setDescription(dto.getDescription());
		launch.setMonth(dto.getMonth());
		launch.setYear(dto.getYear());
		launch.setValue(dto.getValue());

		if (dto.getType() != null)
			launch.setType(TypeLaunch.valueOf(dto.getType()));

		if (dto.getStatus() != null)
			launch.setStatus(StatusLaunch.valueOf(dto.getStatus()));

		User user = userService.findUserById(dto.getUser())
				.orElseThrow(() -> new businessRuleException("Usuário com ID não identificado. Id : " + dto.getUser()));

		launch.setUser(user);

		return launch;
	}

}
