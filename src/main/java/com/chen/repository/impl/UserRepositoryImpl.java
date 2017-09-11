package com.chen.repository.impl;

import com.chen.dao.Users;
import com.chen.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;

/**
 * Created by 陈忠意 on 2017/8/17.
 */
@Repository
public class UserRepositoryImpl extends BaseRepositoryImp<Users> implements UserRepository{

    public UserRepositoryImpl(){
        super(TABLE_NAME, Users.class);
    }

    public static void main(String[] args){
        UserRepository userRepository = new UserRepositoryImpl();
        Users users = new Users();
        users.setUserName("callni");
        users.setPwd("123456");
        users.setRealName("chen zhongyi");
        users.setStatus(1);
        userRepository.add(users);
    }

    @Autowired
    private JdbcTemplate template;

    private static final String TABLE_NAME = "users";

    private static final String ADD =
            "insert into " + TABLE_NAME + " " +
                    "(userName, pwd, realName, mail, role, `right`, otherStr, createAt, createBy) " +
                    "values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    public long add(Users users, long createBy){
        KeyHolder keyHolder = new GeneratedKeyHolder();
        PreparedStatementCreator preparedStatementCreator = con ->{
            PreparedStatement ps = con.prepareStatement(ADD, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, users.getUserName());
            ps.setString(2, users.getPwd());
            ps.setString(3, users.getRealName());
            ps.setString(4, users.getMail());
            ps.setString(5, users.getRole());
            ps.setString(6, users.getRight());
            ps.setString(7, users.getOtherStr());
            ps.setDate(8, new Date(System.currentTimeMillis()));
            ps.setLong(9, createBy);
            return ps;
        };
        template.update(preparedStatementCreator, keyHolder);
        return keyHolder.getKey().longValue();
    }

    private static final String UPDATE =
            "update " + TABLE_NAME + " set " +
                    "realName = ?, mail = ?, loginIp = ?, lastLoginTime = ?, count = ?, role = ?, `right` = ?, status = ? " +
                    "where id = ?";
    public void update(Users users){
        template.update(UPDATE,
                users.getRealName(),
                users.getMail(),
                users.getLoginIp(),
                users.getLastLoginTime(),
                users.getCount(),
                users.getRole(),
                users.getRight(),
                users.getStatus(),
                users.getId());
    }

    private static final String DELETE =
            "delete from " + TABLE_NAME + " where id = ?";
    public void delete(long id){
        template.update(DELETE,
                id);
    }

    private static final String FIND_ONE =
            "select * from " + TABLE_NAME + " where id = ?";
    public Users findOne(long id){
        List<Users> users = template.query(FIND_ONE,
                new UserRowMapper(),
                id);
        if(users != null && users.size() > 0)
            return users.get(0);
        return null;
    }

    private static final String FIND_ONE_BY_USERNAME =
            "select * from " + TABLE_NAME + " where userName = ?";
    public Users findOne(String userName){
        List<Users> users = template.query(FIND_ONE_BY_USERNAME,
                new UserRowMapper(),
                userName);
        if(users != null && users.size() > 0)
            return users.get(0);
        return null;
    }

    private static final String FIND_ALL =
            "select * from " + TABLE_NAME;
    public List<Users> findAll(){
        return template.query(FIND_ALL,
                new UserRowMapper());
    }

    private static final String FIND_BY_USERNAME =
            "select * from " + TABLE_NAME + " where userName = ?";
    public Users findByUserName(String userName){
        List<Users> user = template.query(FIND_BY_USERNAME,
                new UserRowMapper(),
                userName);
        if(user != null && user.size() > 0) return user.get(0);
        return null;
    }

    private static final String UPDATE_PASSWORD =
            "update " + TABLE_NAME + " set pwd = ? where id = ?";
    public void updatePassword(long id, String pwd){
        template.update(UPDATE_PASSWORD,
                pwd,
                id);
    }

    private static final String FIND_BY_USERNAME_AND_PASSWORD =
            "select * from " + TABLE_NAME + " where userName = ? and pwd = ?";
    public Users findByUserNameAndPassword(String userName, String pwd){
        List<Users> users = template.query(FIND_BY_USERNAME_AND_PASSWORD,
                new UserRowMapper(),
                userName,
                pwd);
        if(users != null && users.size() > 0)   return users.get(0);
        return null;
    }

    private static final class UserRowMapper implements RowMapper<Users>{

        public Users mapRow(ResultSet rs, int rowNum) throws SQLException{
            return new Users(
                    rs.getLong("id"),
                    rs.getString("userName"),
                    rs.getString("pwd"),
                    rs.getString("realName"),
                    rs.getString("mail"),
                    rs.getString("loginIp"),
                    rs.getDate("lastLoginTime"),
                    rs.getInt("count"),
                    rs.getString("role"),
                    rs.getString("right"),
                    rs.getString("otherStr"),
                    rs.getDate("createAt"),
                    rs.getInt("createBy"),
                    rs.getInt("status")
            );
        }
    }
}
