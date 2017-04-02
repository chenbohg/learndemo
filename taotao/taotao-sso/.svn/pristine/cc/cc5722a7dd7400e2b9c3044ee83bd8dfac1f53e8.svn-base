package com.taotao.sso.service;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.taotao.common.service.ApiService;
import com.taotao.common.service.RedisService;
import com.taotao.sso.mapper.UserMapper;
import com.taotao.sso.pojo.User;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;
    private static final Map<Integer, Boolean> TYPES = new HashMap<Integer, Boolean>();
    
    @Autowired
    private RedisService redisService;
    
    private static final ObjectMapper MAPPER = new ObjectMapper();

    static {
        TYPES.put(1, true);
        TYPES.put(2, true);
        TYPES.put(3, true);
    }

    /**
     * 检查数据是否可用
     * 
     * @param param
     * @param type
     * @return
     * @throws Exception 
     */
    public Boolean check(String param, Integer type) throws Exception {
        if (!TYPES.containsKey(type)) {
            //参数非法
            throw new Exception("参数非法,type值为1，2，3！");
        }

        User user = new User();

        switch (type) {
        case 1:
            user.setUsername(param);
            break;
        case 2:
            user.setPhone(param);
            break;
        case 3:
            user.setEmail(param);
            break;
        default:
            break;
        }
        //True：数据可用，false：数据不可用
        return this.userMapper.select(user).isEmpty();
    }

    /**
     * 注册用户
     * @param user
     */
    public void register(User user) {
        user.setId(null);
        user.setCreated(new Date());
        user.setUpdated(user.getCreated());
        user.setPassword(DigestUtils.md5Hex(user.getPassword()));
       this.userMapper.insertSelective(user);
    }

    /**
     * 用户登录
     * @param user
     * @return
     * @throws JsonProcessingException 
     */
    public String login(User user) throws JsonProcessingException {
        User newUser = new User();
        newUser.setUsername(user.getUsername());
        User selectOne = this.userMapper.selectOne(newUser);
        if (!StringUtils.equals(DigestUtils.md5Hex(user.getPassword()), selectOne.getPassword())) {
            //登录失败
            return null;
        }
        //返回ticket值
        String ticket = "TICKET"+DigestUtils.md5Hex(System.currentTimeMillis()+user.getUsername());
        //高手就知道
        this.redisService.set(ticket,MAPPER.writeValueAsString(selectOne) , 3600);
        return ticket;
    }

    /**
     * 通过ticket查询用户信息
     * @param ticket
     * @return
     * @throws IOException 
     * @throws JsonMappingException 
     * @throws JsonParseException 
     */
    public User queryUserByTicket(String ticket) throws JsonParseException, JsonMappingException, IOException {
        String jsonData = this.redisService.get(ticket);
        if (jsonData == null) {
            //登录超时
            return null;
        }
        //高手发现
        this.redisService.expire(ticket, 3600);
        return MAPPER.readValue(jsonData, User.class);
    }
    

}
