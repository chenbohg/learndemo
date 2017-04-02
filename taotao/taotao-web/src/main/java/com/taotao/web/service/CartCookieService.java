package com.taotao.web.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.taotao.common.utils.CookieUtils;
import com.taotao.web.pojo.Cart;
import com.taotao.web.pojo.Item;

@Service
public class CartCookieService {

    private static final String CART_COOKIE_ITEM = "TT_ITEM_COOKIE";

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Autowired
    private ItemService itemService;

    /**
     * 添加商品到购物车
     * 
     * @param itemId
     * @param request
     * @param response
     */
    public void addItemToCart(Long itemId, HttpServletRequest request, HttpServletResponse response) {
        // 添加商品到购物车，查询出购物车是否有该商品
        try {
            List<Cart> carts = queryCartList(request);
            if (carts == null) {
                carts = new ArrayList<Cart>();
            }

            Cart cart = null;
            for (Cart c : carts) {
                if (c.getItemId().intValue() == itemId.intValue()) {
                    cart = c;
                    break;
                }
            }
            if (cart == null) {
                // 添加新的商品
                Item item = this.itemService.queryItemById(itemId);
                cart = new Cart();
                cart.setItemId(itemId);
                cart.setItemTitle(item.getTitle());
                cart.setCreated(new Date());
                String[] images = item.getImages();
                if (images == null) {
                    cart.setItemImage("");
                } else {
                    cart.setItemImage(images[0]);
                }
                cart.setItemPrice(item.getPrice());
                cart.setNum(1);
                cart.setUpdated(cart.getCreated());
                carts.add(cart);
            } else {
                // 商品数量相加
                cart.setNum(cart.getNum() + 1);
                cart.setUpdated(new Date());
            }
            // 注意cookie有效期 一般情况下一个月
            CookieUtils.setCookie(request, response, CART_COOKIE_ITEM, MAPPER.writeValueAsString(carts),
                    60 * 60 * 24 * 30, true);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    /**
     * 查询出购物车List<Cart>
     * 
     * @param request
     * @return
     * @throws IOException
     * @throws JsonMappingException
     * @throws JsonParseException
     */
    public List<Cart> queryCartList(HttpServletRequest request) throws JsonParseException,
            JsonMappingException, IOException {
        // 从cookie中取出购物车信息
        String cookieValue = CookieUtils.getCookieValue(request, CART_COOKIE_ITEM, true);
        if (cookieValue == null) {
            return null;
        }
        return MAPPER.readValue(cookieValue,
                MAPPER.getTypeFactory().constructCollectionType(List.class, Cart.class));
    }

    public void updateCart(Long itemId, Integer num, HttpServletRequest request, HttpServletResponse response) {

        try {
            List<Cart> carts = queryCartList(request);
            for (Cart cart : carts) {
                if (cart.getItemId().intValue() == itemId.intValue()) {
                    cart.setNum(num);
                    cart.setUpdated(new Date());
                }
            }

            // 重新写入Cookie
            CookieUtils.setCookie(request, response, CART_COOKIE_ITEM, MAPPER.writeValueAsString(carts),
                    60 * 60 * 24 * 30, true);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public void deleteItemFromCart(Long itemId, HttpServletRequest request, HttpServletResponse response) {
        try {
            List<Cart> carts = queryCartList(request);
            for (Cart cart : carts) {
                if (cart.getItemId().intValue() == itemId.intValue()) {
                    carts.remove(cart);
                    break;
                }
            }

            // 写入Cookie
            CookieUtils.setCookie(request, response, CART_COOKIE_ITEM, MAPPER.writeValueAsString(carts),
                    60 * 60 * 24 * 30, true);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
