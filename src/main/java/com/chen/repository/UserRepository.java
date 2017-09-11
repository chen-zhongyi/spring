package com.chen.repository;

import com.chen.dao.Users;

/**
 * Created by 陈忠意 on 2017/8/17.
 */
public interface UserRepository extends BaseRepository<Users>{

    long add(Users user, long createBy);

    Users findOne(String userName);

    Users findByUserName(String userName);

    void  updatePassword(long id, String password);

    Users findByUserNameAndPassword(String userName, String password);
}
