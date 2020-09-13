package cn.sijiu.dao;

import cn.sijiu.entity.Comment;

import java.util.List;

public interface CommentDao {
    int insert(Comment comment);
    Comment[] queryByArticleId(String articleId);
}
