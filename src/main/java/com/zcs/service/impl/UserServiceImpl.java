package com.zcs.service.impl;

import com.zcs.dao.UserRepository;
import com.zcs.po.User;
import com.zcs.service.UserService;
import com.zcs.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;



    //查询用户
    @Override
    public User checkUser(String username, String password) {
        User user = userRepository.findByUsernameAndPassword(username, MD5Utils.code(password));
        return user;
    }
}
