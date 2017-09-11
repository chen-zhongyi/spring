package com.chen.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.jdbc.core.RowMapper;

import java.io.Serializable;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by 陈忠意 on 2017/8/17.
 */
public class Users implements Serializable{

    private long id;
    private String userName;
    @JsonIgnore
    private String pwd;
    private String realName;
    private String mail;
    @JsonIgnore
    private String loginIp;
    @JsonIgnore
    private Date lastLoginTime;
    @JsonIgnore
    private int count;
    private String role;
    private String right;
    @JsonIgnore
    private Date createAt;
    @JsonIgnore
    private int createBy;
    @JsonIgnore
    private int status;
    @JsonIgnore
    private String otherStr;

    public Users(){};

    public Users(long id, String userName, String pwd, String realName, String mail, String loginIp, Date lastLoginTime,
                 int count, String role, String right, String otherStr, Date createAt, int createBy, int status){
        this.id = id;
        this.userName = userName;
        this.pwd = pwd;
        this.realName = realName;
        this.mail = mail;
        this.loginIp = loginIp;
        this.lastLoginTime = lastLoginTime;
        this.count = count;
        this.role = role;
        this.right = right;
        this.otherStr = otherStr;
        this.createAt = createAt;
        this.createBy = createBy;
        this.status = status;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public int getCreateBy() {
        return createBy;
    }

    public void setCreateBy(int createBy) {
        this.createBy = createBy;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getOtherStr() {
        return otherStr;
    }

    public void setOtherStr(String otherStr) {
        this.otherStr = otherStr;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getRight() {
        return right;
    }

    public void setRight(String right) {
        this.right = right;
    }

    @Override
    public String toString(){
        return "userName = " + this.userName + ", " +
                "pwd = " + this.pwd + ", " +
                "realName = " + this.realName;
    }

    public static final class UserRowMapper implements RowMapper<Users> {

        public Users mapRow(ResultSet rs, int rowNum) throws SQLException {
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
