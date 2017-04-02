package com.taotao.cart.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.cart.pojo.Cart;
import com.taotao.cart.service.CartService;

@RequestMapping("cart")
@Controller
public class CartController {

    @Autowired
    private CartService cartService;

    /**
     * 添加商品到购物车
     */
    // http://cart.taotao.com/rest/cart
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> saveItemToCart(Cart cart) {
        try {
            Boolean bool = this.cartService.saveItemToCart(cart);
            if (bool) {
                // 201
                return ResponseEntity.status(HttpStatus.CREATED).build();
            } else {
                // 204
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    /**
     * 根据用户ID查询购物车数据
     */
    // http://cart.taotao.com/rest/cart/{userId}
    @RequestMapping(value = "{userId}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<Cart>> queryListByUserId(@PathVariable("userId") Long userId) {

        try {
            List<Cart> list = this.cartService.queryListByUserId(userId);
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    /**
     * 更新商品数量
     */
    // http://cart.taotao.com/rest/cart/{userId}/{itemId}/{num}
    @RequestMapping(value = "{userId}/{itemId}/{num}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<Void> updateCart(@PathVariable("userId") Long userId,
            @PathVariable("itemId") Long itemId, @PathVariable("num") Integer num) {
        try {
            this.cartService.updateCart(userId, itemId, num);
            // 204
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    /**
     * 删除购物车信息
     */
    // http://cart.taotao.com/rest/cart/{userId}/{itemId}
    @RequestMapping(value = "{userId}/{itemId}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<Void> deleteCart(@PathVariable("userId") Long userId,
            @PathVariable("itemId") Long itemId) {

        try {
            this.cartService.deleteCart(userId,itemId);
            //204
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
