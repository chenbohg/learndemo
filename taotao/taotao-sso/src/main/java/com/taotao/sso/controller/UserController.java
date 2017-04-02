package com.taotao.sso.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.sso.pojo.User;
import com.taotao.sso.service.UserService;

//http://sso.taotao.com/user/check/zhangsan/1
@RequestMapping("user")
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 检查数据是否可用
     * 
     * @param param
     * @param type
     * @return
     */
    @RequestMapping(value = "check/{param}/{type}")
    @ResponseBody
    public ResponseEntity<Boolean> check(@PathVariable("param") String param,
            @PathVariable("type") Integer type) {
        try {
            Boolean bool = this.userService.check(param, type);
            return ResponseEntity.ok(bool);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    /**
     * 注册用户
     * 
     * @param user
     * @return
     */
    @RequestMapping(value = "register", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Void> register(User user) {
        // TODO
        try {
            this.userService.register(user);
            // 201
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    /**
     * 用户登录
     */
    @RequestMapping(value = "login", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> login(@RequestParam("u") String username, @RequestParam("p") String password) {
        // TODO
        try {
            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            String ticket = this.userService.login(user);
            if (ticket == null) {
                // 登录失败
                return ResponseEntity.ok(null);
            }

            return ResponseEntity.ok(ticket);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    /**
     * 通过ticket查询用户信息
     */
    @RequestMapping(value = "{ticket}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<User> queryUserByTicket(@PathVariable("ticket") String ticket) {
        try {
            User user = this.userService.queryUserByTicket(ticket);
            if (user == null) {
                // 404
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

}
