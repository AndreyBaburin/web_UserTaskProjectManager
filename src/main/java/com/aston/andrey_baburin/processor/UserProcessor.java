package com.aston.andrey_baburin.processor;

import com.aston.andrey_baburin.JDBCImpl.UserJDBC;
import com.aston.andrey_baburin.entity.User;
import com.aston.andrey_baburin.entity.dto.UserDto;
import com.aston.andrey_baburin.entity.mapper.UserMapper;
import com.aston.andrey_baburin.services.UserService;


import java.util.List;


public class UserProcessor implements UserService {
    UserJDBC userJDBC;

    public UserProcessor(UserJDBC userJDBC) {
        this.userJDBC = userJDBC;
    }

    @Override
    public void createUser(UserDto userDto) {
        User user = UserMapper.INSTANCE.toEntity(userDto);
        userJDBC.addUser(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userJDBC.getAllUsers();
        return UserMapper.INSTANCE.toDtoList(users);
    }

    @Override
    public UserDto getUserById(int id) {
        return UserMapper.INSTANCE.toDto(userJDBC.getUserById(id));
    }

    @Override
    public void updateUser(UserDto userDto) {
        User user = UserMapper.INSTANCE.toEntity(userDto);
        userJDBC.updateUser(user);
    }

    @Override
    public void deleteUserById(int id) {

        userJDBC.deleteUserById(id);
    }

}
