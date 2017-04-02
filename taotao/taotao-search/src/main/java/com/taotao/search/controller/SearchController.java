package com.taotao.search.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.bean.EasyUIResult;
import com.taotao.search.service.SearchService;

//http://search.taotao.com/item/search
@RequestMapping("item/search")
@Controller
public class SearchController {
    @Autowired
    private SearchService searchService;

    /**
     * 对外提供接口查询商品
     */
    @RequestMapping(method=RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<EasyUIResult> search(@RequestParam("keyWords") String keyWords,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "rows", defaultValue = "36") Integer rows) {
        try {
            keyWords = new String(keyWords.getBytes("ISO-8859-1"), "UTF-8");
            EasyUIResult easyUIResult = this.searchService.search(keyWords,page,rows);
            return ResponseEntity.ok(easyUIResult);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
}
