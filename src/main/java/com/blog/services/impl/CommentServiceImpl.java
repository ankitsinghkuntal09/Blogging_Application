package com.blog.services.impl;

import com.blog.entities.Comment;
import com.blog.entities.Post;
import com.blog.exceptions.ResourceNotFoundException;
import com.blog.payloads.CommentDto;
import com.blog.repositories.CommentRepo;
import com.blog.repositories.PostRepo;
import com.blog.services.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepo commentRepo;

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CommentDto createComment(CommentDto commentDto, Integer postId) {
        Post post = this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","PostId",postId));
        Comment comment = this.commentDtoToComment(commentDto);
        comment.setPost(post);
        Comment savedComment = commentRepo.save(comment);
        return commentToCommentDto(savedComment);
    }

    @Override
    public void deleteComment(Integer commentId) {
        Comment comment = this.commentRepo.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment","CommentId",commentId));
        commentRepo.delete(comment);
    }

    private Comment commentDtoToComment(CommentDto commentDto) {
        return this.modelMapper.map(commentDto, Comment.class);
    }

    private CommentDto commentToCommentDto(Comment comment) {
        return this.modelMapper.map(comment, CommentDto.class);
    }
}
