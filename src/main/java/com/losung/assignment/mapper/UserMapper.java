package com.losung.assignment.mapper;

import com.losung.assignment.models.dto.UserDto;
import com.losung.assignment.models.entity.User;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {

    public UserDto EntityToDto(User user){
        return UserDto.builder()
                .id(user.getId())
                .userName(user.getUserName())
                .password(user.getPassword())
                .build();
    }

    public User DtoToEntity(UserDto userDto){
        return User.builder()
                .id(userDto.getId())
                .userName(userDto.getUserName())
                .password(userDto.getPassword())
                .build();
    }
}
