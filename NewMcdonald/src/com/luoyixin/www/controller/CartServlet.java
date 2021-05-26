package com.luoyixin.www.controller;

import com.luoyixin.www.ioc.annotation.Autowired;
import com.luoyixin.www.ioc.annotation.Controller;
import com.luoyixin.www.mvc.Result;
import com.luoyixin.www.mvc.innotation.Entity;
import com.luoyixin.www.mvc.innotation.Param;
import com.luoyixin.www.mvc.innotation.RequestMapping;
import com.luoyixin.www.po.Cart;
import com.luoyixin.www.po.CartItem;
import com.luoyixin.www.po.Store;
import com.luoyixin.www.service.CartItemService;
import com.luoyixin.www.service.CartService;
import com.luoyixin.www.service.StoreService;

import java.util.*;

/**
 * @author xin
 * @ProjectName: McDonald
 * @Package: com.luoyixin.www.controller
 * @ClassName: CartServlet
 * @create 2021/5/20-0:43
 * @Version: 1.0
 */
@Controller
@RequestMapping("/ajax/pages/cart")
public class CartServlet {
    @Autowired
    private CartItemService cartItemService;
    @Autowired
    private CartService cartService;
    @Autowired
    private StoreService storeService;
    /**
     * 增加商品
     * @param cartItem 前端穿过的来cartItem的相关信息
     * @param userId 用户id
     * @return
     */
    @RequestMapping("/addToCart")
    public Result addItemToCart(@Entity("cartItem.") CartItem cartItem, @Param("userId") Integer userId,Integer storeId) {
        Map<String,Object> map = new HashMap<>(25);

        Cart cart = cartService.queryCartByUserIdAndStoreId(userId,storeId);


        if(cart == null) {
            cart = cartService.insertCartAndCartItem(cartItem,userId,storeId);
        } else {
            cart = cartService.updateCartAndCartItem(cart,cartItem,userId,storeId);
        }

        map.put("result",true);
        map.put("itemName",cartItem.getItemName());
        map.put("totalCount",cart.getTotalCount());

        return new Result(true,map);
    }

    /**
     * 清空购物车
     * @param userId
     * @return
     */
    @RequestMapping("/clean")
    public Result cleanCart(Integer userId,Integer storeId) {

        cartService.removeCartAndCartItem(userId,storeId);

        return new Result(false,"/pages/cart/cart.html?userId="+userId+"&storeId="+storeId);
    }

    /**
     * 获取到购物车包括商品的所有信息
     * @param userId
     * @return
     */
    @RequestMapping("/getCart")
    public Result getCart(Integer userId,Integer storeId) {

        Cart cart = cartService.queryCartByUserIdAndStoreId(userId,storeId);
        Store store = storeService.queryStoresById(storeId);
        Map<String,Object> map = new HashMap<>(50);



        if(cart == null || cartItemService.queryCartItemForList(cart.getId()).isEmpty()) {
            map.put("result",false);
            return new Result(true,map);
        }

        List<CartItem> cartItems = cartItemService.queryCartItemForList(cart.getId());

        map.put("result",true);
        map.put("cart",cart);
        map.put("cartItems",cartItems);
        map.put("storeName",store.getStoreName());
        return new Result(true,map);
    }

    /**
     * 根据cartItem的Id删除 购物车中的某个商品
     * @param cartItemId
     * @return
     */
    @RequestMapping("/delete")
    public Result deleteCartItem(Integer cartItemId) {

        CartItem cartItem = cartItemService.queryCartItemById(cartItemId);

        cartItemService.deleteCartItemAndUpdateCartById(cartItemId);

        Cart cart = cartService.queryCartByCartId(cartItem.getCartId());

        return new Result(false,"/pages/cart/cart.html?userId="+cart.getUserId()+"&storeId="+cart.getStoreId());
    }


    /**
     * 在购物车中更新数据
     * @param cartItemId
     * @param count
     * @param cartId
     * @return
     */
    @RequestMapping("/updateCount")
    public Result updateCartItemCount(Integer cartItemId,Integer count,Integer cartId) {

        cartItemService.updateCartItemCount(cartItemId,count,cartId);

        Cart cart = cartService.queryCartByCartId(cartId);

        return new Result(false,"/pages/cart/cart.html?userId="+cart.getUserId()+"&storeId="+cart.getStoreId());
    }


    @RequestMapping("/entryCart")
    public Result entryCart(Integer userId) {
        List<Cart> list = cartService.queryCartByUserId(userId);

        Map<String,Object> map = new HashMap<>(100);

        Optional.ofNullable(list)
                .filter(carts -> !carts.isEmpty())
                .ifPresent(carts -> {
                    List<String> storeNames = new ArrayList<>(250);
                    carts.forEach(cart -> {
                        Store store = storeService.queryStoresById(cart.getStoreId());
                        String storeName = store.getStoreName();
                        storeNames.add(storeName);
                    });
                    map.put("storeNames",storeNames);
                    map.put("carts",list);
                    map.put("result",true);
                });

        if(map.isEmpty()) {
            map.put("result",false);
        }


        return new Result(true,map);
    }


}
