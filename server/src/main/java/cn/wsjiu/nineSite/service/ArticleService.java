package cn.wsjiu.nineSite.service;

import cn.wsjiu.nineSite.entity.Article;

import java.util.Map;

public interface ArticleService {
    String imgToUrl(String userId, String ArticleId, String imgId, String imgBase64, String basePath);
    boolean saveArticle(Article article);

    Map<Integer,String> getArticles(int page, String category);
    String getArticleById(String articleId);

    int upAddForArticleById(String articleId);

    String[] getCategories();
}
