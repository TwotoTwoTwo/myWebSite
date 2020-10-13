package cn.wsjiu.nineSite.service;

import cn.wsjiu.nineSite.entity.Comment;

import java.util.Map;

public interface CommentService {
    void saveComment(Comment comment);
    Map<Integer, String> getCommentsByArticleId(String articleId);
}
