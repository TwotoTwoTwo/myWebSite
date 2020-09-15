package cn.wsjiu.nineSite.dao;

import cn.wsjiu.nineSite.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
public interface UserDao {
    User queryUser(User user);
    boolean insertUser(User user);
}
