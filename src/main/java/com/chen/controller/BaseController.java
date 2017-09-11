package com.chen.controller;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by 陈忠意 on 2017/8/17.
 */
public class BaseController {

    public static final String SUCCESS = "1";
    public static final String FAIL = "0";

    public Map<String, Object> createResponse(String code, String msg, Object data){
        Map<String, Object> res = new HashMap <>();
        res.put("code", code);
        res.put("msg", msg);
        res.put("data", data);
        return res;
    }

    public Map<String, String> createErrors(Errors errors){
        Map<String, String> map = new HashMap<>();
        String msg = "";
        for(FieldError fieldError : errors.getFieldErrors()){
            map.put(fieldError.getField(), fieldError.getDefaultMessage());
            msg += fieldError.getField() + ":" + fieldError.getDefaultMessage() + ", ";
        }
        map.put("msg", msg);
        return  map;
    }

    public static String getMd5Password(String password){
        return DigestUtils.md5Hex(password);
    }

    /**
     * md5加盐字符串
     * @return
     */
    private static String[] strings = new String[]{
            "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z",
            "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z",
            "0", "1", "2", "3", "4", "5", "6", "7", "8", "9"
    };
    public static String getMd5OtherString(){
        Random random = new Random(System.currentTimeMillis());
        StringBuffer sb = new StringBuffer("");
        for(int i = 0;i < 12;++i){
            sb.append(strings[random.nextInt(strings.length)]);
        }
        return sb.toString();
    }

    public static void main(String[] args){
        for(int i = 0;i < 10;++i){
            System.out.println(getMd5OtherString());
            try {
                Thread.sleep(1000);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
