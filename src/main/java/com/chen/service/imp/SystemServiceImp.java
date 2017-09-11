package com.chen.service.imp;

import com.chen.dao.System;
import com.chen.repository.SystemRepository;
import com.chen.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by 陈忠意 on 2017/8/24.
 */
@Service
public class SystemServiceImp implements SystemService{

    @Autowired
    private SystemRepository systemRepository;

    public long add(System system){
        return systemRepository.add(system);
    }

    public void delete(long id){
        systemRepository.delete(id);
    }

    public void update(System system){
        systemRepository.update(system);
    }

    public System findOne(long id){
        return systemRepository.findOne(id);
    }

    public System findOne(String code){
        return systemRepository.findOne(code);
    }

    public List<System> findAll(){
        return systemRepository.findAll();
    }

    public boolean hasMatchCode(String code){
        return findOne(code) != null;
    }
}
