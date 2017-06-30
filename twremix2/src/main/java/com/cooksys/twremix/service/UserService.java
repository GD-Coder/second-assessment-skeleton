package com.cooksys.twremix.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Service;

import com.cooksys.twremix.dto.TweetDto;
import com.cooksys.twremix.dto.UserDto;
import com.cooksys.twremix.mapper.TweetMapper;
import com.cooksys.twremix.mapper.UserMapper;
import com.cooksys.twremix.pojo.Credentials;
import com.cooksys.twremix.pojo.Profile;
import com.cooksys.twremix.pojo.Tweet;
import com.cooksys.twremix.pojo.User;
import com.cooksys.twremix.repository.TweetRepository;
import com.cooksys.twremix.repository.UserRepository;

@Service
public class UserService {

	UserRepository userRepository;
	UserMapper userMapper;
	TweetMapper tweetMapper;
	TweetRepository tweetRepository;
	EntityManager entityManager;

	public UserService(UserRepository userRepo, UserMapper userMap, TweetMapper tweetMapper,
			TweetRepository tweetRepository, EntityManager entityManager) {
		super();
		this.userMapper = userMap;
		this.userRepository = userRepo;
		this.tweetMapper = tweetMapper;
		this.tweetRepository = tweetRepository;
		this.entityManager = entityManager;
	}

// Find all active users in the database
	public List<UserDto> index() {
		return userRepository.findByDeletedFalse().stream().map(userMapper::toUserDto).collect(Collectors.toList());
	}

// Adds a new user to the database
	public UserDto post(Credentials credentials, Profile profile) {
		
		User user = userRepository.findByCredentialsUsername(credentials.getUsername());
		if (user == null) {
			user = new User();
			user.setUname(credentials.getUsername());
			user.setCredentials(credentials);
			user.setProfile(profile);
			userRepository.save(user);
			entityManager.detach(user);
			user = userRepository.findByUname(credentials.getUsername());
		
		}
		
// If the user already exists and has been flagged "deleted" this sets the account back to active		
		else if (user.isDeleted() == true) {
			user.setDeleted(false);
			userRepository.save(user);
			
			
			
		}
		return userMapper.toUserDto(user);
	}
	
// Finds an account by username, returns null if user has been flagged for deletion 
	public UserDto get(String username) {
		User delcheck = userRepository.findByUname(username);
		if(delcheck.isDeleted() == true){
			return null;
		} else {
		return userMapper.toUserDto(userRepository.findByUname(username));
		}
	}

// Deletes a user and flags their tweets for deletion
	public UserDto delete(String username) {
			
			User deleted = userRepository.findByUname(username);
			if (credentialCheck(username, deleted.getCredentials())) {
			deleted.setDeleted(true);
			for (Tweet t : deleted.getTweets()) {
				t.setDeleted(true);

			}
			tweetRepository.save(deleted.getTweets());
			userRepository.save(deleted);
			return userMapper.toUserDto(deleted);
		} else
			return null;
	}	

// Creates a follower 
	public void follow(String username, Credentials credentials) {
		if (credentialCheck(credentials.getUsername(), credentials)) {
			User follower = userRepository.findByCredentialsUsername(credentials.getUsername());
			User followee = userRepository.findByUname(username);
			follower.getFollowing().add(followee);
			followee.getFollowers().add(follower);
			userRepository.save(follower);
			userRepository.save(followee);
		}
	}

// Deletes a follower
	public void unfollow(String username, Credentials credentials) {
		if (credentialCheck(credentials.getUsername(), credentials)) {
			User follower = userRepository.findByCredentialsUsername(credentials.getUsername());
			User followee = userRepository.findByUname(username);
			follower.getFollowing().remove(followee);
			followee.getFollowers().remove(follower);
			userRepository.save(follower);
			userRepository.save(followee);
		}
	}
	

// Changes a given users username
	public UserDto patch(String username, String newname) {		    
			User patched = userRepository.findByUname(username);
			patched.setUname(newname);
			userRepository.save(patched);			
			return userMapper.toUserDto(patched);
		}
	
//	public UserDto patch(String username, Credentials credentials, Profile profile) {
//		User patched = userRepository.findByUname(username);
//		//patched.setProfile(profile);
//		patched.setUname(credentials.getUsername());
//		userRepository.save(patched);			
//		return userMapper.toUserDto(patched);
//	}

// Gets all followers for a given user
	public Set<UserDto> getFollowers(String username) {
		User getting = userRepository.findByUname(username);
		Set<UserDto> dtoSet = new HashSet<>();
		for (User u : getting.getFollowers()) {
			dtoSet.add(userMapper.toUserDto(u));
		}
		return dtoSet;
	}

// Gets users the given user is following
	public Set<UserDto> getFollowing(String username) {
		User getting = userRepository.findByUname(username);
		Set<UserDto> dtoSet = new HashSet<>();
		for (User u : getting.getFollowing()) {
			dtoSet.add(userMapper.toUserDto(u));
		}
		return dtoSet;
	}

/* Gets a users posts and determines if it is a new post, reply or a repost
and sets it in the database accordingly */
	public List<TweetDto> feed(String username) {
		List<TweetDto> self = new ArrayList<>();
		for (Tweet tweet : userRepository.findByUname(username).getTweets()) {
			if (tweet.isDeleted() == false){
				switch (tweet.getType()) {
				case "simple":
					self.add(tweetMapper.toSimpleDto(tweet));
					break;
				case "reply":
					self.add(tweetMapper.toReplyDto(tweet));
					break;
				case "repost":
					self.add(tweetMapper.toRepostDto(tweet));
					break;
				}
			}
		}		
/* Gets their followers posts and determines if it is a new post, reply or a repost
and sets it in the database accordingly */
		for (User u : userRepository.findByUname(username).getFollowing()) {
			for (Tweet tweet : u.getTweets()) {
				if (tweet.isDeleted() == false)
					if (tweet.isDeleted() == false){
						switch (tweet.getType()) {
						case "simple":
							self.add(tweetMapper.toSimpleDto(tweet));
							break;
						case "reply":
							self.add(tweetMapper.toReplyDto(tweet));
							break;
						case "repost":
							self.add(tweetMapper.toRepostDto(tweet));
							break;
						}
					}
			}
		}
		Collections.sort(self, new TweetCompare());
		Collections.reverse(self);
		return self;
	}
/* Gets all of a users posts and determines if they are a new post, reply or a repost
and displays them accordingly */
	public List<TweetDto> tweets(String username) {
		List<Tweet> convert = userRepository.findByUname(username).getTweets();

		List<TweetDto> dto = new ArrayList<>();
		for(Tweet check : convert){
			switch (check.getType()) {
			case "simple":
				dto.add(tweetMapper.toSimpleDto(check));
				break;
			case "reply":
				dto.add(tweetMapper.toReplyDto(check));
				break;
			case "repost":
				dto.add(tweetMapper.toRepostDto(check));
				break;
			}
		}
		Collections.sort(dto, new TweetCompare());
		Collections.reverse(dto);
		return dto;
	}

	public List<TweetDto> mentions(String username) {
		List<Tweet> convert = userRepository.findByUname(username).getMentioned();

		List<TweetDto> dto = new ArrayList<>();
		for(Tweet check : convert){
			switch (check.getType()) {
			case "simple":
				dto.add(tweetMapper.toSimpleDto(check));
				break;
			case "reply":
				dto.add(tweetMapper.toReplyDto(check));
				break;
			case "repost":
				dto.add(tweetMapper.toRepostDto(check));
				break;
			}
		}
		Collections.sort(dto,new TweetCompare());
		return dto;

	}
	
	//reusable method to check credentials against the user
	public boolean credentialCheck(String username, Credentials credentials) {
		if (userRepository.findByUname(username).getCredentials().equals(credentials))
			return true;
		else
			return false;
	}	

}