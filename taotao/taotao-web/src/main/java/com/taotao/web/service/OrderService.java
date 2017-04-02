package com.taotao.web.service;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.taotao.common.bean.HttpResult;
import com.taotao.common.service.ApiService;
import com.taotao.web.pojo.Item;
import com.taotao.web.pojo.Order;

@Service
public class OrderService {
    
    @Autowired
    private ApiService apiService;
    
    private static final ObjectMapper MAPPER = new ObjectMapper();
    
    @Value("${ORDER_TAOTAO_URL}")
    private String ORDER_TAOTAO_URL;

    public HttpResult submit(Order order) {
        String url = ORDER_TAOTAO_URL + "order/create";
        String json;
        try {
            json = MAPPER.writeValueAsString(order);
            HttpResult httpResult = this.apiService.dojsonPost(url, json);
            return httpResult;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
      return null;
    }

    public Order queryOrderByOrderId(Long orderId) {
        String url = ORDER_TAOTAO_URL +"order/query/"+orderId;
        try {
            String jsondata = this.apiService.doGet(url);
            if (jsondata == null) {
                return null;
            }
            return MAPPER.readValue(jsondata, Order.class);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    
    
}
