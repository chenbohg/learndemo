package com.taotao.web.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taotao.common.bean.HttpResult;
import com.taotao.common.service.ApiService;
import com.taotao.common.utils.CookieUtils;
import com.taotao.web.pojo.User;

@Service
public class UserService {
    
    @Value("${SSO_TAOTAO_URL}")
    private String SSO_TAOTAO_URL;

    @Autowired
    private ApiService apiService;
    
    private static final ObjectMapper MAPPER = new ObjectMapper();
    
    public static final String  TAOTAO_TICKET ="TT_TICKET";
    
    /**
     * 注册用户
     * @param username
     * @param password
     * @param phone
     * @return
     */
    public Boolean doRegister(String username, String password, String phone) {
        String url  = SSO_TAOTAO_URL + "user/register";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("username", username);
        params.put("password", password);
        params.put("phone", phone);
        try {
            HttpResult httpResult = this.apiService.doPost(url, params);
            if (httpResult.getCode() == 201) {
                //注册成功
                return true;
            }else{
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return false;
    }

    /**
     * 用户登录
     * @param username
     * @param password
     * @return
     */
    public Boolean doLogin(String username, String password,HttpServletRequest request ,HttpServletResponse response) {
        String url  = SSO_TAOTAO_URL + "/user/login";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("u", username);
        params.put("p", password);
        try {
            HttpResult httpResult = this.apiService.doPost(url, params);
            if (httpResult.getCode() == 200) {
                String body = httpResult.getBody();
                //将ticket写入cookie
                CookieUtils.setCookie(request, response, TAOTAO_TICKET, httpResult.getBody());
                return true;
            }else{
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 根据ticket查询用户
     * @param ticket
     * @return
     */
    public User queryUserByticket(String ticket) {
        String url = SSO_TAOTAO_URL +"user/"+ticket;
        try {
            String jsondata = this.apiService.doGet(url);
            if (jsondata == null ) {
              return null;
            }
            User user = MAPPER.readValue(jsondata, User.class);
            return user;
            
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

}
