package com.cooksys.twremix.service;

import org.springframework.stereotype.Service;

import com.cooksys.twremix.pojo.Hashtag;
import com.cooksys.twremix.pojo.User;
import com.cooksys.twremix.repository.TagRepository;
import com.cooksys.twremix.repository.UserRepository;

//service for dealing with validate requests
@Service
public class ValidateService {

	private UserRepository userRepository;
	
	private TagRepository tagRepository;
	
	public ValidateService(UserRepository userRepo, TagRepository tagRepo){
		super();
		this.userRepository = userRepo;
		this.tagRepository = tagRepo;
	}
	
	public boolean tag(String label) {
		Hashtag check = tagRepository.findByLabel(label);
		if(check == null)
			return false;
		else
			return true;
	}

	public boolean userExists(String username) {
		User check = userRepository.findByUname(username);
		System.out.println(username);
		if(check == null || check.isDeleted()){
			return false;
		}
		else 
			return true;
	}

	public boolean userAvailable(String username) {
		User check = userRepository.findByUname(username);
		if(check == null)
			return true;
		else 
			return false;
	}

}
