package com.luoyixin.www.orm.config;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * 给ioc容器使用
 * @author xin
 * @ProjectName: McDonald
 * @Package: com.luoyixin.www.config
 * @ClassName: KnownMapper
 * @create 2021/5/1-7:55
 * @Version: 1.0
 */
public class KnownMapper {
    /**
     *共享数据
     */
    private static Set<MapperStatement> mapperStatements = new HashSet<>();

    private MapperStatement mapperStatement;

    public Set<MapperStatement> getMapperStatements() {
        return mapperStatements;
    }

    public MapperStatement getMapperStatement() {
        return mapperStatement;
    }

    public void setMapperStatement(MapperStatement mapperStatement) {
        this.mapperStatement = mapperStatement;
        mapperStatements.add(mapperStatement);
    }

    public class MapperStatement{
        /**
         * dao名称
         */
        private String mapperName;
        /**
         * dao的class对象
         */
        private Class<?> mapperClass;
        /**
         * dao的实例
         */
        private Object object;

        public MapperStatement() {
        }

        public MapperStatement(String mapperName, Class<?> mapperClass) {
            this.mapperName = mapperName;
            this.mapperClass = mapperClass;
        }

        public MapperStatement(String mapperName, Class<?> mapperClass, Object object) {
            this.mapperName = mapperName;
            this.mapperClass = mapperClass;
            this.object = object;
        }

        public Object getObject() {
            return object;
        }

        public void setObject(Object object) {
            this.object = object;
        }

        public String getMapperName() {
            return mapperName;
        }

        public void setMapperName(String mapperName) {
            this.mapperName = mapperName;
        }

        public Class<?> getMapperClass() {
            return mapperClass;
        }

        public void setMapperClass(Class<?> mapperClass) {
            this.mapperClass = mapperClass;
        }

        @Override
        public String toString() {
            return "MapperStatement{" +
                    "mapperName='" + mapperName + '\'' +
                    ", mapperClass=" + mapperClass +
                    '}';
        }
        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            MapperStatement that = (MapperStatement) o;
            return Objects.equals(mapperName, that.mapperName) && Objects.equals(mapperClass, that.mapperClass);
        }

        @Override
        public int hashCode() {
            return Objects.hash(mapperName, mapperClass);
        }

    }
}
