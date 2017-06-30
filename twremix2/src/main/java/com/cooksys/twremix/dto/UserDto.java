package com.cooksys.twremix.dto;

import java.sql.Timestamp;

import javax.validation.constraints.NotNull;

import com.cooksys.twremix.pojo.Profile;



public class UserDto {

	@NotNull
	private String uname;
	
	@NotNull
	private Profile profile;
	
	@NotNull
	private Timestamp joined;

	public String getUname() {
		return uname;
	}

	public void setUname(String username) {
		this.uname = username;
	}

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	public Timestamp getJoined() {
		return joined;
	}

	public void setJoined(Timestamp joined) {
		this.joined = joined;
	}
}
