package com.taotao.cart.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.abel533.entity.Example;
import com.taotao.cart.mapper.CartMapper;
import com.taotao.cart.pojo.Cart;

@Service
public class CartService {
    
    @Autowired
    private CartMapper cartMapper;
    /**
     *  添加商品到购物车
     * @param cart
     */
    public Boolean saveItemToCart(Cart cart) {
        //初始化数据
        cart.setId(null);
        cart.setCreated(new Date());
        cart.setUpdated(cart.getCreated());
        
        //判断该商品在原来的购物车里存在，存在则数量相加。
        Cart cart2 = new Cart();
        cart2.setUserId(cart.getUserId());
        cart2.setItemId(cart.getItemId());
        Cart selectOne = this.cartMapper.selectOne(cart2);
        
        if (selectOne == null) {
            //该商品在购物车中不存在
            this.cartMapper.insertSelective(cart);
            return true;
        }else{
            //修改数据
            selectOne.setNum(selectOne.getNum()+cart.getNum());
            selectOne.setUpdated(new Date());
            this.cartMapper.updateByPrimaryKey(selectOne);
            return false;
        } 
      
        
        
    }
    /**
     * 根据用户ID查询购物车数据
     * @param userId
     * @return
     */
    public List<Cart> queryListByUserId(Long userId) {
        Cart cart = new Cart();
        cart.setUserId(userId);
        return this.cartMapper.select(cart);
    }
    /**
     * 更新商品数量
     * @param userId
     * @param itemId
     * @param num
     * @return
     */
    public Boolean updateCart(Long userId, Long itemId, Integer num) {
        Cart record = new Cart();
        record.setUserId(userId);
        record.setItemId(itemId);
        Cart selectOne = this.cartMapper.selectOne(record);
        if (selectOne == null) {
            return false;
        }
        selectOne.setUpdated(new Date());
        selectOne.setNum(num);
        this.cartMapper.updateByPrimaryKeySelective(selectOne);
        return true;
    }
    /**
     * 删除购物车信息
     * @param userId
     * @param itemId
     */
    public void deleteCart(Long userId, Long itemId) {
        Example example = new Example(Cart.class);
        example.createCriteria().andEqualTo("userId", userId).andEqualTo("itemId", itemId);
        this.cartMapper.deleteByExample(example);
    }
    
    

}
