package com.chen.repository;

import com.chen.dao.System;

import java.util.List;

/**
 * Created by 陈忠意 on 2017/8/24.
 */
public interface SystemRepository {

    long add(System system);

    void delete(long id);

    void update(System system);

    System findOne(long id);

    System findOne(String code);

    List<System> findAll();
}
