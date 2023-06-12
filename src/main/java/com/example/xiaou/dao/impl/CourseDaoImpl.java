package com.example.xiaou.dao.impl;

import com.example.xiaou.dao.UserDao;
import com.example.xiaou.pojo.User;
import com.example.xiaou.utils.BaseDao;
import com.example.xiaou.utils.PageUtils;

public class UserDaoImpl extends BaseDao<User> implements UserDao {
    @Override
    public User queryUserByUsernameAndPassword(String username, String password) {
        String sql = "select * from user where  username=? and password=?";
        return this.queryOne(sql, User.class, username, password);
    }

    @Override
    public int queryTotalCounts(String search) {
        StringBuilder sql = new StringBuilder("select count(*) from user where 1 = 1");
        if ("".equals(search)){
            return this.queryScalar(sql.toString()).intValue();
        }else{
            sql.append(" and name like "+"'%"+search+"%'");
            return this.queryScalar(sql.toString()).intValue();
        }

    }

    @Override
    public void findPage(PageUtils<User> pageUtils, String search) {
        StringBuilder sql = new StringBuilder("select * from user where 1 = 1");
        if ("".equals(search)){
            sql.append(" limit ?,?");
            pageUtils.list = this.queryAll(sql.toString(),User.class,pageUtils.getStartIndex(),pageUtils.getPageSize());
        }else{
            sql.append(" and name like '%"+search+"%'");
            sql.append(" limit ?,?");
            pageUtils.list = this.queryAll(sql.toString(),User.class,pageUtils.getStartIndex(),pageUtils.getPageSize());
        }
    }

    /**
     * 添加用户
     *
     * @param user 用户
     * @return int
     */
    @Override
    public int addUser(User user) {
        String sql = "insert into user values(null,?,?,?,?,?,?,?,?,?,?)";
        return this.update(sql,
                user.getName(),
                user.getPhone(),
                user.getAge(),
                user.getSex(),
                user.getUsername(),
                user.getPassword(),
                user.getStatus(),
                user.getCreatetime(),
                user.getRole(),
                user.getPicture());
    }

    /**
     * 批del用户id
     *
     * @param ids id
     * @return int
     */
    @Override
    public int batchDelUserByIds(String ids) {
        String sql = "delete from user where uid in (" + ids + ")";
        System.out.println("sql = " + sql);
        return  this.update(sql);
    }

    @Override
    public int updateUser(User user) {
        user.setPicture("https://liuyangdestudent.oss-cn-chengdu.aliyuncs.com/pic/04293916b0304b05ab1f81708fbb2324.png");
        String sql = "update user set name=?,phone=?,age=?,sex=?,username=?,password=?,status=?,createtime=?,role=?,picture=? where uid=?";
        return this.update(sql,
                user.getName(),
                user.getPhone(),
                user.getAge(),
                user.getSex(),
                user.getUsername(),
                user.getPassword(),
                user.getStatus(),
                user.getCreatetime(),
                user.getRole(),
                user.getPicture(),
                user.getUid());
    }

}
