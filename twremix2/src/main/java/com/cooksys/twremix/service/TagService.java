package com.cooksys.twremix.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.cooksys.twremix.dto.HashtagDto;
import com.cooksys.twremix.dto.TweetDto;
import com.cooksys.twremix.mapper.HashtagMapper;
import com.cooksys.twremix.mapper.TweetMapper;
import com.cooksys.twremix.repository.TagRepository;

@Service
public class TagService {
	
	private TagRepository tagRepository;
	private HashtagMapper tagMapper;
	private TweetMapper tweetMapper;
	
	public TagService(TagRepository tagRepository, HashtagMapper hashtagMapper, TweetMapper tweetMapper){
		super();
		this.tagRepository = tagRepository;
		this.tagMapper = hashtagMapper;
		this.tweetMapper = tweetMapper;
	}

	public List<TweetDto> taggedTweets(String label) {
		return tagRepository
				.findByLabel(label)
				.getUsedIn()
				.stream()
				.map(tweetMapper::toTweetDto)
				.collect(Collectors.toList());
	}

	public List<HashtagDto> index() {
		return tagRepository
				.findAll()
				.stream()
				.map(tagMapper::toHashtagDto)
				.collect(Collectors.toList());
				
	}

}