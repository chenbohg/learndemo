package com.taotao.web.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taotao.common.bean.EasyUIResult;
import com.taotao.common.service.ApiService;
import com.taotao.web.pojo.Content;
import com.taotao.web.pojo.Item;

@Service
public class IndexService {
    
    @Autowired
    private ApiService apiService;
    
    @Value("${MANAGE_TAOTAO_URL}")
    private String MANAGE_TAOTAO_URL;
    
    @Value("${INDEX_AD1_URL}")
    private String INDEX_AD1_URL;
    
    @Value("${SEARCH_TAOTAO_RUL}")
    private String SEARCH_TAOTAO_RUL;
    
    private static final ObjectMapper MAPPER = new ObjectMapper();
    
    public static final Integer ROWS=36;

    /**
     * 获得大广告位数据
     * @return
     */
    public String getIndexAD1() {
       String url = MANAGE_TAOTAO_URL+INDEX_AD1_URL;
       try {
        String jsonData = this.apiService.doGet(url);
        EasyUIResult easyUIResult = EasyUIResult.formatToList(jsonData, Content.class);
        List<Content> lists = (List<Content>) easyUIResult.getRows();
        //封装前台所需要的数据json
        List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
        for (Content content : lists) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("srcB", content.getPic());
            map.put("height", 240);
            map.put("alt", content.getTitle());
            map.put("width", 670);
            map.put("src", content.getPic());
            map.put("widthB", 550);
            map.put("href", content.getUrl());
            map.put("heightB", 240);
            result.add(map);
        }
        return MAPPER.writeValueAsString(result);
        
    } catch (Exception e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
        return null;
    }

    /**
     * 从solr中搜索商品结果
     * @param keyWords
     * @return
     */
    public EasyUIResult search(String keyWords,Integer page) {
        String url =SEARCH_TAOTAO_RUL +"item/search/";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("keyWords", keyWords);
        params.put("page", page);
        params.put("rows", ROWS);
        try {
            String jsonData = apiService.doGet(url, params);
            EasyUIResult easyUIResult = EasyUIResult.formatToList(jsonData, Item.class);
            return easyUIResult;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new EasyUIResult();
    }

}
