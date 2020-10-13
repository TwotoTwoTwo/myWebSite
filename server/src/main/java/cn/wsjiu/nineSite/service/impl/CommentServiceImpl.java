package cn.wsjiu.nineSite.service.impl;

import cn.wsjiu.nineSite.dao.CommentDao;
import cn.wsjiu.nineSite.entity.Comment;
import cn.wsjiu.nineSite.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.TreeMap;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    CommentDao commentDao;// 评论信息的dao
    @Override
    public void saveComment(Comment comment) {
        //对内容进行过滤，去除&&，在&&之间插空格
        comment.filter();
        int i = commentDao.insert(comment);
    }

    @Override
    public Map<Integer, String> getCommentsByArticleId(String articleId) {
        Comment[] r = commentDao.queryByArticleId(articleId);
        Map<Integer,String> m = new TreeMap<>();
        int len = r.length;
        for(int i=r.length-1;i>=0;i--) {
            m.put(len-1-i, r[i].toString());
        }
        return m;
    }
}
