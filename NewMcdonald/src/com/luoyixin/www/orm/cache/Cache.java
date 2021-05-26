package com.luoyixin.www.orm.cache;

/**
 * @author xin
 * @ProjectName: McDonald
 * @Package: com.luoyixin.www.cache
 * @ClassName: Cache
 * @create 2021/4/30-23:08
 * @Version: 1.0
 */
public interface Cache {
    /**
     * 加入缓冲
     * @param key
     * @param value
     */
    void putCache(String key,Object value);

    /**
     * 获取缓冲
     * @param key
     * @return
     */
    Object getCache(String key);

    /**
     * 清空缓冲
     */
    void clearCache();

}
