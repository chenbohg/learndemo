package com.taotao.manage.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.bean.ItemCatResult;
import com.taotao.manage.pojo.ItemCat;
import com.taotao.manage.service.ItemCatService;

//http://localhost:8081/rest/item/cat/

@RequestMapping("item")
@Controller
public class ItemCatCotroller {
    
    @Autowired
    private ItemCatService itemCatService;

    @RequestMapping(value="cat",method=RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<ItemCat>> queryItemCatByParentId(@RequestParam(value="id",defaultValue="0") Long parentId){
        try {
            ItemCat itemCat = new ItemCat();
            itemCat.setParentId(parentId);
            List<ItemCat> list = this.itemCatService.queryListByWhere(itemCat);
            //200
            return ResponseEntity.status(HttpStatus.OK).body(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //500
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
    
    //http://manage.taotao.com/rest/item/cat/web/all
    @RequestMapping(value="cat/web/all",method=RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<ItemCatResult> queryAllItemTojson(){
        try {
            ItemCatResult queryAllToTree = this.itemCatService.queryAllToTree();
            
            return ResponseEntity.status(HttpStatus.OK).body(queryAllToTree);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
    
}
