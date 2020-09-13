package cn.sijiu.dao;

import cn.sijiu.entity.Article;
import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

public interface ArticleDao {
    @Transactional()
    Integer insertArticle(Article article);
    Integer updateArticle(Article article);
    int updateArticleForUp(String articleId);
    int updateArticleForRead(String articleId);
    Article queryById(String articleId);
    Article[] query(@Param("pageBegin") int pageBegin, @Param("category")String category);
    String[] queryCategories();
}
