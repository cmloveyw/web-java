package com.heitian.ssm.service;

import com.heitian.ssm.model.User;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Zhangxq on 2016/7/15.
 */
public interface UserService {

    List<User> getAllUser(String userName);

    User getUserByPhoneOrEmail(String emailOrPhone, Short state);

    User getUserById(Long userId);

    /*void saveUser(User user);*/

    void saveUser(String userName, String userPhone, String userEmail);

    void updateUser(String userName, String userPhone, String userEmail, Long id);

    void delete(Long id);
}
