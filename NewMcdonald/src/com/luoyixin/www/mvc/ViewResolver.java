package com.luoyixin.www.mvc;

import com.google.gson.Gson;
import com.luoyixin.www.logger.JdkLogger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

/**
 * @author xin
 * @ProjectName: McDonald
 * @Package: com.luoyixin.www.mvc
 * @ClassName: ViewResolver
 * @create 2021/5/15-13:32
 * @Version: 1.0
 */
public class ViewResolver {
    /**
     * 用于拼接
     */
    private static final String QUESTION = "?";

    private static final String AND = "&";

    private static final String EQUAL = "=";
    private Logger logger = JdkLogger.getLogger();

    public void resolveView(HttpServletRequest request, HttpServletResponse response, Result result) {
        if(result.getIsJson()) {
            try {
                // 解决post请求中文乱码问题
                // 一定要在获取请求参数之前调用才有效
                request.setCharacterEncoding("UTF-8");
                // 解决响应的中文乱码
                response.setContentType("text/html; charset=UTF-8");
                Gson gson = new Gson();

                response.getWriter().write(gson.toJson(result.getJsonData()));
            } catch (Exception e) {
                logger.warning(String.format("Json数据回传失败,数据为{%s}",result.getJsonData().toString()));
            }
        } else {
            try {
                Map<String, Object> jsonData = result.getJsonData();
                String param = "";
                if(jsonData != null && !jsonData.isEmpty()) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(QUESTION);

                    jsonData.forEach((key,value) ->{
                        sb.append(key).append(EQUAL).append(value).append(AND);
                    });

                    String sbString = sb.toString();
                    //去掉最后一个&
                    param = sbString.substring(0,sbString.length()-1);
                }
                if(param.length() != 0) {
                    response.sendRedirect(request.getContextPath() + result.getPath() +param);
                }  else {
                    response.sendRedirect(request.getContextPath() + result.getPath());
                }
            } catch (IOException e) {
                logger.warning(String.format("重定向错误,地址为{%s}",result.getPath()));
            }

        }
    }
}
