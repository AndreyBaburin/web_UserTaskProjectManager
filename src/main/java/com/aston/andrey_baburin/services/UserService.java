package com.aston.andrey_baburin.services;


import com.aston.andrey_baburin.entity.User;
import com.aston.andrey_baburin.entity.dto.UserDto;

import java.util.List;

public interface UserService {
    void createUser(UserDto userDto);

    List<UserDto> getAllUsers();
    UserDto getUserById(int id);

    void updateUser(UserDto userDto);

    void deleteUserById(int id);


}





