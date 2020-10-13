package cn.wsjiu.nineSite.service;

import cn.wsjiu.nineSite.entity.Article;
import cn.wsjiu.nineSite.entity.Comment;
import cn.wsjiu.nineSite.entity.User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface UserService {
    boolean saveUser(User user);
    User queryUserById(User user);
}
