package com.zcs.dao;

import com.zcs.po.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User,Long> {

    User findByUsernameAndPassword(String username,String password);

}
