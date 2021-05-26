package com.luoyixin.www.dao;

import com.luoyixin.www.orm.annotation.Dao;
import com.luoyixin.www.orm.annotation.Select;
import com.luoyixin.www.po.Destination;

/**
 * @author xin
 * @ProjectName: McDonald
 * @Package: com.luoyixin.www.dao
 * @ClassName: DestinationDao
 * @create 2021/5/16-19:41
 * @Version: 1.0
 */
@Dao
public interface DestinationDao {

    /**
     * 获取 具体的位置（省，城市，街区）
     * @param destinationId 位置的id
     * @return 返回位置（省，城市，街区）
     */
    @Select("select `id`,`province`,`city`,`stress` from destination where id = ?")
    Destination getDestinationByDestinationId(Integer destinationId);

    /**
     * 获取destination的id
     * @param province 省
     * @param city 城市
     * @param stress 街区
     * @return
     */
    @Select("select `id` from destination where province=? and city = ? and stress = ?  ")
    Integer getDestinationIdByDestinationId(String province, String city, String stress);
}
