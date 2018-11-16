package com.heitian.ssm.dao;

import com.heitian.ssm.model.User;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Zhangxq on 2016/7/15.
 */

@Repository
public interface UserDao {

    User selectUserById(@Param("userId") Long userId);

    User selectUserByPhoneOrEmail(@Param("emailOrPhone") String emailOrPhone, @Param("state") Short state);

    List<User> selectAllUser(@Param("userName") String userName);

    void saveUser(@Param("userName") String userName, @Param("userPhone") String userPhone, @Param("userEmail") String userEmail);

    void updateUser(@Param("userName") String userName, @Param("userPhone") String userPhone, @Param("userEmail") String userEmail, @Param("id") Long id);

    void deleteUser(@Param("id") Long id);
}
