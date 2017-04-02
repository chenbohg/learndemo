package com.taotao.manage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.taotao.common.bean.EasyUIResult;
import com.taotao.manage.pojo.Item;
import com.taotao.manage.pojo.ItemDesc;
import com.taotao.manage.service.ItemService;

@RequestMapping("item")
@Controller
public class ItemController {

    @Autowired
    private ItemService itemService;

    /**
     * 新增商品
     * 
     * @param item
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Void> saveItem(Item item, @RequestParam("desc") String desc,
            @RequestParam("itemParams") String itemParams) {
        try {
            this.itemService.saveItem(item, desc, itemParams);
            // 200
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    /**
     * 商品列表功能
     */
    // http://manage.taotao.com/rest/item?page=1&rows=30
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<EasyUIResult> queryItemList(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "rows", defaultValue = "30") Integer rows) {

        try {
            PageInfo<Item> pageInfo = this.itemService.queryItemList(page, rows);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new EasyUIResult(pageInfo.getTotal(), pageInfo.getList()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    /**
     * 更新商品信息
     * 
     * @param item
     * @param itemDesc
     */
    // http://manage.taotao.com/rest/item
    @RequestMapping(method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<Void> updateItem(Item item, @RequestParam("desc") String itemDesc,
            @RequestParam("itemParams") String itemParams,
            @RequestParam("itemParamId") Long itemParamId) {
        try {
            this.itemService.updateItem(item, itemDesc, itemParams,itemParamId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
    /**
     * 根据id查询商品
     * @param itemId
     * @return
     */
    //http://manage.taotao.com/rest/item/40
    @RequestMapping(value="{itemId}",method=RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Item> queryItemById(@PathVariable("itemId") Long itemId){
        
        try {
            Item queryById = this.itemService.queryById(itemId);
            return ResponseEntity.status(HttpStatus.OK).body(queryById);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
    
}
