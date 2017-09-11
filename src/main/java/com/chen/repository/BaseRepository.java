package com.chen.repository;

import java.util.List;

/**
 * Created by 陈忠意 on 2017/8/31.
 */
public interface BaseRepository<T> {

    long add(T model);

    void delete(long id);

    void update(T model);

    T findOne(long id);

    List<T> findAll();
}
