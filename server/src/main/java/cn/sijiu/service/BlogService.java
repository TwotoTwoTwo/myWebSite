package cn.sijiu.service;

import cn.sijiu.entity.Article;
import cn.sijiu.entity.Comment;
import cn.sijiu.entity.User;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface BlogService {
    String imgToUrl(String userId,String ArticleId,String imgId,String imgBase64,String basePath);
    boolean saveArticle(Article article);

    Map<Integer,String> getArticles(int page,String category);
    String getArticleById(String articleId);

    int upAddForArticleById(String articleId);

    boolean saveUser(User user);
    User queryUserById(User user);

    void saveComment(Comment comment);
    Map<Integer, String> getCommentsByArticleId(String articleId);
    String[] getCategories();
}
