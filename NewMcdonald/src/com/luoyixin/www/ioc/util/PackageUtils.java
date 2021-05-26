package com.luoyixin.www.ioc.util;


import com.luoyixin.www.orm.exception.NormalException;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashSet;
import java.util.Set;

/**
 * @author xin
 * @ProjectName: McDonald
 * @Package: com.luoyixin.www.utils
 * @ClassName: PackageUtils
 * @create 2021/4/22-17:00
 * @Version: 1.0
 */
public class PackageUtils {
    /**
     *
     * @param packagePath
     * @return
     */
    public static Set<String> getClassNameByPackage(String packagePath) {
        Set<String> packName = new HashSet<>();
        String packageFile = packagePath.replace(".","/");
       /*为了避免在项目中加载不到本项目中静态资源文件的BUG发生，
         *调用静态资源的classLoader最好用Thread.currentThread().getContextClassLoader()方法来获取，
         *<getClassLoader()是当前类加载器,而getContextClassLoader是当前线程的类加载器>
         *因为一般同一个项目中java代码和其静态资源文件都是同一个classLoader来加载的,
         * 以此确保通过此classLoader也能加载到本项目中的资源文件,此方法 得到模板下的路径<Class.getResource(String path)
         *path不以'/'开头时，默认是从此类所在的包下取资源；
         *path以'/'开头时，则是从项目的ClassPath根下获取资源。
         */
        String classPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        if(classPath == null) {

            classPath = Thread.currentThread().getContextClassLoader().getResource("/").getPath();
        }
        try {
            //以UTF-8解析字符串，防止发过来的字符串不是UTF-8
            String decode = URLDecoder.decode(classPath, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        File dir = new File(classPath + packageFile);
        if(dir.exists()) {
            //listFiles 获取指定目录下所有文件
            File[] files = dir.listFiles();
            for (File file : files) {
                //仅仅只有文件名 ：如StudentService.class
                String name = file.getName();
                if(file.isFile() && name.endsWith(".class")) {
                    //去掉.class, name：com.luoyixin.www.service.StudentService
                    name = packagePath + "." + name.substring(0,name.lastIndexOf("."));
                    packName.add(name);
                }
            }
        } else {
            throw new NormalException("包路径不存在");
        }
        return packName;
    }

}
