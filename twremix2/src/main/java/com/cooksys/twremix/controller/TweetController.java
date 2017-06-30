package com.cooksys.twremix.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.twremix.dto.HashtagDto;
import com.cooksys.twremix.dto.ReplyDto;
import com.cooksys.twremix.dto.RepostDto;
import com.cooksys.twremix.dto.TweetDto;
import com.cooksys.twremix.dto.UserDto;
import com.cooksys.twremix.pojo.Credentials;
import com.cooksys.twremix.pojo.User;
import com.cooksys.twremix.repository.UserRepository;
import com.cooksys.twremix.service.TweetService;

@RestController
@RequestMapping("tweets")
public class TweetController {
	
	private TweetService tweetService;

	
	public TweetController(TweetService tweetService){
		super();
		this.tweetService = tweetService;
	}
	

	@GetMapping
	public List<TweetDto> index(){
		return tweetService.index();
	}
	
	@PostMapping
	public TweetDto post(@RequestBody Credentials credentials, String tweet, HttpServletResponse httpResponse){
		TweetDto dto = tweetService.post(credentials.getUsername().toString(), tweet);
		httpResponse.setStatus(HttpServletResponse.SC_CREATED);
		return dto;
	}
	
	@DeleteMapping("{id}")
	public TweetDto delete(@PathVariable int id, @RequestBody Credentials credentials){
		return tweetService.delete(id, credentials);
	}	

	@PostMapping("{id}/like")
	public void like(@PathVariable int id, @RequestBody Credentials credentials){
		tweetService.like(id, credentials);
	}
	
	@PostMapping("{id}/reply")
	public ReplyDto reply(@PathVariable int id, @RequestBody Credentials credentials, String reply, HttpServletResponse httpResponse){
		//if(credentialCheck(credentials.getUsername(), credentials)) {
		ReplyDto dto = tweetService.reply(id, credentials, reply);
		httpResponse.setStatus(HttpServletResponse.SC_CREATED);
		return dto;
//		} else {
//			return null;
//		}
	}

//	@PostMapping("{id}/reply")
//	public ReplyDto reply(@PathVariable int id, @RequestBody RequestWrapper wrapper, HttpServletResponse httpResponse){
//		ReplyDto dto = tweetService.reply(id, wrapper.getCredentials(), wrapper.getContent());
//		httpResponse.setStatus(HttpServletResponse.SC_CREATED);
//		return dto;
//	}	

	@PostMapping("{id}/repost")
	public RepostDto repost(@PathVariable int id, @RequestBody Credentials credentials, HttpServletResponse httpResponse){
		RepostDto dto = tweetService.repost(id, credentials);
		httpResponse.setStatus(HttpServletResponse.SC_CREATED);
		return dto;
	}	

	@GetMapping("{id}/tags")
	public List<HashtagDto> tags(@PathVariable int id){
		return tweetService.getTags(id);
	}	

	@GetMapping("{id}/likes")
	public List<UserDto> getLikes(@PathVariable int id){
		return tweetService.getLikes(id);
	}
	
	@GetMapping("{id}/replies")
	public List<ReplyDto> getReplies(@PathVariable int id){
		return tweetService.getReplies(id);
	}
	
	@GetMapping("{id}/reposts")
	public List<RepostDto> getReposts(@PathVariable int id){
		return tweetService.getReposts(id);
	}	
	
	@GetMapping("{id}/mentions")
	public List<UserDto> getMentions(@PathVariable int id){
		return tweetService.mentions(id);
	}
	
	
}
