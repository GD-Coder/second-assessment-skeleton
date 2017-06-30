package com.cooksys.twremix.pojo;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;

@Entity
public class Hashtag {

	@Id
	@GeneratedValue
	private Integer id;
	@NotNull
	private String label;
	@Column(nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Timestamp firstUsed;
	
	private Timestamp lastUsed;
	
	@ManyToMany(mappedBy = "tagsUsed")
	private List<Tweet> usedIn;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
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
	public List<Tweet> getUsedIn() {
		return usedIn;
	}
	public void setUsedIn(List<Tweet> usedIn) {
		this.usedIn = usedIn;
	}
	
}
