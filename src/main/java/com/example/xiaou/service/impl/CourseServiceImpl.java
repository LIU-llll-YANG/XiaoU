package com.example.xiaou.service.impl;

import com.example.xiaou.dao.UserDao;
import com.example.xiaou.dao.impl.UserDaoImpl;
import com.example.xiaou.pojo.User;
import com.example.xiaou.service.UserService;
import com.example.xiaou.utils.PageUtils;

public class UserServiceImpl implements UserService {
    UserDao userDao = new UserDaoImpl();
    @Override
    public User queryUserByUsernameAndPassword(String username, String password) {
        return  userDao.queryUserByUsernameAndPassword(username,password);
    }

    @Override
    public int queryTotalCounts(String search) {
        return userDao.queryTotalCounts(search);
    }

    @Override
    public void findPage(PageUtils<User> pageUtils, String search) {
        userDao.findPage(pageUtils,search);
    }

    /**
     * 添加用户
     *
     * @param user 用户
     * @return int
     */
    @Override
    public int addUser(User user) {
        return userDao.addUser(user);
    }

    /**
     * 批del用户id
     *
     * @param ids id
     * @return int
     */
    @Override
    public int batchDelUserByIds(String ids) {
        return userDao.batchDelUserByIds(ids);
    }

    @Override
    public int updateUser(User user) {
        return userDao.updateUser(user);
    }


}
