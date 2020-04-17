package com.vua.service;


import com.vua.entity.User;
import com.vua.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: miaosha
 * @description:
 * @author: vua
 * @create: 2020-02-29 15:22
 */
@Service
@Slf4j
public class UserService{
    @Autowired
    UserRepository userRepository;

    public User insertUser(User user) {
        return userRepository.save(user);
    }

    public User verifyLogin(String name, String password) {
        return userRepository.findByNameAndPassword(name, password);
    }
    public List<User> findAll(){
        return userRepository.findAll();
    }
}
