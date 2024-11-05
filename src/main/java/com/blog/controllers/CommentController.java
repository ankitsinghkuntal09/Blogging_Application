package com.blog.controllers;

import com.blog.payloads.ApiResponse;
import com.blog.payloads.CommentDto;
import com.blog.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/post/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto, @PathVariable Integer postId)
    {
        return new ResponseEntity<>(commentService.createComment(commentDto,postId), HttpStatus.CREATED);
    }

    @DeleteMapping("/comments/{commentsId}")
    public ResponseEntity<ApiResponse> deleteComment( @PathVariable Integer commentsId)
    {   commentService.deleteComment(commentsId);
        return new ResponseEntity<>(new ApiResponse("Comment deleted successfully",true), HttpStatus.OK);
    }
}
