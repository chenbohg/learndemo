package com.taotao.manage.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.abel533.entity.Example;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.manage.mapper.ItemParamItemMapper;
import com.taotao.manage.pojo.ItemParamItem;

@Service
public class ItemParamItemService extends BaseService<ItemParamItem> {
    
    @Autowired
    private ItemParamItemMapper itemParamItemMapper;

    
    public PageInfo<ItemParamItem> queryItemParamItemByPage(Integer page, Integer rows) {
        PageHelper.startPage(page, rows);
        Example example = new Example(ItemParamItem.class);
        example.setOrderByClause(" updated DESC");
        List<ItemParamItem> list = this.itemParamItemMapper.selectByExample(example);
        PageInfo<ItemParamItem> pageInfo = new PageInfo<ItemParamItem>(list);
        return pageInfo;
    }


    public ItemParamItem queryItemParamItemByItemId(Long itemId) {
        ItemParamItem itemParamItem = new ItemParamItem();
        itemParamItem.setItemId(itemId);
        ItemParamItem selectOne = this.itemParamItemMapper.selectOne(itemParamItem);
        if (selectOne == null) {
            return new ItemParamItem();
        }
        return selectOne;
    }

}
