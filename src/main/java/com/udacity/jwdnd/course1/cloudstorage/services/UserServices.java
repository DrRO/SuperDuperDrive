package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.Mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.Models.UserModel;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;

@Service
public class UserServices {

    private  UserMapper userMapper;
    private  HashService hashService;

    public UserServices(UserMapper userMapper, HashService hashService) {
        this.userMapper = userMapper;
        this.hashService = hashService;
    }
// Create new user account
    public int NewUserAcount(UserModel newUser) {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        String encodedSalt = Base64.getEncoder().encodeToString(salt);
        String hashedPassword = hashService.getHashedValue(newUser.getPassword(), encodedSalt);
        return userMapper.insertUser(new UserModel(null, newUser.getUsername(), encodedSalt, hashedPassword, newUser.getFirstName(), newUser.getLastName()));
    }

    // check if user account exist or not
    public boolean checkUserAvaliability(String username) {

        return userMapper.getUser(username) == null;
    }

// Get user info form UserMapper
    public UserModel getUser(String username) {

        return userMapper.getUser(username);
    }

    public UserModel getUserId(Integer userId) {

        return userMapper.getUserId(userId);
    }
}
