package cn.sijiu.controller;

import cn.sijiu.entity.Comment;
import cn.sijiu.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@RestController
@RequestMapping("/")
@CrossOrigin
public class CommentController {
    @Autowired
    BlogService blogService;
    @RequestMapping("/comment")
    public void upComment(@RequestBody Comment comment){
        blogService.saveComment(comment);
        return;
    }

    @RequestMapping("/comments")
    public Map<Integer, String> getComment(@RequestParam String articleId){
        Map<Integer, String> m = blogService.getCommentsByArticleId(articleId);
        return m;
    }

}
