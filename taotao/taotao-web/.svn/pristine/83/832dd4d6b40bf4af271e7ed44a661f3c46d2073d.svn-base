package com.taotao.web.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taotao.common.bean.HttpResult;
import com.taotao.common.service.ApiService;
import com.taotao.web.pojo.Cart;
import com.taotao.web.pojo.Item;
import com.taotao.web.pojo.User;

@Service
public class CartService {

    @Value("${TAOTAO_CART_URL}")
    private String TAOTAO_CART_URL;

    @Autowired
    private ApiService apiService;

    @Autowired
    private ItemService itemService;

    private static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * 添加商品到购物车
     * 
     * @param itemId
     * @param user
     */
    public Boolean addItemToCart(Long itemId, User user) {
        String url = TAOTAO_CART_URL + "rest/cart";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("userId", user.getId());
        params.put("itemId", itemId);
        Item item = this.itemService.queryItemById(itemId);
        params.put("itemTitle", item.getTitle());
        String[] images = item.getImages();
        if (images == null) {
            params.put("itemImage", "");
        } else {
            params.put("itemImage", images[0]);
        }
        params.put("itemPrice", item.getPrice());
        params.put("num", 1);// 默认购买1个商品

        try {
            HttpResult httpResult = this.apiService.doPost(url, params);
            if (httpResult.getCode() == 201 || httpResult.getCode() == 204) {
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 根据用户id查询购物车
     * 
     * @param user
     */
    public List<Cart> queryCartList(User user) {
        String url = TAOTAO_CART_URL + "rest/cart/" + user.getId();
        try {
            String jsondata = this.apiService.doGet(url);
            if (jsondata.isEmpty()) {
                return null;
            }
            return MAPPER.readValue(jsondata,
                    MAPPER.getTypeFactory().constructCollectionType(List.class, Cart.class));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 更新购物车商品数量
     * @param itemId
     * @param num
     */
    public Boolean updateCart(User user,Long itemId, Integer num) {
        String url = TAOTAO_CART_URL + "rest/cart/" + user.getId()+"/"+itemId+"/"+num;
        try {
            HttpResult httpResult = this.apiService.doPut(url);
            if (httpResult.getCode() == 200) {
                return true;
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 从购物车中删除商品
     * @param user
     * @param itemId
     * @return
     */
    public Boolean deleteItemFromCart(User user, Long itemId) {
        String url =  TAOTAO_CART_URL + "rest/cart/" + user.getId() + "/"+itemId;
        try {
            HttpResult httpResult = this.apiService.doDelete(url);
            if (httpResult.getCode() == 204) {
                return true;
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }

    
    
}
