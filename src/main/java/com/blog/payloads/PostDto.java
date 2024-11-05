package com.blog.payloads;

import com.blog.entities.Category;
import com.blog.entities.Comment;
import com.blog.entities.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
public class PostDto {

    private int postId;

    private String content;

    private String title;

    private String imageName;

    private Date creationDate;

    //we will change Category type to CategoryDto(it does not have post attribute exposed,s o recrusion will break) because Category Class has Post again, so if we use Category class here,
    //response payload will show recursively post->category->posts and so on...explain in video 19 at end.
    //private Category category;
    private CategoryDto category;

    //we will change User type to UserDto because User Class has Post again, so if we use User class here,
    //response payload will show recursively Post->User->Post and so on
    //private User user;
    private UserDto user;

    private Set<CommentDto> comments = new HashSet<>();
}
