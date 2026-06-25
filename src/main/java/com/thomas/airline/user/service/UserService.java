package com.thomas.airline.user.service;

import com.thomas.airline.common.enums.RoleName;
import com.thomas.airline.common.enums.UserStatus;
import com.thomas.airline.exception.UserAlreadyExistException;
import com.thomas.airline.exception.UserNotFoundException;
import com.thomas.airline.user.dto.UserRequestDto;
import com.thomas.airline.user.dto.UserResponseDto;
import com.thomas.airline.user.entity.User;
import com.thomas.airline.user.mapper.UserMapper;
import com.thomas.airline.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public UserResponseDto createUser(UserRequestDto requestDto){
        if(userRepository.existsByEmail(requestDto.getEmail())){
            throw new UserAlreadyExistException("Email is already in use.");
        }
        if(userRepository.existsByPhoneNumber(requestDto.getPhoneNumber())){
            throw new UserAlreadyExistException("Phone Number is already in use.");
        }
        User user=userMapper.requestDtoToUser(requestDto);
        user.setStatus(UserStatus.ACTIVE);
        user.setRole(RoleName.USER);
        LocalDateTime currentTime=LocalDateTime.now();
        user.setCreatedAt(currentTime);
        user.setUpdatedAt(currentTime);
        User savedUser=userRepository.save(user);
        UserResponseDto responseDto=userMapper.userToResponseDto(savedUser);
        return responseDto;
    }
    public List<UserResponseDto> getAllUsers(){
        List<User>users=userRepository.findAll();
        List<UserResponseDto> responseDtos=new ArrayList<>();
        for(User user:users){
            UserResponseDto responseDto=userMapper.userToResponseDto(user);
            responseDtos.add(responseDto);
        }
        return responseDtos;
    }
    public UserResponseDto getUserById(Long id){
        User user=userRepository.findById(id).orElseThrow(()->new UserNotFoundException("User doesn't exist."));
        UserResponseDto responseDto=userMapper.userToResponseDto(user);
        return responseDto;
    }
    public UserResponseDto updateUser(Long id, UserRequestDto requestDto){
        User user=userRepository.findById(id).orElseThrow(()-> new UserNotFoundException("User doesn't exist."));
        if(!user.getEmail().equals(requestDto.getEmail())){
            if(userRepository.existsByEmail(requestDto.getEmail())){
                throw new UserAlreadyExistException("Email already in use.");
            }
            user.setEmail(requestDto.getEmail());
        }
        if(!user.getPhoneNumber().equals(requestDto.getPhoneNumber())){
            if(userRepository.existsByPhoneNumber(requestDto.getPhoneNumber())){
                throw  new UserAlreadyExistException("Phone number is already in use.");
            }
            user.setPhoneNumber(requestDto.getPhoneNumber());
        }
        user.setFirstName(requestDto.getFirstName());
        user.setLastName(requestDto.getLastName());
        user.setPassword(requestDto.getPassword());
        LocalDateTime currentTime=LocalDateTime.now();
        user.setUpdatedAt(currentTime);
        User savedUser=userRepository.save(user);
        UserResponseDto responseDto=userMapper.userToResponseDto(savedUser);
        return responseDto;
    }
    public void deleteUser(Long id){
        User user=userRepository.findById(id).orElseThrow(()-> new UserNotFoundException("User not exist."));
        user.setStatus(UserStatus.INACTIVE);
        LocalDateTime currentTime=LocalDateTime.now();
        user.setUpdatedAt(currentTime);
        User savedUser=userRepository.save(user);
    }
}
