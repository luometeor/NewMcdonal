package com.luoyixin.www.orm.cache;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xin
 * @ProjectName: McDonald
 * @Package: com.luoyixin.www.cache
 * @ClassName: SimpleCache
 * @create 2021/4/30-23:13
 * @Version: 1.0
 */
public class SimpleCache implements Cache{
    private static Map<String,Object> map = new HashMap<>();

    @Override
    public void putCache(String key, Object value) {
        map.put(key,value);
    }

    @Override
    public Object getCache(String key) {
        return map.get(key);
    }

    @Override
    public void clearCache() {
        map.clear();
    }
}
