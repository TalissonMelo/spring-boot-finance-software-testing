package com.talissonmelo.finance.resources;

import java.net.URI;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.talissonmelo.finance.entity.User;
import com.talissonmelo.finance.entity.dto.UserAuthenticateDTO;
import com.talissonmelo.finance.entity.dto.UserDTO;
import com.talissonmelo.finance.exceptions.ErrorAuthenticateException;
import com.talissonmelo.finance.exceptions.businessRuleException;
import com.talissonmelo.finance.services.LaunchService;
import com.talissonmelo.finance.services.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/users")
@RequiredArgsConstructor
public class UserResource {

	private final UserService service;
	private final LaunchService launchService;

	@PostMapping("/login")
	public ResponseEntity<?> authenticate(@RequestBody UserAuthenticateDTO objDTO) {
		try {
			User user = service.authenticate(objDTO.getEmail(), objDTO.getPassword());
			return ResponseEntity.ok().body(user);
		} catch (ErrorAuthenticateException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}

	}

	@PostMapping
	public ResponseEntity<?> insert(@RequestBody UserDTO objDTO) {
		try {
			User user = User.builder().name(objDTO.getName()).email(objDTO.getEmail()).password(objDTO.getPassword())
					.build();

			User userSave = service.insert(user);
			URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(userSave.getId())
					.toUri();
			return ResponseEntity.created(uri).body(userSave);
		} catch (businessRuleException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@Transactional
	@GetMapping("/{id}/balance")
	public ResponseEntity<?> balanceForUser(@PathVariable Long id) {

		Optional<User> user = service.findUserById(id);

		if (!user.isPresent()) {
			return ResponseEntity.badRequest().body("Usuário nao encontrado.");
		}

		Double balance = launchService.balanceUser(id);
		return ResponseEntity.ok().body(balance);

	}
}
