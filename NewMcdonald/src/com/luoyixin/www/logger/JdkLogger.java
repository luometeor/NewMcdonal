package com.luoyixin.www.logger;

import org.apache.commons.math3.analysis.function.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * @author xin
 * @ProjectName: NewMcDonald
 * @Package: com.luoyixin.www.logger.jdklogger
 * @ClassName: JdkLogger
 * @create 2021/5/25-7:32
 * @Version: 1.0
 */
public class JdkLogger {

    private static final Logger logger = Logger.getLogger("JdkLogger");
    public void setProperties(String propertiesPath) throws IOException {
        InputStream is = JdkLogger.class.getClassLoader().getResourceAsStream(propertiesPath);
        LogManager.getLogManager().readConfiguration(is);
    }

    public static  Logger getLogger() {
        return logger;
    }

}
