package com.cooksys.twremix.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.cooksys.twremix.dto.UserDto;
import com.cooksys.twremix.pojo.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
	
	@Mapping(source = "uname", target = "uname")
	UserDto toUserDto(User user);
	@Mapping(source = "uname", target = "uname")
	User toUser(UserDto userDto);
}