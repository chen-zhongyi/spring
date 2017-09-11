package com.chen.validator;

import com.chen.dao.Users;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

/**
 * Created by 陈忠意 on 2017/8/23.
 */
public class UserValidator {

    @Size(min = 6, max = 44)
    private String userName;
    @Size(min = 6, max = 44)
    private String pwd;
    @Size(min = 6, max = 44)
    private String newPwd;
    @Size(min = 2, max = 44)
    private String realName;
    private String mail;
    private String role;
    private String right;
    @Min(0)
    @Max(1)
    private Integer status;

    public Users buildUser(){
        Users user = new Users();
        user.setUserName(userName);
        user.setPwd(pwd);
        user.setRealName(realName);
        user.setMail(mail);
        user.setRight(right);
        user.setRole(role);
        user.setStatus(status);
        return user;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public String getNewPwd() {
        return newPwd;
    }

    public void setNewPwd(String newPwd) {
        this.newPwd = newPwd;
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
}
