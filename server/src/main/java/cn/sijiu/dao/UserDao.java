package cn.sijiu.dao;

import cn.sijiu.entity.User;

public interface UserDao {
    User queryUser(User user);
    boolean insertUser(User user);
}
