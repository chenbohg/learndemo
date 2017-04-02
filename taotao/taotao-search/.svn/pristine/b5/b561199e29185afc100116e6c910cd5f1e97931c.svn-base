package com.taotao.search.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.ClientProtocolException;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taotao.common.bean.EasyUIResult;
import com.taotao.common.service.ApiService;
import com.taotao.search.pojo.Item;

@Service
public class SearchService {
    
    
    @Autowired
    private HttpSolrServer httpSolrServer;
    
    @Autowired
    private ApiService apiService;
    
    @Value("${MANAGE_TAOTAO_URL}")
    private String MANAGE_TAOTAO_URL;
    
    private static final ObjectMapper MAPPER = new ObjectMapper();

    public EasyUIResult search(String keyWords, Integer page, Integer rows) throws SolrServerException {
        //构造搜索条件
        SolrQuery solrQuery = new SolrQuery();
        solrQuery.setQuery("title:"+keyWords+" AND status:1");
        //如果start=0，就开始，rows=5返回5条记录，如果是第2页，start=5
        solrQuery.setStart((Math.max(page, 1)-1)*rows);
        solrQuery.setRows(rows);
        
       
        
        //判断是否要高亮显示
        Boolean isHightLight = !StringUtils.equals("*", keyWords) && StringUtils.isNotEmpty(keyWords);
        
        if (isHightLight) {
            //设置高亮显示
            solrQuery.setHighlight(true);
            solrQuery.addHighlightField("title");
            solrQuery.setHighlightSimplePre("<em>");
            solrQuery.setHighlightSimplePost("</em>");
        }
        
      
        //执行查询
        QueryResponse queryResponse = this.httpSolrServer.query(solrQuery);
        
        List<Item> list = queryResponse.getBeans(Item.class);
        
        if (list == null) {
            //没有查询到数据
            return new EasyUIResult();
        }
        
        Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();
        for (Map.Entry<String, Map<String, List<String>>> entry : highlighting.entrySet()) {
            for (Item item : list) {
                if (!entry.getKey().equals(item.getId().toString())) {
                    continue;
                }
                item.setTitle(StringUtils.join(entry.getValue().get("title"),""));
                break;
            }
        }
        
        EasyUIResult easyUIResult = new EasyUIResult(queryResponse.getResults().getNumFound(), list);
      
        return easyUIResult;
    }

    public Item getItemById(long itemId) {
        //http://manage.taotao.com/rest/item/1474391928
        String url =MANAGE_TAOTAO_URL + "rest/item/"+itemId;
        try {
            String jsonData = this.apiService.doGet(url);
            Item item = MAPPER.readValue(jsonData, Item.class);
            return item;
            
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    
    
}
