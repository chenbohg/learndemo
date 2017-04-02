package com.taotao.manage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.manage.pojo.ItemDesc;
import com.taotao.manage.service.ItemDescService;

@RequestMapping("item/desc")
@Controller
public class ItemDescController {
    
    @Autowired
    private ItemDescService itemDescService;

    /**
     * 根据itemid查询商品描述 回显示功能
     * @param itemId
     * @return
     */
    //http://manage.taotao.com/rest/item/desc
    @RequestMapping(value="{itemId}",method=RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<ItemDesc> queryItemDescById(@PathVariable("itemId") Long itemId){
        try {
            ItemDesc itemDesc =  this.itemDescService.queryItemDescById(itemId);
            if (itemDesc == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            return ResponseEntity.ok().body(itemDesc);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
    
  
}
