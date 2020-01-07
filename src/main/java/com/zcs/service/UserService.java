package com.zcs.service;

import com.zcs.po.User;

/**
 * @description: TODO
 * @author: mufeng
 */
public interface UserService {

    User checkUser(String username, String password);

}
