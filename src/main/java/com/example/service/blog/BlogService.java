package com.example.service.blog;



import com.example.model.blog.Blog;

import java.util.List;

public interface BlogService {

    boolean publishBlog(Blog blog);


    List<Blog> getBlogList(int page,int size,long uid,String type);

    /**
     * 给帖子点赞
     * @param bid
     * @param uid
     * @return
     */
    boolean addBlogLike(long bid,long uid,int state);

    /**
     * 收藏帖子
     * @param bid
     * @param uid
     * @param state
     * @return
     */
    boolean addBlogStar(long bid,long uid,int state);

    /**
     * 根据博客id得到博客信息
     * @param bid
     * @return
     */
    Blog getBlogById(long uid,long bid);

    /**
     * 搜索帖子
     * @param content 搜索内容
     * @return 得到的帖子结果
     */
    Blog queryBlogByContent(String content,long uid);

    boolean removeBlog(long bid);
}
