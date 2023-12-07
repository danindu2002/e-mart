package com.emart.emart.mappers;

import com.emart.emart.dtos.UserDto;
import com.emart.emart.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;
@Mapper
public interface UserMapper {
    UserMapper userMapper = Mappers.getMapper(UserMapper.class);
    UserDto mapToUserDto(User user);
    List<UserDto> mapToUserDtoList(List<User> userList);

}
