package com.cooksys.twremix.dto;

import java.sql.Timestamp;

public class RepostDto extends TweetDto{

	private Integer id;
	
	private UserDto author;
	
	private Timestamp posted;
	
	private RepostDto repostOf;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public UserDto getAuthor() {
		return author;
	}

	public void setAuthor(UserDto author) {
		this.author = author;
	}

	public Timestamp getPosted() {
		return posted;
	}

	public void setPosted(Timestamp posted) {
		this.posted = posted;
	}

	public RepostDto getRepostOf() {
		return repostOf;
	}

	public void setRepostOf(RepostDto repostOf) {
		this.repostOf = repostOf;
	}
}
