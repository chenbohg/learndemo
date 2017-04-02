package com.taotao.web.service;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taotao.common.service.ApiService;
import com.taotao.web.pojo.ItemDesc;

@Service
public class ItemDescService {
    
    private static final ObjectMapper MAPPER = new ObjectMapper();
    
    @Value("${MANAGE_TAOTAO_URL}")
    private String MANAGE_TAOTAO_URL;

    @Autowired
    private ApiService apiService;

    public ItemDesc queryItemDescByItemId(Long id) {
       
        String url =MANAGE_TAOTAO_URL + "rest/item/desc/"+id;
        String jsonData;
        try {
            jsonData = this.apiService.doGet(url);
            if (jsonData == null) {
                return null;
            }
            return this.MAPPER.readValue(jsonData, ItemDesc.class);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
       return null;
    }
    
}
