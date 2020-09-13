package cn.sijiu.service;

import cn.sijiu.dao.ArticleDao;
import cn.sijiu.dao.CommentDao;
import cn.sijiu.dao.RedisCacheDao;
import cn.sijiu.dao.UserDao;
import cn.sijiu.entity.Article;
import cn.sijiu.entity.Comment;
import cn.sijiu.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.*;
import java.util.Base64;
import java.util.*;

@Service
public class BlogServiceImpl implements BlogService{
    @Autowired
    ArticleDao articleDao;// 博客文章的Dao
    @Autowired
    UserDao userDao;// 用户的dao
    @Autowired
    CommentDao commentDao;// 评论信息的dao
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
        String url="static-resources/images/"+userId+"/"+ArticleId+"/";
        //图片储存路径
        String path=basePath+url;
        try {
            byte b[]=Base64.getDecoder().decode(imgBase64);
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


    @Override
    public void saveComment(Comment comment) {
        //对内容进行过滤，去除&&，在&&之间插空格
        comment.filter();
        int i = commentDao.insert(comment);
    }

    @Override
    public Map<Integer, String> getCommentsByArticleId(String articleId) {
        Comment[] r = commentDao.queryByArticleId(articleId);
        Map<Integer,String> m = new TreeMap<>();
        int len = r.length;
        for(int i=r.length-1;i>=0;i--) {
            m.put(len-1-i, r[i].toString());
        }
        return m;
    }

    // 查询分类
    @Override
    public String[] getCategories() {
        return articleDao.queryCategories();
    }


}
