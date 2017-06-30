package com.cooksys.twremix.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.twremix.pojo.Credentials;
import com.cooksys.twremix.service.UserService;
import com.cooksys.twremix.service.ValidateService;



@RestController
@RequestMapping("validate")
public class ValidateController {

	private ValidateService validateService;
	private UserService userService;
	
	public ValidateController(ValidateService validateService, UserService userService){
		super();
		this.validateService = validateService;
		this.userService = userService;
	}

	@PostMapping("username/credentials/@{username}")
	public boolean credentialCheck(@PathVariable String username, @RequestBody Credentials credentials){
		System.out.println(credentials.getUsername() + " " + credentials.getPassword());
		return userService.credentialCheck(username, credentials);
	}
	
	@GetMapping("tag/exists/{label}")
	public boolean tagCheck(@PathVariable String label){
		return validateService.tag(label);
	}
	
	@GetMapping("username/exists/@{username}")
	public boolean exists(@PathVariable String username){
		return validateService.userExists(username);
	}
	
	@GetMapping("username/available/@{username}")
	public boolean available(@PathVariable String username){
		return validateService.userAvailable(username);
	}
}
