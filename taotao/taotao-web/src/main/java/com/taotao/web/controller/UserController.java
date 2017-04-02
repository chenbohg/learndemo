package com.taotao.web.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.taotao.common.utils.CookieUtils;
import com.taotao.web.pojo.Order;
import com.taotao.web.service.UserService;

@RequestMapping("user")
@Controller
public class UserController {

    @Autowired
    private UserService userService;
    
   

    /**
     * 显示登录页面
     */
    @RequestMapping("register")
    public String register() {
        return "register";
    }

    /**
     * 注册用户
     * 
     * @param username
     * @param password
     * @param phone
     * @return
     */
    @RequestMapping(value = "doRegister", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> doRegister(@RequestParam("username") String username,
            @RequestParam("password") String password, @RequestParam("phone") String phone) {

        Boolean bool = this.userService.doRegister(username, password, phone);
        Map<String, Object> result = new HashMap<String, Object>(1);
        if (bool) {
            // 注册成功
            result.put("status", "200");
        } else {
            result.put("status", "500");
        }
        return result;
    }

    /**
     * 显示登录页面
     */
    @RequestMapping("login")
    public String login() {
        return "login";
    }

    // http://www.taotao.com/service/user/doLogin
    @RequestMapping(value ="doLogin",method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> doLogin(@RequestParam("username") String username,
            @RequestParam("password") String password,HttpServletRequest request ,HttpServletResponse response) {
        Map<String, Object> result = new HashMap<String, Object>(1);
        Boolean bool = this.userService.doLogin(username, password,request,response);
        
        if (bool) {
            result.put("status", 200);
            
        }else{
            result.put("status", 500);
        }
        return result;
    }
    
    /*
     * 个人信息页 
     */
    @RequestMapping("info")
   public String userInfo(){
       return "my-info";
   }
    
    /**
     * 用户头像
     */
    @RequestMapping("/userinfo/showImg")
    public String userImg(){
        return "my-info-img";
    }
    
    @RequestMapping("/userInfo/moreUserInfo")
    public String moreUserInfo(){
        return "my-info-more" ;
    }
}
