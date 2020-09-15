package cn.wsjiu.nineSite.controller;

import cn.wsjiu.nineSite.entity.Comment;
import cn.wsjiu.nineSite.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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
