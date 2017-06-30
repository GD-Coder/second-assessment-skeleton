package com.cooksys.twremix.mapper;

import org.mapstruct.Mapper;

import com.cooksys.twremix.dto.ReplyDto;
import com.cooksys.twremix.dto.RepostDto;
import com.cooksys.twremix.dto.SimpleDto;
import com.cooksys.twremix.dto.TweetDto;
import com.cooksys.twremix.pojo.Tweet;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface TweetMapper {

	
	TweetDto toTweetDto(Tweet tweet);
	
	Tweet toTweet(TweetDto tweetDto);
	
	ReplyDto toReplyDto(Tweet tweet);
	
	Tweet toTweet(ReplyDto replyDto);
	
	RepostDto toRepostDto(Tweet tweet);
	
	Tweet toTweet(RepostDto repostDto);
	
	SimpleDto toSimpleDto(Tweet tweet);
	
	Tweet toTweet(SimpleDto simpleDto);
}
