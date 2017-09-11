package com.chen.controller;

import com.chen.dao.System;
import com.chen.service.SystemService;
import com.chen.validator.SystemValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * Created by 陈忠意 on 2017/8/24.
 */
@Controller
@RequestMapping (value = "/api/systems", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class SystemController extends BaseController{

    @Autowired
    private SystemService systemService;

    @RequestMapping (value = "", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> add(@Valid @RequestBody SystemValidator systemValidator, Errors errors){
        if(errors.hasErrors()){
            Map<String, String> error = createErrors(errors);
            return createResponse(FAIL, error.get("msg"), error);
        }
        if(systemValidator.getCode() == null){
            return createResponse(FAIL, "系统代码字段必须", null);
        }
        if(systemService.hasMatchCode(systemValidator.getCode()) == true){
            return createResponse(FAIL, "系统代码已存在", null);
        }
        System system = systemValidator.buildSystem();
        long id = systemService.add(system);
        return createResponse(SUCCESS, "添加系统成功", systemService.findOne(id));
    }

    @RequestMapping (value = "{id}", method = RequestMethod.DELETE)
    public @ResponseBody Map<String, Object> delete(@PathVariable long id){
        if(systemService.findOne(id) == null){
            return createResponse(FAIL, "系统不存在", null);
        }
        systemService.delete(id);
        return createResponse(SUCCESS, "删除系统成功", null);
    }

    @RequestMapping (value = "{id}", method = RequestMethod.PUT)
    public @ResponseBody Map<String, Object> update(@PathVariable long id, @Valid @RequestBody SystemValidator systemValidator, Errors errors){
        if(errors.hasErrors()){
            Map<String, String > error = createErrors(errors);
            return createResponse(FAIL, error.get("msg"), error);
        }
        System system = systemService.findOne(id);
        if(system == null){
            return createResponse(FAIL, "系统不存在", null);
        }
        if(systemValidator.getDescription() != null)    system.setDescription(systemValidator.getDescription());
        if(systemValidator.getStatus() != null) system.setStatus(systemValidator.getStatus());
        systemService.update(system);
        return createResponse(SUCCESS, "修改系统成功", system);
    }

    @RequestMapping (value = "{id}", method = RequestMethod.GET)
    public @ResponseBody Map<String, Object> findOne(@PathVariable long id){
        System system = systemService.findOne(id);
        if(system == null){
            return createResponse(FAIL, "系统不存在", null);
        }
        return createResponse(SUCCESS, "获取系统成功", system);
    }

    @RequestMapping (value = "", method = RequestMethod.GET)
    public @ResponseBody Map<String, Object> findAll(){
        List<System> systems = systemService.findAll();
        return createResponse(SUCCESS, "获取系统成功", systems);
    }
}
