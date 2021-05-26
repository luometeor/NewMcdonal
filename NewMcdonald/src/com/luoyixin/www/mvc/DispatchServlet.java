package com.luoyixin.www.mvc;

import com.luoyixin.www.ioc.ioc.AnnotationConfigApplicationContext;
import com.luoyixin.www.logger.JdkLogger;
import com.luoyixin.www.orm.config.Config;
import com.luoyixin.www.orm.datasource.PoolDataSourceFactory;
import com.luoyixin.www.orm.datasource.pool.PoolDataSource;
import com.luoyixin.www.orm.datasource.pool.PoolDataSourceFactoryImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author xin
 * @ProjectName: McDonald
 * @Package: com.luoyixin.www.mvc
 * @ClassName: DispatchServlet
 * @create 2021/5/15-13:26
 * @Version: 1.0
 */
public class DispatchServlet extends HttpServlet {
    /**
     * 处理器映射
     */
    private HandlerMapping handlerMapping;
    /**
     * 处理器调用
     */
    private HandlerInvoker handlerInvoker;
    /**
     * 视图解析
     */
    private ViewResolver viewResolver;

    @Override
    public void init() {
        try {
            JdkLogger jdkLogger = new JdkLogger();

            jdkLogger.setProperties("logger.properties");
            //数据池工厂
            PoolDataSourceFactory dataSourceFactory = new PoolDataSourceFactoryImpl();
            //传入配置文件相关信息
            dataSourceFactory.setProperties("jdbc.properties");
            //造池
            PoolDataSource poolDataSource = dataSourceFactory.getPoolDataSource();
            //全局配置文件
            Config config = new Config("com.luoyixin.www.dao", poolDataSource);
            //获取ioc
            AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(config, "com.luoyixin.www.service","com.luoyixin.www.controller");
            //处理器映射，找到前端请求的方法
            handlerMapping = new HandlerMapping("com.luoyixin.www.controller",annotationConfigApplicationContext);
            //处理器调用,找到对应方法，处理参数后执行
            handlerInvoker = new HandlerInvoker();
            //视图解析  ，根据调用对应方法后返回 result执行给前端响应
            viewResolver = new ViewResolver();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            ActionHandler actionHandler = handlerMapping.getActionHandler(req);

            Result result = handlerInvoker.invokerHandler(req,resp, actionHandler);

            viewResolver.resolveView(req, resp, result);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
