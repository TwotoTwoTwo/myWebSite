package cn.sijiu.dao;

import cn.sijiu.entity.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.Jedis;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.TimeUnit;

public class RedisCacheDao {
    @Autowired
    public RedisTemplate<String,String> rt;

    public String queryArticleById(String articleId){
        String c= null;
        c=(String) rt.opsForValue().get(articleId);
        return c;
    }

    public void writeArticle(Article a){
        String c=a.toString()+"&&"+a.getContent();
        rt.opsForValue().set(a.getArticleId(),c,20,TimeUnit.SECONDS);
    }

    public void deleteArticle(String articleId){
        rt.delete(articleId);
    }

    public  long recordAccessIp(String ip){
        return rt.opsForHyperLogLog().add("defIp",ip);
    }

    public  long recordVistors() {
        return rt.opsForValue().increment("vistors",1);
    }

    public long getIpNums(){
        return rt.opsForHyperLogLog().size("defIp");
    }

    public String getVistorNums(){
        return rt.opsForValue().get("vistors");
    }

}
