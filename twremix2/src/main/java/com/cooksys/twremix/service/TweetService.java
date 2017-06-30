package com.cooksys.twremix.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.cooksys.twremix.dto.HashtagDto;
import com.cooksys.twremix.dto.ReplyDto;
import com.cooksys.twremix.dto.RepostDto;
import com.cooksys.twremix.dto.SimpleDto;
import com.cooksys.twremix.dto.TweetDto;
import com.cooksys.twremix.dto.UserDto;
import com.cooksys.twremix.mapper.HashtagMapper;
import com.cooksys.twremix.mapper.TweetMapper;
import com.cooksys.twremix.mapper.UserMapper;
import com.cooksys.twremix.pojo.Credentials;
import com.cooksys.twremix.pojo.Hashtag;
import com.cooksys.twremix.pojo.Tweet;
import com.cooksys.twremix.pojo.User;
import com.cooksys.twremix.repository.TagRepository;
import com.cooksys.twremix.repository.TweetRepository;
import com.cooksys.twremix.repository.UserRepository;

@Service
public class TweetService {

	private TweetRepository tweetRepository;
	private UserRepository userRepository;
	private TagRepository tagRepository;
	private TweetMapper tweetMapper;
	private HashtagMapper tagMapper;
	private UserMapper userMapper;

	public TweetService(TweetRepository tweetRepository, UserRepository userRepository, TagRepository tagRepository,
			TweetMapper tweetMapper, HashtagMapper tagMapper, UserMapper userMapper) {
		super();
		this.tweetRepository = tweetRepository;
		this.userRepository = userRepository;
		this.tagRepository = tagRepository;
		this.tweetMapper = tweetMapper;
		this.tagMapper = tagMapper;
		this.userMapper = userMapper;
	}

	public List<TweetDto> index() {
		List<Tweet> notDeleted = tweetRepository.findByDeletedFalse();
		List<TweetDto> index = new ArrayList<>();
		for(Tweet check : notDeleted){
			switch (check.getType()) {
			case "simple":
				index.add(tweetMapper.toSimpleDto(check));
				break;
			case "reply":
				index.add(tweetMapper.toReplyDto(check));
				break;
			case "repost":
				index.add(tweetMapper.toRepostDto(check));
				break;
			}
		}
		Collections.reverse(index);
		return index;
	}

	public SimpleDto post(String username, String content) {

		Tweet posted = new Tweet();
		

		createTweet(posted, username, "simple", content);
		
		return tweetMapper.toSimpleDto(posted);
	}

	public TweetDto get(int id) {
		Tweet gotten = tweetRepository.findByIdAndDeletedFalse(id);
		TweetDto dto = new TweetDto();
		switch (gotten.getType()) {
		case "simple":
			dto = tweetMapper.toSimpleDto(gotten);
			break;
		case "reply":
			dto = tweetMapper.toReplyDto(gotten);
			break;
		case "repost":
			dto = tweetMapper.toRepostDto(gotten);
			break;
		}
		return dto;
	}

	public TweetDto delete(int id, Credentials credentials) {
			Tweet deleted = tweetRepository.findById(id);
			deleted.setDeleted(true);
			tweetRepository.save(deleted);
			return tweetMapper.toTweetDto(deleted);
	}

	public void like(int id, Credentials credentials) {
			Tweet liked = tweetRepository.findByIdAndDeletedFalse(id);
			User liker = userRepository.findByCredentialsUsername(credentials.getUsername());
			liked.getLikes().add(liker);
			liker.getLiked().add(liked);
			tweetRepository.save(liked);
			userRepository.save(liker);
	
	}

	public ReplyDto reply(int id, Credentials credentials, String content) {
		Tweet reply = new Tweet();
		Tweet repliedTo = tweetRepository.findByIdAndDeletedFalse(id);
		reply.setInReplyTo(tweetRepository.findById(id));
		createTweet(reply,credentials.getUsername(), "reply", content);
		repliedTo.getReplies().add(reply);
		tweetRepository.save(repliedTo);

		return tweetMapper.toReplyDto(reply);
	}

	public RepostDto repost(int id, Credentials credentials) {
		Tweet repost = new Tweet();
		Tweet reposted = tweetRepository.findByIdAndDeletedFalse(id);
		repost.setRepostOf(reposted);
		reposted.getReposts().add(repost);
		createTweet(repost, credentials.getUsername(), "repost", "");
		tweetRepository.save(reposted);
		return tweetMapper.toRepostDto(repost);
	}

	public List<HashtagDto> getTags(int id) {

		List<HashtagDto> dto = tweetRepository.findByIdAndDeletedFalse(id).getTagsUsed().stream()
				.map(tagMapper::toHashtagDto).collect(Collectors.toList());
		System.out.println(tweetRepository.findById(id).getTagsUsed());
		return dto;
	}

	public List<UserDto> getLikes(int id) {
		return tweetRepository.findByIdAndDeletedFalse(id).getLikes().stream().map(userMapper::toUserDto)
				.collect(Collectors.toList());
	}

///////////////////////////////////////////////////////////  DOESN'T WORK!!!!!  ////////////////////////////////////////////

//	 public Context context(int id) {
//		 Context context = new Context();
//		 Tweet target = tweetRepository.findByIdAndDeletedFalse(id);
//		 switch (target.getType()) {
//			case "simple":
//				context.setTarget(tweetMapper.toSimpleDto(target));
//				break;
//			case "reply":
//				context.setTarget(tweetMapper.toReplyDto(target));
//				break;
//			case "repost":
//				context.setTarget(tweetMapper.toRepostDto(target));
//				break;
//			}
//		 boolean top = false;
//		 Tweet upward  = target.getInReplyTo();
//		 while(top == false){
//			 if(upward != null){
//				 if(upward.getType() == "reply"){
//					 context.getBefore().add(tweetMapper.toReplyDto(upward));
//				 } else {
//					 context.getBefore().add(tweetMapper.toSimpleDto(upward));
//				 }
//				 upward = upward.getInReplyTo();
//			 } else
//				 top = true; 
//		 }
//
//		 traverseTweets(target, context);
//		 
//		 Collections.sort(context.getBefore(), new TweetCompare());
//		 Collections.sort(context.getAfter(), new TweetCompare());
//		 return context;
//	 }
	
///////////////////////////////////////////////////////////  DOESN'T WORK!!!!  ////////////////////////////////////////////

	public List<ReplyDto> getReplies(int id) {
		List<Tweet> replies = tweetRepository.findByIdAndDeletedFalse(id).getReplies();
		if (replies.isEmpty())
			return null;
		else {
			List<ReplyDto> replyDto = replies.stream().map(tweetMapper::toReplyDto).collect(Collectors.toList());
			return replyDto;
		}
	}

	public List<RepostDto> getReposts(int id) {
		List<Tweet> reposts = tweetRepository.findByIdAndDeletedFalse(id).getReposts();
		List<RepostDto> repostDto = reposts.stream().map(tweetMapper::toRepostDto).collect(Collectors.toList());
		return repostDto;
	}

	public List<UserDto> mentions(int id) {
		return tweetRepository.findByIdAndDeletedFalse(id).getMentions().stream().map(userMapper::toUserDto)
				.collect(Collectors.toList());
	}
	//same as method from userservice
	public boolean credentialCheck(int id, Credentials credentials) {
		if (tweetRepository.findById(id).getAuthor().getCredentials().equals(credentials))
			return true;
		else
			return false;
	}
// Identifies 
	public List<Hashtag> tagSearch(String content) {
		List<Hashtag> tags = new ArrayList<>();
		for (String retval : content.split("(?=#)")) {
			if (retval.startsWith("#")) {
				Hashtag find = tagRepository.findByLabel(retval.substring(1));
				if (find == null) {

					Hashtag temp = new Hashtag();
					temp.setLabel(retval.substring(1));
					tagRepository.save(temp);
					tags.add(temp);
				} else {

					tags.add(find);
					find.setLastUsed(new Timestamp(System.currentTimeMillis()));
					tagRepository.save(find);
				}
			}
		}
		return tags;
	}
//same but for mentions
	public List<User> mentionSearch(String content) {
		List<User> mentions = new ArrayList<>();
		for (String retval : content.split("(?=@)")) {
			if (retval.startsWith("@")) {
				User find = userRepository.findByUname(retval.substring(1));
				if (find != null) {
					mentions.add(find);
				}
			}
		}
		return mentions;
	}
	
	public void createTweet(Tweet posted, String username, String type, String content) {
		User author = userRepository.findByUname(username);
		
		posted.setAuthor(author);
		posted.setType(type);
		if(type == "simple" || type == "reply"){
			posted.setContent(content);
			posted.setTagsUsed(tagSearch(content));
			posted.setMentions(mentionSearch(content));
			}
		author.getTweets().add(posted);
		userRepository.saveAndFlush(author);
		tweetRepository.saveAndFlush(posted);
		
	}

	public void traverseTweets(Tweet tweet, Context context){
		int replyCount = tweet.getReplies().size();
		if(replyCount == 0){
			
		} else {
			for(int i = 0; i < replyCount; i++){
				Tweet reply = tweet.getReplies().get(i);
				context.getAfter().add(tweetMapper.toReplyDto(reply));
				traverseTweets(reply, context);
			}
		}
	}
	
}
