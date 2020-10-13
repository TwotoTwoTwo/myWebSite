package cn.wsjiu.nineSite.service.impl;

import cn.wsjiu.nineSite.dao.UserDao;
import cn.wsjiu.nineSite.entity.User;
import cn.wsjiu.nineSite.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    static Logger logger = LogManager.getRootLogger();
    @Autowired
    UserDao userDao;// 用户的dao

    @Override
    public boolean saveUser(User user) {
        userDao.insertUser(user);
        return false;
    }
    @Override
    public User queryUserById(User user) {
        User usr=userDao.queryUser(user);
        // ID错误或者密码错误
        if(usr == null) return null;
        // 不是登录，走默认账号，没有权限
        if(user.getUserPassword() == null) usr.setUserPassword(null);
        else if(user.getUserPassword().equals( usr.getUserPassword())){
            // 登录成功，封装password成token，写入usr，缓存保持
        }else usr = null; //密码错误
        return usr;
    }

}
