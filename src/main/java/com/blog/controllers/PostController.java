package com.blog.controllers;

import com.blog.config.AppConstants;
import com.blog.payloads.ApiResponse;
import com.blog.payloads.PostDto;
import com.blog.payloads.PostResponse;
import com.blog.services.FileService;
import com.blog.services.PostService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api/")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private FileService fileService;

    //this values's value is coming from application.properties and its required for fileUpload.
    @Value("${project.image}")
    private String path;

    @PostMapping("/user/{userId}/category/{categoryId}/posts")
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto, @PathVariable Integer userId,
                                              @PathVariable Integer categoryId) {
        PostDto createdPost = postService.createPost(postDto, userId, categoryId);
        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);

    }


    //this API is explicitely to understand transactional behaviour in springboot.
    @PostMapping("/postsWithTransaction")
    public ResponseEntity<PostDto> createPostWithTransaction(@RequestBody PostDto postDto, @PathVariable Integer userId,
                                              @PathVariable Integer categoryId) {
        PostDto createPostWithTransaction = postService.createPostWithTransaction(postDto, userId, categoryId);
        return new ResponseEntity<>(createPostWithTransaction, HttpStatus.CREATED);

    }

    @GetMapping("/user/{userId}/posts")
    public ResponseEntity<List<PostDto>> getPostByUser(@PathVariable Integer userId) {
        List<PostDto> posts = postService.getPostsByUser(userId);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @GetMapping("/category/{categoryId}/posts")
    public ResponseEntity<List<PostDto>> getPostByCategory(@PathVariable Integer categoryId) {
        List<PostDto> posts = postService.getPostsByCategory(categoryId);
        return new ResponseEntity<>(posts, HttpStatus.OK);

    }

    //it is swagger ui annotation that will place this API out of controller in Swagger UI and put a  name to this API that
    //we have mentioned here.
    @Operation(
            tags="Get a POST",
            summary = "Get POST Detail",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            description = "Failure",
                            responseCode = "404"
                    )
            }
    )
    @GetMapping("/posts/{postId}")
    public ResponseEntity<PostDto> getPost(@PathVariable Integer postId) {
        PostDto post = postService.getPostById(postId);
        return new ResponseEntity<>(post, HttpStatus.OK);

    }

    //It is also working fine with pagination logic...but we are not getting pageNumber,pagesize and totalElements in
    //Response payload, hence we are  use  method below this commented code.
//    @GetMapping("/posts")
//    public ResponseEntity<List<PostDto>> getAllPosts(
//            @RequestParam(value="pageNumber", defaultValue = "0", required = false)Integer pageNumber,
//            @RequestParam(value="pageSize", defaultValue = "10",required = false)Integer pageSize) {
//        List<PostDto> posts = postService.getAllPosts(pageNumber,pageSize);
//        return new ResponseEntity<>(posts, HttpStatus.OK);
//
//    }

    @GetMapping("/posts")
    public ResponseEntity<PostResponse> getAllPosts(
            @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir) {
        PostResponse posts = postService.getAllPosts(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(posts, HttpStatus.OK);

    }

    @Hidden  //this will hide this api from swagger
    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<?> deletePost(@PathVariable Integer postId) {
        this.postService.deletePost(postId);
        return new ResponseEntity<>(new ApiResponse("Post is deleted successfully", true), HttpStatus.OK);
    }

    @PutMapping("/posts/{postId}")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable Integer postId) {
        PostDto updatedPost = postService.updatePost(postDto, postId);
        return ResponseEntity.ok(updatedPost);
    }

    @GetMapping("/posts/search/{keyword}")
    public ResponseEntity<List<PostDto>> searchPostByTitle(@PathVariable String keyword) {
        List<PostDto> postDto = postService.searchPosts(keyword);
        return new ResponseEntity<>(postDto, HttpStatus.OK);

    }

    //method to upload file/image
    @PostMapping("/posts/image/upload/{postId}")
    public ResponseEntity<PostDto> uploadPostImage(
            @RequestParam("image")MultipartFile image,
            @PathVariable Integer postId) throws IOException {

        PostDto post =  this.postService.getPostById(postId);
        String fileName = this.fileService.uploadImage(path,image);
        post.setImageName(fileName);
        PostDto updatedPost = this.postService.updatePost(post,postId);
        return new ResponseEntity<>(updatedPost, HttpStatus.OK);
    }

    //method to download file/image
    @GetMapping(value = "/post/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(@PathVariable String imageName,
                              HttpServletResponse response) throws IOException {
        InputStream resource = this.fileService.getResource(path,imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource,response.getOutputStream());

    }

}
