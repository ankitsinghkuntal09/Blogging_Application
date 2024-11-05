package com.blog.services.impl;

import com.blog.config.AppConstants;
import com.blog.entities.Role;
import com.blog.entities.User;
import com.blog.exceptions.ExistingRecordException;
import com.blog.exceptions.ResourceNotFoundException;
import com.blog.payloads.UserDto;
import com.blog.repositories.RoleRepo;
import com.blog.repositories.UserRepo;
import com.blog.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    //Note: We are able to make object of UserRepo even tho UserRepo is interface because whenever springboot
    //application is being started..all repo classes are scanned and its Implementation classes are created at runtime
    //We can see repoTest() test in BlogAppApisApplicationTests where we are printing className and packageName.
    //ClassName is : jdk.proxy2.$Proxy109  packageName is : jdk.proxy2
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private RoleRepo roleRepo;

    @Override
    public UserDto createUser(UserDto userDto) {
        User user = this.dtoToUser(userDto);
        try {
            User savedUser = userRepo.save(user);
            return this.usertoDto(savedUser);
        }catch(Exception e)
        {
            throw new ExistingRecordException(userDto.getEmail());
        }
    }

    @Override
    public UserDto updateUser(UserDto userDto, Integer userId) {
        User user = this.userRepo.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("User"," Id ",userId));
        User updatedUser = null;
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setAbout(userDto.getAbout());
        updatedUser = this.userRepo.save(user);
        return this.usertoDto(updatedUser);
    }

    @Override
    public UserDto getUserById(Integer userId) {
        User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User"," Id ", userId));
        return this.usertoDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = this.userRepo.findAll();
        List<UserDto> userDtos = users.stream().map(user-> this.usertoDto(user)).collect(Collectors.toList());
        return userDtos;
    }

    @Override
    public void deleteUser(Integer userId) {
        User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","Id", userId));
        this.userRepo.deleteById(userId);
    }

    @Override
    public UserDto createUserWithJwt(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Role role = this.roleRepo.findById(AppConstants.NORMAL_USER).get();

        user.getRoles().add(role);
        return this.usertoDto(userRepo.save(user));
    }

    //We can use like below 2 methods to get Dto from User or User from Dto. But better approach is to use modelmapper. I
    //have used modelmapper below these commented methods.
    //    private User dtoToUser(UserDto userDto)
    //    {
    //        User user = new User();
    //        user.setId(userDto.getId());
    //        user.setName(userDto.getName());
    //        user.setEmail(userDto.getEmail());
    //        user.setPassword(userDto.getPassword());
    //        user.setAbout(userDto.getAbout());
    //        return user;
    //    }
    //
    //    private UserDto usertoDto(User user)
    //    {
    //        UserDto userDto = new UserDto();
    //        userDto.setId(user.getId());
    //        userDto.setName(user.getName());
    //        userDto.setEmail(user.getEmail());
    //        userDto.setPassword(user.getPassword());
    //        userDto.setAbout(user.getAbout());
    //        return userDto;
    //    }

    private User dtoToUser(UserDto userDto){
        User user = this.modelMapper.map(userDto,User.class);
        return user;
    }

    private UserDto usertoDto(User user){
        UserDto userDto = this.modelMapper.map(user,UserDto.class);
        return userDto;
    }

}
