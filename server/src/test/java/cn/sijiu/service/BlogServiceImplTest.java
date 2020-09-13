package cn.sijiu.service;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Map;

import static org.junit.Assert.*;
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration("src/main/resources")
@ContextConfiguration(locations = {"classpath*:spring-*.xml"})
public class BlogServiceImplTest{
    @Autowired
    BlogService blogService;
    @Test
    public void getArticles() {
        Map<Integer,String> m=blogService.getArticles(0,"java");
        System.out.print(m.values());
    }
}