package com.taotao.search.mq.handle;

import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.taotao.search.pojo.Item;
import com.taotao.search.service.SearchService;

public class ItemMQHandle {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    
    @Autowired
    private HttpSolrServer httpSolrServer;
    
    @Autowired
    private SearchService searchService;
    // 处理消息 更新索引
    public void execute(String msg) {

        JsonNode jsonNode;
        try {
            jsonNode = MAPPER.readTree(msg);
            //更新索引
            String type = jsonNode.get("type").asText();
            if (StringUtils.equals(type, "delete")) {
                //删除索引 
                this.httpSolrServer.deleteById(jsonNode.get("itemId").asText());
                
            }else if (StringUtils.equals(type, "insert") || StringUtils.equals(type, "update")) {
                Item item = this.searchService.getItemById(jsonNode.get("itemId").asLong());
                //新添索引
                this.httpSolrServer.addBean(item);
            }
            
            //提交
            this.httpSolrServer.commit();
            
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
