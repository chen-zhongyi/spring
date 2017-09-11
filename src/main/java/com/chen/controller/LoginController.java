package com.chen.controller;

import com.chen.dao.Users;
import com.chen.service.UserService;
import com.chen.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.sql.Date;
import java.util.Map;

/**
 * Created by 陈忠意 on 2017/8/24.
 */

@Controller
@RequestMapping( value = "/api/users", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class LoginController extends BaseController{

    @Autowired
    private UserService userService;

    @RequestMapping( value = "/login", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> login(HttpServletRequest request, HttpSession session, @Valid @RequestBody UserValidator userValidator, Errors errors){
        if(errors.hasErrors()){
            Map<String, String> error = createErrors(errors);
            return createResponse(FAIL, error.get("msg"), error);
        }
        if(userValidator.getUserName() == null || userValidator.getPwd() == null){
            return createResponse(FAIL, "用户名或密码不能为空", null);
        }
        if(userService.hasMatchUser(userValidator.getUserName()) == false){
            return createResponse(FAIL, "用户名不存在或用户名密码不匹配", null);
        }
        Users temp = userService.findOne(userValidator.getUserName());
        if(temp.getStatus() == 0){
            return createResponse(FAIL, "给用户已被禁用，请联系管理员", null);
        }
        if(userService.hasMatchPassword(userValidator.getUserName(), userValidator.getPwd() + temp.getOtherStr()) == false){
            return createResponse(FAIL, "用户名不存在或用户名密码不匹配", null);
        }
        Users user = userService.findOne(userValidator.getUserName());
        user.setLastLoginTime(new Date(System.currentTimeMillis()));
        user.setCount(user.getCount() + 1);
        user.setLoginIp(request.getRemoteAddr());
        userService.updateUser(user);
        session.setAttribute("user", user);
        return createResponse(SUCCESS, "登录成功", user);
    }

    @RequestMapping (value = "/register", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> register(HttpSession session, @Valid @RequestBody UserValidator userValidator, Errors errors){
        if(errors.hasErrors()){
            Map<String, String> error = createErrors(errors);
            return createResponse(FAIL, error.get("msg"), error);
        }
        if(userValidator.getUserName() == null || userValidator.getPwd() == null || userValidator.getRealName() == null){
            return createResponse(FAIL, "用户名或密码或昵称不能为空", null);
        }
        userValidator.setStatus(1);
        Users user = userValidator.buildUser();
        user.setPwd(getMd5Password(user.getPwd() + getMd5OtherString()));
        long id = userService.addUser(user, -1);
        user = userService.findOne(id);
        session.setAttribute("user", user);
        return createResponse(SUCCESS, "注册成成功", user);
    }
}
