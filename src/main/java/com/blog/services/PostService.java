package com.blog.services;

import com.blog.payloads.PostDto;
import com.blog.payloads.PostResponse;

import java.util.List;

public interface PostService {

    public PostDto createPost(PostDto postDto,Integer userId,Integer postId);

    public PostDto createPostWithTransaction(PostDto postDto,Integer userId,Integer postId);

    public PostDto updatePost(PostDto postDto,Integer postId);

    public PostDto getPostById(Integer postId);

//    public List<PostDto> getAllPosts(Integer pageNumber, Integer pageSize);
    public PostResponse getAllPosts(Integer pageNumber, Integer pageSize,String sortBy, String sortDir);

    public void deletePost(Integer postId);

    //get all posts by category
    public List<PostDto> getPostsByCategory(Integer categoryId);

    //get all posts by user
    public List<PostDto> getPostsByUser(Integer userId);

    //search posts
    public List<PostDto> searchPosts(String keyword);

}
