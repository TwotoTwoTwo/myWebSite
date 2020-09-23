package cn.wsjiu.nineSite.service;

import cn.wsjiu.nineSite.entity.Article;
import cn.wsjiu.nineSite.entity.Comment;
import cn.wsjiu.nineSite.entity.User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface BlogService {
    String imgToUrl(String userId, String ArticleId, String imgId, String imgBase64, String basePath);
    boolean saveArticle(Article article);

    Map<Integer,String> getArticles(int page, String category);
    String getArticleById(String articleId);

    int upAddForArticleById(String articleId);

    boolean saveUser(User user);
    User queryUserById(User user);

    void saveComment(Comment comment);
    Map<Integer, String> getCommentsByArticleId(String articleId);
    String[] getCategories();

    static void test() {
        return;
    };
}
