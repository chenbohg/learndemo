package com.taotao.manage.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.abel533.entity.Example;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.manage.mapper.ItemDescMapper;
import com.taotao.manage.mapper.ItemMapper;
import com.taotao.manage.mapper.ItemParamItemMapper;
import com.taotao.manage.mapper.ItemParamMapper;
import com.taotao.manage.pojo.Item;
import com.taotao.manage.pojo.ItemDesc;
import com.taotao.manage.pojo.ItemParam;
import com.taotao.manage.pojo.ItemParamItem;

@Service
public class ItemService extends BaseService<Item> {

    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private ItemDescMapper itemDescMapper;

    @Autowired
    private ItemParamItemMapper itemParamItemMapper;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public void saveItem(Item item, String desc, String paramData) {
        // 事务问题
        item.setId(null);
        item.setCreated(new Date());
        item.setUpdated(item.getCreated());
        item.setStatus(1);
        this.itemMapper.insert(item);

        ItemDesc itemDesc = new ItemDesc();
        itemDesc.setItemId(item.getId());
        itemDesc.setCreated(new Date());
        itemDesc.setUpdated(itemDesc.getCreated());
        itemDesc.setItemDesc(desc);
        this.itemDescMapper.insert(itemDesc);

        ItemParamItem itemParamItem = new ItemParamItem();
        itemParamItem.setId(null);
        itemParamItem.setCreated(new Date());
        itemParamItem.setParamData(paramData);
        itemParamItem.setUpdated(itemParamItem.getCreated());
        itemParamItem.setItemId(item.getId());

        this.itemParamItemMapper.insertSelective(itemParamItem);
        // 发送消息到搜索
        sendMQ("insert", item.getId());

    }

    /**
     * 分页显示商品列表
     * 
     * @param page
     * @param rows
     * @return
     */
    public PageInfo<Item> queryItemList(Integer page, Integer rows) {
        PageHelper.startPage(page, rows);
        Example example = new Example(Item.class);
        example.setOrderByClause(" updated DESC");
        List<Item> list = this.itemMapper.selectByExample(example);
        PageInfo<Item> pageInfo = new PageInfo<Item>(list);
        return pageInfo;
    }

    /**
     * 更新商品信息
     * 
     * @param item
     * @param itemDesc
     */
    public void updateItem(Item item, String itemDesc, String itemParams, Long itemParamId) {
        item.setUpdated(new Date());
        this.itemMapper.updateByPrimaryKeySelective(item);

        ItemDesc itemDesc2 = new ItemDesc();
        itemDesc2.setItemId(item.getId());
        itemDesc2.setUpdated(new Date());
        itemDesc2.setItemDesc(itemDesc);
        this.itemDescMapper.updateByPrimaryKeySelective(itemDesc2);

        ItemParamItem itemParamItem = new ItemParamItem();
        itemParamItem.setId(itemParamId);
        itemParamItem.setItemId(itemDesc2.getItemId());
        itemParamItem.setUpdated(new Date());
        itemParamItem.setParamData(itemParams);
        this.itemParamItemMapper.updateByPrimaryKeySelective(itemParamItem);

        // 发送消息到前台、搜索
        sendMQ("update", item.getId());
    }

    public void sendMQ(String type, Long itemId) {
        try {
            // 发送消息到RabbitMq交换机
            Map<String, Object> map = new HashMap<String, Object>(2);
            map.put("type", type);
            map.put("itemId", itemId);
            rabbitTemplate.convertAndSend("item." + type, MAPPER.writeValueAsString(map));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
