package com.liuyang.smallsystem.utils;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;
import java.util.List;

public class BaseDao<T> {
    //对User表中的数据进行增删改查
    QueryRunner queryRunner = DruidUtils.queryRunner;

    //更新操作
    public int update(String sql, Object... par) {
        try {
            return queryRunner.update(sql, par);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }


    //查询
    //查询一个数据
    public T selectOne(String sql, Class<T> c, Object... params) {
        try {
            return queryRunner.query(sql, new BeanHandler<T>(c), params);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    //查询多个数据
    public List<T> getAll(String sql, Class<T> c, Object... params) {
        try {
            return queryRunner.query(sql, new BeanListHandler<T>(c), params);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //查询单条数据
    public Object getScalar(String sql, Object... params) {
        try {
            return queryRunner.query(sql, new ScalarHandler<>(),params);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
