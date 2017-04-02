package com.taotao.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.taotao.web.service.ItemDescService;
import com.taotao.web.service.ItemService;
//http://www.taotao.com/item/868462.html
@RequestMapping("item")
@Controller
public class ItemController {
    
    @Autowired
    private ItemService itemService;
    
    @Autowired
    private ItemDescService itemDescService;

    @RequestMapping(value="{itemId}",method=RequestMethod.GET)
    public ModelAndView queryItemById(@PathVariable("itemId") Long id){
        ModelAndView mv = new ModelAndView("item");
        mv.addObject("item", this.itemService.queryItemById(id));
        mv.addObject("itemDesc", this.itemDescService.queryItemDescByItemId(id));
        mv.addObject("itemParam", this.itemService.queryItemParamItemToHtml(id));
        return mv;
    }
}
