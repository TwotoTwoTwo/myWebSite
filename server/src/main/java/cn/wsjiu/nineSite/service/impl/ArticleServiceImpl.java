package cn.wsjiu.nineSite.service.impl;

import cn.wsjiu.nineSite.dao.ArticleDao;
import cn.wsjiu.nineSite.dao.RedisCacheDao;
import cn.wsjiu.nineSite.entity.Article;
import cn.wsjiu.nineSite.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Map;
import java.util.TreeMap;

@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    ArticleDao articleDao;// 博客文章的Dao

    @Autowired
    RedisCacheDao rcd;

    Base64.Decoder decoder=Base64.getDecoder();// 图片的base64解码

    @Override
    public String imgToUrl(String userId,String ArticleId,String imgId,String imgBase64,String basePath) {
        if(imgBase64==null||"".equals(imgBase64)) return "no";
        // 正则获取图片格式
        String format=imgBase64.split(";", 2)[0].split("/", 2)[1];
        if("jpeg".equals(format)) format="jpg";
        //去掉前缀
        imgBase64=imgBase64.split(",", 2)[1];
        imgBase64=imgBase64.replaceAll(" ", "+");
        //System.out.println(imgBase64);
        //生成图片名字
        String imgName=imgId+"."+format;
        //生成图片的后半部分URL
        String url= "/static-resources/images/" +userId+"/"+ArticleId+"/";
        //图片储存路径
        String path=basePath+url;
        try {
            byte b[]= Base64.getDecoder().decode(imgBase64);
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {// 调整异常数据
                    b[i] += 256;
                }
            }
            //生成同一文章的所有图片的文件夹
            File f=new File(path);
            if(!f.exists()) f.mkdirs();
            //生成图片文件
            f=new File(path+imgName);
            if(!f.exists()) f.createNewFile();

            BufferedOutputStream printer = new BufferedOutputStream(new FileOutputStream(f));
            printer.write(b);
            printer.flush();
        } catch (IOException e) {
            e.printStackTrace();
            return "no";
        }

        return url+imgName;
    }
    @Override
    public boolean saveArticle(Article article){
        // 过滤&&
        article.filter();
        // 查询数据库是否已有该文章
        Article article1=articleDao.queryById(article.getArticleId());
        //是则update否则insert
        int i=0;
        if(article1 == null){
            i=articleDao.insertArticle(article);
        }else {
            i=articleDao.updateArticle(article);
        }
        rcd.writeArticle(article);
        return i>0?true:false;

    }
    @Override
    public Map<Integer,String> getArticles(int page,String category) {
        //  拿到数据
        Article[] articles;
        articles=articleDao.query(page*5,category);
        Map<Integer,String> results=new TreeMap<>();

        // 转换成map
        for(int i=articles.length-1;i>=0;i--){
            results.put(i,articles[i].toString());
            // 将查询到的文章放入缓存中
            rcd.writeArticle(articles[i]);
        }
        return results;
    }
    @Override
    public String getArticleById(String articleId) {
        String c;//文章内容
        // 先从缓存redis拿数据
        if((c=rcd.queryArticleById(articleId))==null) {
            //缓存没有，mysql拿
            Article a=articleDao.queryById(articleId);
            if(a==null) {
                c= null;
            }else {
                c=a.toString()+"&&"+a.getContent();
                //写入缓存
                rcd.writeArticle(a);
            }
        }
        // 更新阅读量
        int nums=articleDao.updateArticleForRead(articleId);
        return  c;
    }
    @Override
    public int upAddForArticleById(String articleId) {
        // 缓存双删策略，保证缓存一致性
        int nums=articleDao.updateArticleForUp(articleId);
        rcd.deleteArticle(articleId);
        return nums;
    }

    // 查询分类
    @Override
    public String[] getCategories() {
        return articleDao.queryCategories();
    }
}
