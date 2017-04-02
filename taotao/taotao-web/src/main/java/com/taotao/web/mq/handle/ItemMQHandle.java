package com.taotao.web.mq.handle;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.taotao.common.service.RedisService;
import com.taotao.web.service.ItemService;

public class ItemMQHandle {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Autowired
    private RedisService redisService;

    // 处理消息 删除redis的数据
    public void execute(String msg) {

        JsonNode jsonNode;
        try {
            jsonNode = MAPPER.readTree(msg);
            // 删除缓存数据
            this.redisService.del(ItemService.REDIS_ITEM + jsonNode.get("itemId").asLong());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
