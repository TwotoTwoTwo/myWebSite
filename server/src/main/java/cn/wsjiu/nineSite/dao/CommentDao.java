package cn.wsjiu.nineSite.dao;

import cn.wsjiu.nineSite.entity.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
public interface CommentDao {
    int insert(Comment comment);
    Comment[] queryByArticleId(String articleId);
}
