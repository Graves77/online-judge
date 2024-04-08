package com.example.controller.blog;


import com.example.model.JsonResult;
import com.example.model.blog.Blog;
import com.example.service.blog.BlogService;
import com.example.utils.State;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class BlogController {

    @Autowired
    private BlogService blogService;

    /**
     * 发布帖子
     * @param blog
     * @return
     */
    @PostMapping("/uploadBlog")
    public JsonResult uploadBlog(@RequestBody Blog blog){
        System.out.println(blog);
        boolean flag = blogService.publishBlog(blog);
        return new JsonResult(flag, flag ? State.SUCCESS : State.FAILURE,flag ? "发布成功!" : "发布失败!");
    }

    @GetMapping("/getBlogList/{page}/{size}/{uid}/{type}")
    public JsonResult getBlogLIst(@PathVariable int page,@PathVariable int size,@PathVariable long uid,@PathVariable String type){
        return new JsonResult(blogService.getBlogList(page,size,uid,type),State.SUCCESS,"获取成功!");
    }

    @GetMapping("/likeBlog/{uid}/{bid}/{state}")
    public JsonResult likeBlog(@PathVariable long uid,@PathVariable long bid,@PathVariable int state){
        boolean flag = blogService.addBlogLike(bid,uid,state);
        return new  JsonResult(flag,flag ? State.SUCCESS : State.FAILURE,
                flag ? state == 0 ? "取消点赞":"点赞成功!" : "操作失败!"
        );
    }
    @GetMapping("/starBlog/{uid}/{bid}/{state}")
    public JsonResult starBlog(@PathVariable long uid,@PathVariable long bid,@PathVariable int state){

        boolean flag = blogService.addBlogStar(bid,uid,state);
        return new  JsonResult(flag,flag ? State.SUCCESS : State.FAILURE,
                flag ? state == 0 ? "取消收藏":"收藏成功!" : "操作失败!"
        );
    }

    /**
     * 根据id查blog
     * @param uid
     * @param bid
     * @return
     */
    @GetMapping("/queryBlog/{uid}/{bid}")
    public JsonResult queryUp(@PathVariable long uid,@PathVariable long bid){
        Blog blogById = blogService.getBlogById(uid,bid);
        return new JsonResult(blogById,blogById == null ? State.FAILURE : State.SUCCESS,blogById == null ? "查询失败":"查询成功");
    }

    @GetMapping("/queryBlogByContent/{content}/{uid}")
    public JsonResult queryBlogByContent(@PathVariable String content,@PathVariable long uid){
        return new JsonResult(blogService.queryBlogByContent(content,uid),State.SUCCESS,"查询成功！");
    }

    @GetMapping("/removeBlog/{bid}")
    public JsonResult removeBlog(@PathVariable long bid){
        boolean remove = blogService.removeBlog(bid);
        return new JsonResult(
                remove,remove ? State.SUCCESS : State.FAILURE,
                remove ? "删除成功!" : "删除失败"
        );
    }

}
