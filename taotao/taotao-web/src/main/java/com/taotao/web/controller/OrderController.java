package com.taotao.web.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.jsqlparser.expression.operators.conditional.OrExpression;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.taotao.common.bean.HttpResult;
import com.taotao.common.utils.CookieUtils;
import com.taotao.web.pojo.Item;
import com.taotao.web.pojo.Order;
import com.taotao.web.pojo.User;
import com.taotao.web.service.CartService;
import com.taotao.web.service.ItemService;
import com.taotao.web.service.OrderService;
import com.taotao.web.service.UserService;
import com.taotao.web.threadlocal.UserThreadLocal;

@RequestMapping("order")
@Controller
public class OrderController {
    @Autowired
    private ItemService itemService;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;
    
    @Autowired
    private CartService cartService;

    private static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * 显示订单确认页
     * 
     * @return
     */
    @RequestMapping(value = "{itemId}", method = RequestMethod.GET)
    public ModelAndView toOrder(@PathVariable("itemId") Long itemId) {
        Item item = this.itemService.queryItemById(itemId);
        ModelAndView mv = new ModelAndView("order");
        mv.addObject("item", item);
        return mv;
    }

    @RequestMapping(value = "submit", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> submit(Order order, HttpServletRequest request) {
        // 获取当前用户，设置买家信息
        User user = this.userService.queryUserByticket(CookieUtils.getCookieValue(request,
                UserService.TAOTAO_TICKET));
        order.setUserId(user.getId());
        order.setBuyerNick(user.getUsername());
        
        Map<String, Object> result = new HashMap<String, Object>();
        HttpResult httpResult = this.orderService.submit(order);
        if (httpResult.getCode() == 200) {
            // 成功
            String jsonStr = httpResult.getBody();
            JsonNode node;
            try {
                node = MAPPER.readTree(jsonStr);
                if (node.get("status").asText().equals("200")) {
                    result.put("status", 200);
                    result.put("data", node.get("data").asText());
                    return result;
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return null;
    }
    
    
    @RequestMapping(value="success",method=RequestMethod.GET)
    public ModelAndView success(@RequestParam("id") Long orderId){
        ModelAndView mv = new ModelAndView("success");
        Order order = this.orderService.queryOrderByOrderId(orderId);
        mv.addObject("order", order);
        //送达时间，当前时间推后两天
        mv.addObject("date", new DateTime().plusDays(2).toString("MM月dd日"));
        return mv;
    }
    
    @RequestMapping(value="create",method=RequestMethod.GET)
    public ModelAndView toCartOrder(){
        
        ModelAndView mv = new ModelAndView("order-cart");
        User user = UserThreadLocal.get();
        mv.addObject("carts", this.cartService.queryCartList(user));
        return mv;
    }
}
