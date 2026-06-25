package com.thomas.airline.user.mapper;

import com.thomas.airline.user.dto.UserRequestDto;
import com.thomas.airline.user.dto.UserResponseDto;
import com.thomas.airline.user.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public User requestDtoToUser(UserRequestDto requestDto){
        User user= new User();
        user.setFirstName(requestDto.getFirstName());
        user.setLastName(requestDto.getLastName());
        user.setEmail(requestDto.getEmail());
        user.setPassword(requestDto.getPassword());
        user.setPhoneNumber(requestDto.getPhoneNumber());
        return  user;
    }
    public UserResponseDto userToResponseDto(User user){
        UserResponseDto responseDto=new UserResponseDto();
        responseDto.setId(user.getId());
        responseDto.setFirstName(user.getFirstName());
        responseDto.setLastName(user.getLastName());
        responseDto.setEmail(user.getEmail());
        responseDto.setPhoneNumber(user.getPhoneNumber());
        responseDto.setStatus(user.getStatus());
        responseDto.setRole(user.getRole());
        responseDto.setCreatedAt(user.getCreatedAt());
        responseDto.setUpdatedAt(user.getUpdatedAt());
        return responseDto;
    }
}
