package cn.wsjiu.nineSite.controller;

import cn.wsjiu.nineSite.entity.Comment;
import cn.wsjiu.nineSite.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/")
@CrossOrigin
public class CommentController {
    @Autowired
    private CommentService commentService;
    @RequestMapping("/comment")
    public void upComment(@RequestBody Comment comment){
        commentService.saveComment(comment);
        return;
    }

    @RequestMapping("/comments")
    public Map<Integer, String> getComment(@RequestParam String articleId){
        Map<Integer, String> m = commentService.getCommentsByArticleId(articleId);
        return m;
    }

}
