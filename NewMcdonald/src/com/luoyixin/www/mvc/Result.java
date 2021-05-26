package com.luoyixin.www.mvc;

import java.util.Map;

/**
 * @author xin
 * @ProjectName: McDonald
 * @Package: com.luoyixin.www.mvc
 * @ClassName: Result
 * @create 2021/5/15-14:04
 * @Version: 1.0
 */
public class Result {
    /**
     * 为true表示为json
     */
    private Boolean isJson;
    /**
     * json回传数据，或者重定向的数据
     */
    private Map<String,Object> jsonData;
    /**
     * 请求重定向的地址
     */
    private String path;


    public Result(Boolean isJson, Map<String, Object> jsonData) {
        this.isJson = isJson;
        this.jsonData = jsonData;
    }

    public Result(Boolean isJson, Map<String, Object> jsonData, String path) {
        this.isJson = isJson;
        this.jsonData = jsonData;
        this.path = path;
    }

    public Result(Boolean isJson, String path) {
        this.isJson = isJson;
        this.path = path;
    }


    public Boolean getIsJson() {
        return isJson;
    }

    public void setIsJson(Boolean json) {
        isJson = json;
    }

    public Map<String, Object> getJsonData() {
        return jsonData;
    }

    public void setJsonData(Map<String, Object> jsonData) {
        this.jsonData = jsonData;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
/*
    public List<T> getJsonList() {
        return jsonList;
    }

    public void setJsonList(List<T> jsonList) {
        this.jsonList = jsonList;
    }*/
}
