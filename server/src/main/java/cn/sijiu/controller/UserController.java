package cn.sijiu.controller;

import cn.sijiu.dao.RedisCacheDao;
import cn.sijiu.entity.User;
import cn.sijiu.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@Controller
@RequestMapping("/")
@CrossOrigin
public class UserController {
    @Autowired
    private BlogService blogService;
    @ResponseBody
    @RequestMapping("/login")
    public User login(@RequestBody User user){
        User usr = blogService.queryUserById(user);
        return usr;
    }

    @ResponseBody
    @RequestMapping("/register")
    public User register(@RequestBody User user){
        blogService.saveUser(user);
        // 返回用户注册的id
        return user;
    }

}
