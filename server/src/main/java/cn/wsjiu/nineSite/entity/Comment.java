package cn.wsjiu.nineSite.entity;

import cn.wsjiu.nineSite.Utils;

/*
评论的实体类
 */
public class Comment {
    String articleId;//评论所属文章
    String userName;//评论的用户名
    String content;// 评论内容
    String date;// 日期
    int type; // 类型，评论或回复
    public void filter(){
        userName= Utils.datafilter(userName);
        content= Utils.datafilter(content);
    }
    public String getArticleId() {
        return articleId;
    }

    public String getUserName() {
        return userName;
    }

    public String getContent() {
        return content;
    }

    public int getType() {
        return type;
    }

    public String getDate() {
        return date;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return userName + "&&" +
                content + "&&" +
                date ;
    }
}
