/*
 * Copyright(c)2019-*, 亏钱有限股份无限有限公司
 *    项目名称:myWebServer
 *    文件名称:InfoController.java
 *    Date:20-5-9 下午1:54
 *    Author:王四九
 */
// 网站通用信息处理器
package cn.wsjiu.nineSite.controller;

import cn.wsjiu.nineSite.dao.RedisCacheDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@CrossOrigin
@RequestMapping("/")
public class InfoController {
    @Autowired
    RedisCacheDao rcd;
    @RequestMapping("")
    public String accessWebSite(@Autowired HttpServletRequest request){
        return "redirect:./blog";
    }
    @ResponseBody
    @RequestMapping(value="/ipNums",method = RequestMethod.GET)
    public String getIpNums(){
        long nums=rcd.getIpNums();
        return String.valueOf(nums);
    }
    @ResponseBody
    @RequestMapping(value = "/vistorNums",method = RequestMethod.GET)
    public String getVistorNums(){
        String nums = rcd.getVistorNums();
        return rcd.getVistorNums();
    }
}
