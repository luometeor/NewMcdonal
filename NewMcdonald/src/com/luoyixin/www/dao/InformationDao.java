package com.luoyixin.www.dao;

import com.luoyixin.www.orm.annotation.*;
import com.luoyixin.www.po.Information;

import java.util.List;

/**
 * @author xin
 * @ProjectName: McDonald
 * @Package: com.luoyixin.www.dao
 * @ClassName: InformationDao
 * @create 2021/5/16-17:42
 * @Version: 1.0
 */
@Dao
public interface InformationDao {
    /**
     * 增加配餐信息
     * @param information 配餐信息
     * @return 返回1表示成功，返回0表示失败
     */
    @Insert
    Integer addInformation(Information information);

    /**
     * 查询配餐信息根据用户id
     * @param userId 用户id
     * @return 返回配餐信息，没有的话为null
     */
    @Select("select `id`,`username`,`sex`,`phone_number` phoneNumber,`detail_destination` detailDestination," +
            "`user_id` userId,`destination_id` destinationId ,`default` isDefault from information where user_id = ?")
    List<Information> queryInformationByUserId(Integer userId);

    /**
     * 删除配餐信息
     * @param informationId 配餐信息id
     * @return 返回1表示成功，返回0表示失败
     */
    @Delete("delete from information where id = ? ")
    Integer deleteInformationByInformationId(Integer informationId);

    /**
     * 更新配餐信息
     * @param information 配餐信息
     * @return 返回1表示成功，返回0表示失败
     */
    @Update
    Integer updateInformation(Information information);

    /**
     * 根据主键获取information
     * @param informationId
     * @return
     */
    @Select("select `id`,`username`,`sex`,`phone_number` phoneNumber,`detail_destination` detailDestination," +
            "`user_id` userId,`destination_id` destinationId ,`default` isDefault from information where id = ?")
    Information queryInformationByInformationId(Integer informationId);
}
