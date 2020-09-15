package cn.wsjiu.nineSite.dao;

import cn.wsjiu.nineSite.entity.Article;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Mapper
public interface ArticleDao {
    @Transactional()
    Integer insertArticle(Article article);
    Integer updateArticle(Article article);
    int updateArticleForUp(String articleId);
    int updateArticleForRead(String articleId);
    Article queryById(String articleId);
    Article[] query(@Param("pageBegin") int pageBegin, @Param("category") String category);
    String[] queryCategories();
}
