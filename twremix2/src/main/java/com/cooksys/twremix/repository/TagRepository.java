package com.cooksys.twremix.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cooksys.twremix.pojo.Hashtag;


public interface TagRepository extends JpaRepository<Hashtag, Integer>{
	
	Hashtag findByLabel(String label);

}
