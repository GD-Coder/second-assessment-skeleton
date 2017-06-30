package com.cooksys.twremix.mapper;

import org.mapstruct.Mapper;

import com.cooksys.twremix.dto.HashtagDto;
import com.cooksys.twremix.pojo.Hashtag;


@Mapper(componentModel = "spring")
public interface HashtagMapper {
	
	HashtagDto toHashtagDto(Hashtag hashtag);
	
	Hashtag toHashtab(HashtagDto hashtagDto);

}