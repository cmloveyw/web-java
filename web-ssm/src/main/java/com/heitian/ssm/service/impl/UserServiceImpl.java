package com.heitian.ssm.service.impl;

import com.heitian.ssm.dao.UserDao;
import com.heitian.ssm.model.User;
import com.heitian.ssm.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Zhangxq on 2016/7/15.
 */

@Service("userService")
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl implements UserService {
    
    @Resource
    private UserDao userDao;

    public User getUserById(Long userId) {
        return userDao.selectUserById(userId);
    }

    @Override
    public void saveUser(String userName, String userPhone, String userEmail) {
        userDao.saveUser(userName,userPhone,userEmail);
    }

    @Override
    public void updateUser(String userName, String userPhone, String userEmail, Long id) {
        userDao.updateUser(userName,userPhone,userEmail,id);
    }

    @Override
    public void delete(Long id) {
        userDao.deleteUser(id);
    }


    public User getUserByPhoneOrEmail(String emailOrPhone, Short state) {
        return userDao.selectUserByPhoneOrEmail(emailOrPhone,state);
    }
    
    public List<User> getAllUser(String userName) {
        return userDao.selectAllUser(userName);
    }
}
