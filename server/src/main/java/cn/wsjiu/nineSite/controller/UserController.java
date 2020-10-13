package cn.wsjiu.nineSite.controller;

import cn.wsjiu.nineSite.entity.User;
import cn.wsjiu.nineSite.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/")
@CrossOrigin
public class UserController {
    @Autowired
    private UserService userService;
    @ResponseBody
    @RequestMapping("/login")
    public User login(@RequestBody User user){
        User usr = userService.queryUserById(user);
        return usr;
    }

    @ResponseBody
    @RequestMapping("/register")
    public User register(@RequestBody User user){
        userService.saveUser(user);
        // 返回用户注册的id
        return user;
    }

}
