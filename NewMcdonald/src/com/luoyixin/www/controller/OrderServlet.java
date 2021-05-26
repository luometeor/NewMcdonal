package com.luoyixin.www.controller;

import com.luoyixin.www.exception.Constant;
import com.luoyixin.www.ioc.annotation.Autowired;
import com.luoyixin.www.ioc.annotation.Controller;
import com.luoyixin.www.logger.JdkLogger;
import com.luoyixin.www.mvc.Result;
import com.luoyixin.www.mvc.innotation.RequestMapping;
import com.luoyixin.www.po.Cart;
import com.luoyixin.www.po.Order;
import com.luoyixin.www.po.OrderItem;
import com.luoyixin.www.po.Store;
import com.luoyixin.www.service.*;
import com.luoyixin.www.util.ExcelUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * @author xin
 * @ProjectName: McDonald
 * @Package: com.luoyixin.www.controller
 * @ClassName: OrderServlet
 * @create 2021/5/20-21:29
 * @Version: 1.0
 */
@Controller
@RequestMapping("/ajax/pages/order")
public class OrderServlet {
    @Autowired
    private OrderService orderService;
    @Autowired
    private CartService cartService;
    @Autowired
    private CartItemService cartItemService;
    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private StoreService storeService;
    private Logger logger = JdkLogger.getLogger();

    /**
     * 结账生成订单
     * @param cartId 那个购物车
     * @param storeId 订单所属的店的id
     * @return
     */
    @RequestMapping("/checkout")
    public Result createOrder(Integer cartId,Integer storeId) {
        Map<String,Object> map = new HashMap<>(50);
        Cart cart = cartService.queryCartByCartId(cartId);
        String orderId = orderService.createOrder(cart,storeId);
        map.put("userId",cart.getUserId());
        map.put("orderId",orderId);
        return new Result(false,map,"/pages/cart/checkout.html");
    }

    /**
     * 展示订单
     * @param userId
     * @param type 类型 一周前还是 一个月前,还是所有
     * @return
     */
    @RequestMapping("/entry_order")
    public Result entryOrder(Integer userId,String type) {
        List<Order> orders = null;

        if(Constant.THIS_WEEK.equals(type)) {
            orders = orderService.queryOrdersByLimitTime(userId,Constant.THIS_WEEK);
        } else if(Constant.THIS_MONTH.equals(type)) {
            orders = orderService.queryOrdersByLimitTime(userId,Constant.THIS_MONTH);
        } else if(Constant.THIS_YEAR.equals(type)) {
            orders = orderService.queryOrdersByLimitTime(userId,Constant.THIS_YEAR);
        } else if(Constant.THIS_DAY.equals(type)) {
            orders = orderService.queryOrdersByLimitTime(userId,Constant.THIS_DAY);
        } else if(Constant.LAST_YEAR.equals(type)){
            orders = orderService.queryOrdersByLimitTime(userId,Constant.LAST_YEAR);
        } else if(Constant.ALL.equals(type)){
            orders = orderService.queryOrdersByUserId(userId);
        }

        Map<String, Object> map = getOrderAndItem(orders);
        return new Result(true,map);
    }

    /**
     * 进入我的店，去查看订单
     * @param userId
     * @return
     */
    @RequestMapping("/show_my_store")
    public Result showMyStore(Integer userId) {
        List<Store> stores = storeService.queryStoresByUserId(userId);
        Map<String,Object> map = new HashMap<>(50);
        if(stores == null || stores.isEmpty()) {
            map.put("result",false);
        } else {
            map.put("result",true);
            map.put("stores",stores);
        }
        return new Result(true,map);
    }

    /**
     * 根据店的id 展示orders
     * @param storeId
     * @param type 类型 已经出售和未出售
     * @return
     */
    @RequestMapping("/list_orders")
    public Result listOrders(Integer storeId,String type) {

        List<Order> orders = orderService.queryOrdersByStatus(storeId,type);

        Map<String, Object> map = getOrderAndItem(orders);

        return new Result(true,map);
    }

    /**
     * 买单
     * @param orderId
     * @param storeId
     * @param userId
     * @return
     */
    @RequestMapping("/sell")
    public Result sell(String orderId,Integer storeId,Integer userId) {
        Order order = orderService.queryOrderByOrderId(orderId);

        order.setStatus(1);

        orderService.updateOrder(order);
        return new Result(false,"/pages/order/sell_order.html?storeId="+storeId+"&userId="+userId+"&type=no_sell");
    }




    /**
     *   根据order匹配商品，一起封装到map中
     * @param orders
     * @return
     */
    private Map<String,Object> getOrderAndItem(List<Order> orders) {
        Map<String,Object> map = new HashMap<>(250);

        Optional.ofNullable(orders)
                .filter(order -> !order.isEmpty())
                .ifPresent(order ->{
                    List<OrderItem> orderItems = new ArrayList<>();
                    order.forEach(o -> {
                        List<OrderItem> orderItem = orderItemService.queryOrderItemsByOrderId(o.getOrderId());
                        orderItem.forEach(item -> {
                            orderItems.add(item);
                        });
                    });
                    map.put("orderItems",orderItems);
                    map.put("orders",orders);
                    map.put("result",true);
                });
        if(map.isEmpty()) {
            map.put("result",false);
        }

        return map;
    }

    @RequestMapping("/export")
    public void export(HttpServletRequest request, HttpServletResponse response,Integer storeId,String type) {
        //一个店订单
        List<Order> orders = null;
        Map<String,Object> map = new HashMap<>(4);
        if (Constant.THIS_WEEK.equals(type)) {
            orders = orderService.queryOrdersByLimitTimeByStoreId(storeId, Constant.THIS_WEEK);
        } else if (Constant.THIS_MONTH.equals(type)) {
            orders = orderService.queryOrdersByLimitTimeByStoreId(storeId, Constant.THIS_MONTH);
        } else if (Constant.THIS_DAY.equals(type)) {
            orders = orderService.queryOrdersByLimitTimeByStoreId(storeId, Constant.THIS_DAY);
        }

        String[] title = {"店名称","商品名称","商品销售量","商品销售额","店的总销售","店的总销售额"};

        String sheetName = "销售情况表";


        //店总销售额
        Optional<BigDecimal> allPrice = orders.stream()
                                                .map(order -> order.getPrice())
                                                .reduce(BigDecimal::add);


        Store store = storeService.queryStoresById(orders.get(0).getStoreId());
        //店名
        String storeName = store.getStoreName();
        List<OrderItem> orderItems = orderItemService.queryOrderItemsByStoreName(storeName);

        //得到去重后商品的名称
        List<String> itemNames =  orderItems.stream().map(orderItem -> orderItem.getItemName())
                                                      .distinct()
                                                      .collect(Collectors.toList());
        String[][] content = new String[itemNames.size()][6];

        Integer storeTotalCount = 0;
        for (int i = 0; i < itemNames.size(); i++) {
            //商品名称
            String itemName = itemNames.get(i);

            //商品销售量
            Optional<Integer> totalCount = orderItems.stream()
                    .filter(orderItem -> itemName.equals(orderItem.getItemName()))
                    .map(orderItem -> orderItem.getCount())
                    .reduce(Integer::sum);
            //商品销售额
            Optional<BigDecimal> totalPrice = orderItems.stream()
                    .filter(orderItem -> itemName.equals(orderItem.getItemName()))
                    .map(orderItem -> orderItem.getPrice())
                    .reduce(BigDecimal::add);
            content[i][0] = storeName;
            content[i][1] = itemName;
            content[i][2] = totalCount.get().toString();
            content[i][3] = totalPrice.get().toString();
            content[i][5] = allPrice.get().toString();

            storeTotalCount = storeTotalCount + totalCount.get();
        }

        for (int i = 0; i < itemNames.size(); i++) {
            content[i][4] = storeTotalCount.toString();
        }

        HSSFWorkbook hssfWorkbook = ExcelUtil.getHssfWorkbook(sheetName, title, content);
        OutputStream outputStream = null;
       try {
           response.setContentType("application/vnd.ms-excel");
           response.setHeader("Content-disposition", "attachment;filename="+"销售情况:"+Instant.now()+".xls");
           outputStream = response.getOutputStream();
           hssfWorkbook.write(outputStream);
       } catch (IOException e) {
            logger.warning(String.format("导出excel错误"));
        } finally {
           try {
               outputStream.flush();
               outputStream.close();
           } catch (IOException e) {
               logger.warning(String.format("导出excel时outputSteam关闭发送错误"));
           }

       }

    }


}

