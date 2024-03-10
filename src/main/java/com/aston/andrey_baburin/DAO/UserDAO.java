package com.aston.andrey_baburin.DAO;



import com.aston.andrey_baburin.entity.User;

import java.util.List;

public interface UserDAO {
    void addUser(User user);

    List<User> getAllUsers();

    User getUserById(int id);
    void updateUser(User user);

    void deleteUserById(int id);
}
