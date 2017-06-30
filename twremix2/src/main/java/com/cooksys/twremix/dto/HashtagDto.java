package com.cooksys.twremix.dto;

import java.sql.Timestamp;

public class HashtagDto {
	
	private String label;
	
	private Timestamp firstUsed;
	
	private Timestamp lastUsed;

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Timestamp getFirstUsed() {
		return firstUsed;
	}

	public void setFirstUsed(Timestamp firstUsed) {
		this.firstUsed = firstUsed;
	}

	public Timestamp getLastUsed() {
		return lastUsed;
	}

	public void setLastUsed(Timestamp lastUsed) {
		this.lastUsed = lastUsed;
	}
	
	

}
