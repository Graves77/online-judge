package com.example.controller.blog;


import com.example.model.JsonResult;
import com.example.model.blog.Comments;
import com.example.service.blog.CommentsService;
import com.example.utils.State;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class CommentsController {
    @Autowired
    private CommentsService commentsService;

    @PostMapping("/addComments")
    public JsonResult addComments(@RequestBody Comments comments){
        return new JsonResult(commentsService.addComments(comments),State.SUCCESS,"评论成功!");
    }
    @GetMapping("/getCommentsList/{bid}/{uid}")
    public JsonResult getCommentsList(@PathVariable long bid,@PathVariable long uid){
        return new JsonResult(commentsService.getCommentsList(bid,uid),State.SUCCESS,"获取成功!");
    }

    @GetMapping("/likeComments/{uid}/{cid}/{state}")
    public JsonResult likeBlog(@PathVariable long uid,@PathVariable long cid,@PathVariable int state){

        boolean flag = commentsService.addCommentsLike(cid,uid,state);
        return new  JsonResult(flag,flag ? State.SUCCESS : State.FAILURE,

                flag ? state == 0 ? "取消点赞":"点赞成功!" : "操作失败!"
        );
    }

    @GetMapping("/removeComments/{cid}/{bid}")
    public JsonResult removeComments(@PathVariable long cid,@PathVariable long bid){
        boolean remove = commentsService.removeComments(cid,bid);
        return new JsonResult(
                remove,remove ? State.SUCCESS : State.FAILURE,
                remove ? "删除成功!" : "删除失败!"
        );
    }

}
