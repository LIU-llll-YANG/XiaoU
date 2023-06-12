package com.liuyang.smallsystem.utils;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.apache.commons.dbutils.QueryRunner;

import javax.sql.DataSource;
import java.io.InputStream;
import java.util.Properties;

public class DruidUtils {
    public static QueryRunner queryRunner ;
    static {
        //1 使用properties加载配置文件
        Properties properties = new Properties();
        InputStream inputStream = DruidUtils.class.getClassLoader().getResourceAsStream("druid.properties");
        try {
            properties.load(inputStream);
            inputStream.close();
            //2 获取数据源
            DataSource dataSource = DruidDataSourceFactory.createDataSource(properties);
            //3 使用dbutils
            queryRunner = new QueryRunner(dataSource);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
