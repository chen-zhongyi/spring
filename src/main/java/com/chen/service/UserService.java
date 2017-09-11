package com.chen.service;

import com.chen.dao.Users;

/**
 * Created by 陈忠意 on 2017/8/18.
 */
public interface UserService {

    boolean hasMatchUser(String username);

    boolean hasMatchUser(long id);

    boolean hasMatchPassword(String username, String password);

    long addUser(Users user, long createBy);

    Users findOne(long id);

    void updateUser(Users user);

    void updatePassword(long id, String password);

    void delete(long id);

    Users findOne(String userName);
}
