package com.example.service.blog;



import com.example.model.blog.Comments;

import java.util.List;

public interface CommentsService {

    boolean addComments(Comments comments);

    /**
     * 得到某个帖子得评论列表
     * @param bid
     * @return
     */
    List<Comments> getCommentsList(long bid,long uid);

    /**
     * 给帖子点赞
     * @param bid
     * @param uid
     * @return
     */
    boolean addCommentsLike(long bid,long uid,int state);

    /**
     * 删除评论
     * @param cid
     * @return
     */
    boolean removeComments(long cid,long bid);
}
