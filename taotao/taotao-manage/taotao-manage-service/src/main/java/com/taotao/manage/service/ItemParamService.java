package com.taotao.manage.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.manage.mapper.ItemParamMapper;
import com.taotao.manage.pojo.ItemParam;

@Service
public class ItemParamService  extends BaseService<ItemParam>{
    
    @Autowired
    private ItemParamMapper itemParamMapper;

    public ItemParam queryItemParamByItemCatId(Long itemCatId) {
        
        ItemParam itemParam = new ItemParam();
        itemParam.setItemCatId(itemCatId);
        return  this.itemParamMapper.selectOne(itemParam);
    }

    
    public void saveItemParam(ItemParam itemParam) {
        itemParam.setId(null);
        itemParam.setCreated(new Date());
        itemParam.setUpdated(itemParam.getCreated());
        this.itemParamMapper.insertSelective(itemParam);
        
    }

}
