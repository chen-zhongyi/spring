package com.chen.controller;

/**
 * Created by 陈忠意 on 2017/8/17.
 */

import com.chen.dao.Users;
import com.chen.service.UserService;
import com.chen.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Map;

@Controller
@RequestMapping( value = "/api/users", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class UserController extends BaseController{

    @Autowired
    private UserService userService;

    @RequestMapping( value = "", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> createUser(HttpSession session, @Valid @RequestBody UserValidator userValidator, Errors errors){
        if(errors.hasErrors()){
            Map<String, String> error = createErrors(errors);
            return createResponse(FAIL, error.get("msg"), error);
        }
        if(userValidator.getUserName() == null || userValidator.getPwd() == null || userValidator.getRealName() == null){
            return createResponse(FAIL, "用户名或密码或昵称不能为空", null);
        }
        Users loginUser = (Users) session.getAttribute("user");
        Users users = userValidator.buildUser();
        if(userService.hasMatchUser(users.getUserName()) == true){
            return createResponse(FAIL, "用户名已存在", null);
        }
        users.setOtherStr(getMd5OtherString());
        users.setPwd(getMd5Password(users.getPwd() + users.getOtherStr()));
        long id = userService.addUser(users, loginUser.getId());
        System.out.println("id = " + id);
        return createResponse(SUCCESS, "添加用户成功", userService.findOne(id));
    }

    @RequestMapping( value = "/{userId}", method = RequestMethod.PUT)
    public @ResponseBody Map<String, Object> updateUser(@PathVariable long userId, @Valid @RequestBody UserValidator userValidator, Errors errors){
        if(errors.hasErrors()){
            Map<String, String> error = createErrors(errors);
            return createResponse(FAIL, error.get("msg"), error);
        }
        if(userService.hasMatchUser(userId) == false){
            return createResponse(FAIL, "用户不存在", null);
        }
        Users temp = userService.findOne(userId);
        if(userValidator.getMail() != null)  temp.setMail(userValidator.getMail());
        if(userValidator.getRealName() != null)  temp.setRealName(userValidator.getRealName());
        if(userValidator.getRole() != null)  temp.setRole(userValidator.getRole());
        if(userValidator.getRight() != null) temp.setRight(userValidator.getRight());
        if(userValidator.getStatus() != null) temp.setStatus(userValidator.getStatus());
        userService.updateUser(temp);
        return createResponse(SUCCESS, "修改用户成功", userService.findOne(userId));
    }

    @RequestMapping( value = "/updatePassword/{userId}", method = RequestMethod.PUT)
    public @ResponseBody Map<String, Object> updatePassword(@PathVariable long userId, @Valid @RequestBody UserValidator userValidator, Errors errors){
        if(errors.hasErrors()){
            Map<String, String> error = createErrors(errors);
            return createResponse(FAIL, error.get("msg"), error);
        }
        if(userValidator.getPwd() == null || userValidator.getNewPwd() == null){
            return createResponse(FAIL, "旧密码或新密码不能为空", null);
        }
        if(userService.hasMatchUser(userId) == false){
            return createResponse(FAIL, "用户不存在或密码不匹配", null);
        }
        Users temp = userService.findOne(userId);
        if(userService.hasMatchPassword(temp.getUserName(), userValidator.getPwd() + temp.getOtherStr()) == false){
            return createResponse(FAIL, "用户不存在或密码不匹配", null);
        }
        userService.updatePassword(userId, getMd5Password(userValidator.getNewPwd() + temp.getOtherStr()));
        return createResponse(SUCCESS, "修改密码成功", userService.findOne(userId));
    }

    @RequestMapping( value = "/{userId}", method = RequestMethod.DELETE)
    public @ResponseBody Map<String, Object> delete(@PathVariable long userId){
        if(userService.hasMatchUser(userId) == false){
            return createResponse(FAIL, "用户不存在", null);
        }
        userService.delete(userId);
        return createResponse(SUCCESS, "删除成功", null);
    }

    @RequestMapping( value = "/{userId}", method = RequestMethod.GET)
    public @ResponseBody Map<String, Object> findOne(@PathVariable long userId){
        if(userService.hasMatchUser(userId) == false){
            return createResponse(FAIL, "用户不存在", null);
        }
        return createResponse(SUCCESS, "获取用户信息成功", userService.findOne(userId));
    }

}
