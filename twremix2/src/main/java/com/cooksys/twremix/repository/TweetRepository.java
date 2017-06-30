package com.cooksys.twremix.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cooksys.twremix.pojo.Tweet;
import com.cooksys.twremix.pojo.User;

public interface TweetRepository extends JpaRepository<Tweet, Integer>{
	
	Tweet findById(Integer id);
	Tweet findByIdAndDeletedFalse(Integer id);
	Tweet findByRepostOf(Tweet tweet);
	Tweet findByAuthor(User user);
	List<Tweet> findByDeletedFalse();
}
