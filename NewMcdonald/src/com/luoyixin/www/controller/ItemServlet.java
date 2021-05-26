package com.luoyixin.www.controller;

import com.luoyixin.www.ioc.annotation.Autowired;
import com.luoyixin.www.ioc.annotation.Controller;
import com.luoyixin.www.logger.JdkLogger;
import com.luoyixin.www.mvc.Result;
import com.luoyixin.www.mvc.innotation.Param;
import com.luoyixin.www.mvc.innotation.RequestMapping;
import com.luoyixin.www.po.*;
import com.luoyixin.www.service.*;
import com.luoyixin.www.vo.Page;
import com.luoyixin.www.vo.Product;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.*;
import java.util.logging.Logger;

/**
 * @author xin
 * @ProjectName: McDonald
 * @Package: com.luoyixin.www.controller
 * @ClassName: ItemServlet
 * @create 2021/5/13-9:05
 * @Version: 1.0
 */
@Controller
@RequestMapping("/ajax/pages/item")
public class ItemServlet {
    private Logger logger = JdkLogger.getLogger();
    @Autowired
    private StoreService storeService;
    /**
     *  store--item的 中间表
     */
    @Autowired
    private StoreItemService storeItemService;
    /**
     * 商品
     */
    @Autowired
    private ItemsService itemsService;
    @Autowired
    private InformationService informationService;
    @Autowired
    private DestinationService destinationService;


    /**
     * 列举店，用与用户进入
     * @return
     */
    @RequestMapping("/getStore")
    public Result getStore(Integer userId) {

        List<Information> information = informationService.queryInformationByUserId(userId);

        Map<String,Object> map = new HashMap<>(250);

        List<Store> stores = storeService.queryStores();
        //匹配的店
        List<Store> matchStore = new ArrayList<>();

        if(information == null || information.isEmpty()) {
            map.put("result",false);
            map.put("message","还未配置送餐信息，请先配置");
        }  else if(stores == null || stores.isEmpty()) {
            map.put("result",false);
            map.put("message","暂时没有店哦，亲");
        } else {
            information.forEach(info -> {
                //得到默认地址
                if(info.getIsDefault()) {
                    Integer destinationId = info.getDestinationId();
                    Destination destination = destinationService.getDestinationByDestinationId(destinationId);
                    //找到城市相同的
                    stores.forEach(store -> {
                        if (store.getCity().equals(destination.getCity())) {
                            matchStore.add(store);
                        }
                    });

                }
            });

        }
        if(matchStore.isEmpty()) {
            map.put("result",false);
            map.put("message","您的默认地址所在地没有店");
        } else {
            map.put("result",true);
            map.put("stores",matchStore);
        }



        return new Result(true,map);
    }


    /**
     * 进入店商品分页
     * @param storeId
     * @return 商品
     */
    @RequestMapping("/entryStore")
    public Result entryStore(@Param("storeId") Integer storeId,@Param("pageNo") Integer pageNo) {

        Map<String,Object> map = new HashMap<>(250);
        //中间表查询
        List<Integer> itemIdList = storeItemService.queryStoreItemByStoreId(storeId);

        if(itemIdList != null && !itemIdList.isEmpty()) {
            Page<Product> productPage = itemsService.queryProductPage(pageNo,itemIdList);
            map.put("result",true);
            map.put("productPage",productPage);
        } else {
            map.put("result",false);
        }
        return new Result(true,map);

    }


    /**
     * 店长增加商品
     * @param req
     * @param storeId
     * @param pageNo
     * @return
     */
    @RequestMapping("/addItem")
    public Result addItem(HttpServletRequest req,Integer storeId,@Param("pageNo") Integer pageNo) {

        Map<String, Object> param = getParam(req);

        itemsService.addItem(param,storeId);

        return new Result(false,"/pages/manager/manager_item.html?storeId="+storeId+"&pageNo="+pageNo);
    }

    /**
     * 店长更新商品
     * @param req
     * @param itemId
     * @param pageNo
     * @return
     */
    @RequestMapping("/updateItem")
    public Result updateItem(HttpServletRequest req,@Param("itemId") Integer itemId,@Param("pageNo") Integer pageNo,Integer storeId) {

        Map<String, Object> param = getParam(req);

        itemsService.updateItem(param,itemId);

        return new Result(false,"/pages/manager/manager_item.html?storeId="+storeId+"&pageNo="+pageNo);
    }

    /**
     * 店长删除商品
     * @param itemId
     * @param pageNo
     * @return
     */
    @RequestMapping("/deleteItem")
    public Result deleteItem(Integer itemId,@Param("pageNo") Integer pageNo,Integer storeId) {

        itemsService.deleteItemByItemId(itemId);

        return new Result(false,"/pages/manager/manager_item.html?storeId="+storeId+"&pageNo="+pageNo);
    }


    /**
     * 上传文件
     * 用于表单提交得到map
     * key-->表单的name属性
     * value-->表单提交的值
     * @param req
     * @return
     */
    private Map<String,Object> getParam(HttpServletRequest req)   {
        Map<String, Object> param = new HashMap<>(250);

        if (ServletFileUpload.isMultipartContent(req)) {
            // 创建 FileItemFactory 工厂实现类
            FileItemFactory fileItemFactory = new DiskFileItemFactory();
            // 创建用于解析上传数据的工具类 ServletFileUpload 类
            ServletFileUpload servletFileUpload = new ServletFileUpload(fileItemFactory);

            // 解析上传的数据，得到每一个表单项 FileItem
            List<FileItem> list = null;
            try {
                list = servletFileUpload.parseRequest(req);
            } catch (FileUploadException e) {
               logger.warning(String.format("店长长传文件错误，参数为{%s}",list.toString()));
            }
            // 循环判断，每一个表单项，是普通类型，还是上传的文件
            Optional.ofNullable(list)
                    .filter(fileItems -> !fileItems.isEmpty())
                    .ifPresent(fileItems -> {
                        fileItems.forEach(fileItem -> {
                            try {
                                if (fileItem.isFormField()) {
                                    // 普通表单项
                                    param.put(fileItem.getFieldName(), fileItem.getString("UTF-8"));
                                } else {
                                    // 上传的文件
                                    param.put(fileItem.getFieldName(), "static/img/" + fileItem.getName());

                                    fileItem.write(new File("D:\\ideal-JDK8\\NewMcDonald\\web\\static\\img\\" + fileItem.getName()));

                                }
                            } catch (Exception e) {
                                logger.warning(String.format("店长长传文件错误，参数为{%s}",param.toString()));
                            }
                        });
                    });

            return param;
        }
        return null;
    }


}
