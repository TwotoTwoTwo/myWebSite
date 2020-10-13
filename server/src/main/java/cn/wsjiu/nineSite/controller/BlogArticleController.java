package cn.wsjiu.nineSite.controller;

import cn.wsjiu.nineSite.dao.RedisCacheDao;
import cn.wsjiu.nineSite.entity.Article;
import cn.wsjiu.nineSite.service.ArticleService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.io.File;
import java.util.*;

@Controller
@RequestMapping("/")
@CrossOrigin
public class BlogArticleController {
    @Autowired
    private ArticleService articleService;

    private static Logger logger = LogManager.getRootLogger();

    @Value("${application.path}")
    private String WEB_ROOT_PATH;

    //图片储存转url
    @RequestMapping("/imgToUrl")
    @ResponseBody
    public Map<String,String> imgsToUrl(@RequestBody Map<String,String> params){
        String id,imgBase64,url;
        String userId;
        Map<String,String> urls=new HashMap<>();
        // 获取图片储存的绝对路径
        String basePath=WEB_ROOT_PATH;
        id=params.get("articleId");// 获取img的文章id
        userId=params.get("userId");
        // 利用Enumeration获取req的所有参数
        Set<String> e=params.keySet();
        for(String key : e){
            // 取出base64
            // 生成url
            //key=(String)e.nextElement();
            if("articleId".equals(key)||"userId".equals(key)) continue;
            imgBase64=params.get(key);
            System.out.println(key);
            url= articleService.imgToUrl(userId,id, UUID.randomUUID().toString(), imgBase64,basePath);
            urls.put(key, url);
        }
        return urls;
    }

    @RequestMapping("/upLoad")
    @ResponseBody
    public String upBlogArticle(@RequestBody Article article){
        //service保存article
        boolean result= articleService.saveArticle(article);
        // 返回储存结果
        return result?"Y":"N";
    }

    @RequestMapping("/getArticles")
    @ResponseBody
    public Map<Integer,String> getArticles(@RequestParam int page,@RequestParam(required = false) String category) throws Throwable{
        Map<Integer,String> results= articleService.getArticles(page,category);
        return results;
    }

    @RequestMapping("/getCover")
    public String previewCover(@RequestParam String articleId,@RequestParam String userId){
    String coverName=null;// 预览图名称
    // 获取根路径
    String path= WEB_ROOT_PATH + "/static-resources/images/"
            + userId+"/" + articleId;;
    File f=new File(path);
    if(f.exists()) {
        String[] list=f.list();
        if(list.length>0) coverName="images/"+userId+'/'+articleId+"/"+list[0];
    }else {
        f.mkdirs();
    }
    if(coverName==null)  coverName="default.jpg";
    return "redirect:/static-resources/"+coverName;
    }

    @RequestMapping("/details")
    @ResponseBody
    public String getArticleById(@RequestParam String articleId){
        return articleService.getArticleById(articleId);
    }

    // 文章点赞
    @RequestMapping("/upAdd")
    @ResponseBody
    public String up(@RequestParam String articleId){
        int i= articleService.upAddForArticleById(articleId);
        return i>0?"Y":"N";
    }


    // 分类
    @RequestMapping(value = "/categories",method = RequestMethod.GET)
    @ResponseBody
    public String[] categories(){
        return articleService.getCategories();
    }

    @Autowired
    RedisCacheDao rct;
    @RequestMapping("/test")
    @ResponseBody
    public String test()
    {
        System.out.println("test");
        return "sss";
    }

}
