package com.cooksys.twremix.pojo;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "users")
public class User {
	@Id
	@GeneratedValue
	private Long id;
	@NotNull
	@Column(unique = true)
	private String uname;

	private Profile profile;

	@Column(name = "timestamp", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Timestamp joined;
	@Embedded
	private Credentials credentials;
	@OneToMany(mappedBy = "id")
	private List<Tweet> tweets;
	
	@ManyToMany(mappedBy="author")
	private List<Tweet> liked;
	
	private boolean deleted;
	
	@JoinTable(name = "follows", joinColumns = {@JoinColumn(name = "followee", referencedColumnName = "id", nullable = false)}, inverseJoinColumns = {
	@JoinColumn(name = "follower", referencedColumnName = "id", nullable = false)})
	@ManyToMany
	private Set<User> following;
	
	@ManyToMany(mappedBy = "following")
	private Set<User> followers;
	
	@ManyToMany(mappedBy = "mentions")
	private List<Tweet> mentioned;

	public List<Tweet> getMentioned() {
		return mentioned;
	}

	public void setMentioned(List<Tweet> mentioned) {
		this.mentioned = mentioned;
	}

	public List<Tweet> getLiked() {
		return liked;
	}

	public void setLiked(List<Tweet> liked) {
		this.liked = liked;
	}

	public Set<User> getFollowing() {
		return following;
	}

	public void setFollowing(Set<User> following) {
		this.following = following;
	}

	public Set<User> getFollowers() {
		return followers;
	}

	public void setFollowers(Set<User> followers) {
		this.followers = followers;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Tweet> getTweets() {
		return tweets;
	}

	public void setTweets(List<Tweet> tweets) {
		this.tweets = tweets;
	}

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

	public Credentials getCredentials() {
		return credentials;
	}

	public void setCredentials(Credentials credentials) {
		this.credentials = credentials;
	}

}
