package com.luoyixin.www.dao;

import com.luoyixin.www.orm.annotation.*;
import com.luoyixin.www.po.Store;

import java.util.List;

/**
 * @author xin
 * @ProjectName: McDonald
 * @Package: com.luoyixin.www.dao
 * @ClassName: StoreDao
 * @create 2021/5/13-9:05
 * @Version: 1.0
 */
@Dao
public interface StoreDao {
    /**
     * 查询所有店
     * @return 返回list集合，没有返回null
     */
    @Select("select `id`,`store_name` storeName,`user_id` userId,`description`,`city` city from store ")
    List<Store> queryList();

    /**
     * 增加店
     * @param store 要加的店
     * @return 返回1表示操作成功，返回0表示操作失败
     */
    @Insert
    int addStore(Store store);

    /**
     * 根据店的id删除店
     * @param storeId 店的id
     * @return 返回1表示操作成功，返回0表示操作失败
     */
    @Delete("delete from store where id = ?")
    int deleteStoreByStoreId(Integer storeId);

    /**
     * 更新店
     * @param store 含有要更新数据的店
     * @return 返回1表示操作成功，返回0表示操作失败
     */
    @Update
    int updateStore(Store store);

    /**
     * 根据用户id查询所有店
     * @param userId 用户id
     * @return 返回list，没有为null
     */
    @Select("select `id`,`store_name` storeName,`user_id` userId,`description`,`city` from store where user_id = ? ")
    List<Store> queryListByUserId(Integer userId);

    /**
     * 根据主键id查询店
     * @param storeId 店的id
     * @return 返回店
     */
    @Select("select `id`,`store_name` storeName,`user_id` userId,`description`,`city` from store where id = ? ")
    Store queryStoreById(Integer storeId);
}
