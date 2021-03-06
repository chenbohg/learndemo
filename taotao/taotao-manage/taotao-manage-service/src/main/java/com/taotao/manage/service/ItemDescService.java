package com.taotao.manage.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.manage.mapper.ItemDescMapper;
import com.taotao.manage.pojo.ItemDesc;

@Service
public class ItemDescService extends BaseService<ItemDesc>{
    
    @Autowired
    private ItemDescMapper itemDescMapper;

    /**
     * 根据itemid查询商品描述
     * @param itemId
     * @return
     */
    public ItemDesc queryItemDescById(Long itemId) {
        return this.itemDescMapper.selectByPrimaryKey(itemId);
    }

}
