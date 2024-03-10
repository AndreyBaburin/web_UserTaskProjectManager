package com.aston.andrey_baburin.processor;

import com.aston.andrey_baburin.JDBCImpl.UserJDBC;
import com.aston.andrey_baburin.entity.User;
import com.aston.andrey_baburin.services.UserService;


import java.util.List;


public class UserProcessor implements UserService {
    UserJDBC userJDBC;

    public UserProcessor(UserJDBC userJDBC) {
        this.userJDBC = userJDBC;
    }

    @Override
    public void createUser(User user) {
        userJDBC.addUser(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userJDBC.getAllUsers();
    }

    @Override
    public User getUserById(int id) {
        return userJDBC.getUserById(id);
    }

    @Override
    public void updateUser(User user) {
        userJDBC.updateUser(user);
    }

    @Override
    public void deleteUserById(int id) {
        userJDBC.deleteUserById(id);
    }

}
