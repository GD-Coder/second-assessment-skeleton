package com.cooksys.twremix.service;

import java.util.ArrayList;
import java.util.List;

import com.cooksys.twremix.dto.TweetDto;

/***
 * context class
 * holds target TweetDto and lists of before and after
 * pojo
 */
public class Context {

	private TweetDto target;
	
	private List<TweetDto> before = new ArrayList<>();
	
	private List<TweetDto> after = new ArrayList<>();

	public TweetDto getTarget() {
		return target;
	}

	public void setTarget(TweetDto target) {
		this.target = target;
	}

	public List<TweetDto> getBefore() {
		return before;
	}

	public void setBefore(List<TweetDto> before) {
		this.before = before;
	}

	public List<TweetDto> getAfter() {
		return after;
	}

	public void setAfter(List<TweetDto> after) {
		this.after = after;
	}
}