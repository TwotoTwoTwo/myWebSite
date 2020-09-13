package cn.sijiu.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.*;
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration("src/main/resources")
@ContextConfiguration(locations = { "classpath*:/spring-mvc.xml", "classpath*:/spring-redis.xml","classpath*:/spring-mybatis.xml" })
public class RedisCacheDaoTest{

    @Test
    public void queryArticle() {
    }
}