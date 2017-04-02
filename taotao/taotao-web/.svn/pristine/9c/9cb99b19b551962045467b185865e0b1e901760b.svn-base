package com.taotao.web.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.taotao.common.service.ApiService;
import com.taotao.common.service.RedisService;
import com.taotao.web.pojo.Item;
import com.taotao.web.pojo.ItemDesc;
import com.taotao.web.pojo.ItemParamItem;

@Service
public class ItemService {
    
    private static final ObjectMapper MAPPER = new ObjectMapper();
    
    @Value("${MANAGE_TAOTAO_URL}")
    private String MANAGE_TAOTAO_URL;
    
    @Autowired
    private RedisService redisService;
    
    @Autowired
    private ApiService apiService;
    
    public static final String REDIS_ITEM = "TAOTAO_WEB_ITEM_";

    public Item queryItemById(Long id) {
        String key = REDIS_ITEM + id;
        try {
            // 从缓存中命中
            String cacheJson = this.redisService.get(key);
            if (StringUtils.equals("404", cacheJson)) {
                //非法请求，直接返回null
                return null;
            }
            if (StringUtils.isNotEmpty(cacheJson)) {
                return MAPPER.readValue(cacheJson, Item.class);
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        
        String url = MANAGE_TAOTAO_URL + "/rest/item/" + id;
        try {
            String jsonData = this.apiService.doGet(url);
            if (jsonData == null) {
                try {
                    //非法请求的数据，缓存一小时
                    // 写入到缓存中
                    this.redisService.set(key, "404", 3600);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return null;
            }
            try {
                // 写入到缓存中
                this.redisService.set(key, jsonData, 60 * 60 * 24);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return MAPPER.readValue(jsonData, Item.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * 根据商品id加载描述信息
     * 
     * @param itemId
     * @return
     */
    public ItemDesc getItemDescById(Long itemId) {
        String url = MANAGE_TAOTAO_URL + "/rest/item/desc/" + itemId;
        try {
            String jsonData = this.apiService.doGet(url);
            if (jsonData == null) {
                return null;
            }
            return MAPPER.readValue(jsonData, ItemDesc.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    /**
     * 查询规格参数并后回html页面
     * @param id
     * @return
     */
    public String queryItemParamItemToHtml(Long id) {
        String url = MANAGE_TAOTAO_URL+"rest/item/param/item/"+id;
        try {
            String jsonData = this.apiService.doGet(url);
            if (jsonData.isEmpty()) {
                return null;
            }
            ItemParamItem itemParamItem = MAPPER.readValue(jsonData, ItemParamItem.class);
            String paramData = itemParamItem.getParamData();
            if (paramData == null) {
                return null;
            }
            // 解释json
            JsonNode jsonNode = MAPPER.readTree(paramData);
            ArrayNode arrayNode = (ArrayNode) jsonNode;

            StringBuilder sb = new StringBuilder();
            sb.append("<table cellpadding=\"0\" cellspacing=\"1\" width=\"100%\" border=\"0\" class=\"Ptable\"><tbody>");

            for (JsonNode node : arrayNode) {
                String group = node.get("group").asText();
                sb.append("<tr><th class=\"tdTitle\" colspan=\"2\">" + group + "</th></tr>");
                JsonNode paramNode1 = node.get("params");

                ArrayNode paramNode = (ArrayNode) paramNode1;
                for (JsonNode jsonNode2 : paramNode) {
                    sb.append("<tr><td class=\"tdTitle\">" + jsonNode2.get("k").asText() + "</td><td>"
                            + jsonNode2.get("v").asText() + "</td></tr>");
                }
            }
            sb.append("</tbody></table>");
            return sb.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}
