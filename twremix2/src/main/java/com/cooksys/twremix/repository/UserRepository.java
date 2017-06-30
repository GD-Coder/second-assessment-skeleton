package com.cooksys.twremix.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cooksys.twremix.pojo.User;

public interface UserRepository extends JpaRepository<User, Long>{

	User findByUname(String username);
	User findByCredentialsUsername(String username);
	List<User> findByDeletedFalse();
}
