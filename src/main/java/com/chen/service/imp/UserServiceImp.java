package com.chen.service.imp;

import com.chen.dao.Users;
import com.chen.repository.UserRepository;
import com.chen.service.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by 陈忠意 on 2017/8/18.
 */
@Service
public class UserServiceImp implements UserService {

    @Autowired
    private UserRepository userRepository;

    public boolean hasMatchUser(String userName){
        return userRepository.findByUserName(userName) != null;
    }

    public boolean hasMatchUser(long id){
        return userRepository.findOne(id) != null;
    }

    public boolean hasMatchPassword(String userName, String password){
        return userRepository.findByUserNameAndPassword(userName, DigestUtils.md5Hex(password)) != null;
    }

    public long addUser(Users user, long createBy){
        if(createBy == -1){
            return userRepository.add(user);
        }
        return userRepository.add(user, createBy);
    }

    public Users findOne(long id){
        return userRepository.findOne(id);
    }

    public Users findOne(String userName){
        return userRepository.findOne(userName);
    }

    public void updateUser(Users user){
        userRepository.update(user);
    }

    public void updatePassword(long id, String password){
        userRepository.updatePassword(id, password);
    }

    public void delete(long id){
        userRepository.delete(id);
    }
}
