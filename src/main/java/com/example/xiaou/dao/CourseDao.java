package com.example.xiaou.dao;

import com.example.xiaou.pojo.User;
import com.example.xiaou.utils.PageUtils;

public interface UserDao {
    User queryUserByUsernameAndPassword(String username, String password);

    int queryTotalCounts(String search);

    void findPage(PageUtils<User> pageUtils, String search);

    int addUser(User user);

    int batchDelUserByIds(String ids);

    int updateUser(User user);
}

