package com.chen.repository.impl;

import com.chen.dao.System;
import com.chen.repository.SystemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * Created by 陈忠意 on 2017/8/24.
 */
@Repository
public class SystemRepositoryImp implements SystemRepository{

    @Autowired
    private JdbcTemplate template;

    private static final String TABLE_NAME = "systems";

    private static final String ADD =
            "insert into " + TABLE_NAME + " (code, description) values(?, ?)";
    public long add(System system){
        KeyHolder keyHolder = new GeneratedKeyHolder();
        PreparedStatementCreator preparedStatementCreator = con ->{
            PreparedStatement ps = con.prepareStatement(ADD, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, system.getCode());
            ps.setString(2, system.getDescription());
            return ps;
        };
        template.update(preparedStatementCreator, keyHolder);
        return keyHolder.getKey().longValue();
    }

    private static final String DELETE =
            "delete from " + TABLE_NAME + " where id = ?";
    public void delete(long id){
        template.update(DELETE,
                id);
    }

    private static final String UPDATE =
            "update " + TABLE_NAME + " set description = ?, status = ? where id = ?";
    public void update(System system){
        template.update(UPDATE,
                system.getDescription(),
                system.getStatus(),
                system.getId());
    }

    private static final String FIND_ONE =
            "select * from " + TABLE_NAME + " where id = ?";
    public System findOne(long id){
        List<System> systems = template.query(FIND_ONE,
                new SystemRowMapper(),
                id);
        if(systems != null && systems.size() > 0)   return systems.get(0);
        return null;
    }

    private static final String FIND_ONE_BY_CODE =
            "select * from " + TABLE_NAME + " where code = ?";
    public System findOne(String code){
        List<System> systems = template.query(FIND_ONE_BY_CODE,
                new SystemRowMapper(),
                code);
        if(systems != null && systems.size() > 0)   return systems.get(0);
        return null;
    }

    private static final String FIND_ALL =
            "select * from " + TABLE_NAME;
    public List<System> findAll(){
        return template.query(FIND_ALL,
                new SystemRowMapper());
    }

    private static final class SystemRowMapper implements RowMapper<System>{

        public System mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new System(
                    rs.getLong("id"),
                    rs.getString("code"),
                    rs.getString("description"),
                    rs.getInt("status")
            );
        }
    }
}
