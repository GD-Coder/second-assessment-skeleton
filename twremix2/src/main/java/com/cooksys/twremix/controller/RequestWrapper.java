package com.cooksys.twremix.controller;
import com.cooksys.twremix.pojo.Credentials;
import com.cooksys.twremix.pojo.Profile;

//holds profile, content, and credentials
//made to solve problem of only one thing in request body
public class RequestWrapper {

	Credentials credentials;
	Profile profile;
	String content;
	
	public Credentials getCredentials() {
		return credentials;
	}
	public void setCredentials(Credentials credentials) {
		this.credentials = credentials;
	}
	public Profile getProfile() {
		return profile;
	}
	public void setProfile(Profile profile) {
		this.profile = profile;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	
	
}