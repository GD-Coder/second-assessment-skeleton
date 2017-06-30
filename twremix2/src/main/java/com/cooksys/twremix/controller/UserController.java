package com.cooksys.twremix.controller;


import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.twremix.dto.TweetDto;
import com.cooksys.twremix.dto.UserDto;
import com.cooksys.twremix.execpt.PersonNotFoundExcept;
import com.cooksys.twremix.pojo.Credentials;
import com.cooksys.twremix.pojo.Tweet;
import com.cooksys.twremix.pojo.User;
import com.cooksys.twremix.service.UserService;

@RestController
@Validated
@RequestMapping("users")
public class UserController {

	private UserService userService;
	
	public UserController(UserService userService){
		super();
		this.userService = userService;
	}
	
	@GetMapping
	public List<UserDto> index() {
		return userService.index();
	}

	@PostMapping
	public UserDto post(@RequestBody RequestWrapper wrapper, HttpServletResponse httpResponse){
		UserDto dto = userService.post(wrapper.getCredentials(), wrapper.getProfile());
		httpResponse.setStatus(HttpServletResponse.SC_CREATED);
		return dto;
	}	

	@GetMapping("@{username}")
	public UserDto getUsername(@PathVariable String username){
		UserDto user = userService.get(username);
//		if(user == null){
//			throw new UserException("User does not exist");
//		}
		return user;
	}

	@PutMapping("@{username}")
	public UserDto patchUser(@PathVariable String username, @RequestBody Credentials credentials, String newname, HttpServletResponse httpResponse){
		if(userService.credentialCheck(username, credentials)) {
		return userService.patch(username, newname);
		}
		else {
			return null;
		}
	}
//	@PutMapping("@{username}")
//	public UserDto patchUser(@PathVariable String username, @RequestBody RequestWrapper wrapper, HttpServletResponse httpResponse){
//		UserDto user = userService.get(username);
//		return userService.patch(username, wrapper.getCredentials(), wrapper.getProfile());
//	}
	@DeleteMapping("@{username}")
	public UserDto deleteUser(@PathVariable String username, HttpServletResponse httpResponse){
		return userService.delete(username);
	}
	
	@PostMapping("@{username}/follow")
	public void follow(@PathVariable String username, @RequestBody Credentials credentials, HttpServletResponse httpResponse){
		userService.follow(username, credentials);
	}
	
	@PostMapping("@{username}/unfollow")
	public void unfollow(@PathVariable String username, @RequestBody Credentials credentials, HttpServletResponse httpResponse){
		userService.unfollow(username, credentials);
	}
	
	@GetMapping("@{username}/feed")
	public List<TweetDto> getFeed(@PathVariable String username){
		return userService.feed(username);
	}
	
	@GetMapping("@{username}/tweets")
	public List<TweetDto> getTweets(@PathVariable String username){
		return userService.tweets(username);
	}
	
	@GetMapping("@{username}/mentions")
	public List<TweetDto> getMentions(@PathVariable String username){
		return userService.mentions(username);
	}
	
	@GetMapping("@{username}/followers")
	public Set<UserDto> getFollowers(@PathVariable String username){
			return userService.getFollowers(username);
	}
	
	@GetMapping("@{username}/following")
	public Set<UserDto> getFollowing(@PathVariable String username){
		return userService.getFollowing(username);
	}
	
}
