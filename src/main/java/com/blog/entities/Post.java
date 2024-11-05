package com.blog.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "post")
@NoArgsConstructor
@Getter
@Setter
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer postId;

    @Column(nullable = false,length = 100)
    private String title;

    @Column(length = 10000)
    private String content;

    private String imageName;

    private Date creationDate;

    //many post can be mapped to 1 user.
    @ManyToOne
    private User user;

    //many post will be mapped to 1 category.
    //column will be created in post table wtih name category_category_id, to change this name, since it is join column,
    //we can use like below
    @JoinColumn(name="category_Id")
    @ManyToOne
    private Category category;

    //1 post can have many comments
    @OneToMany(mappedBy = "post",cascade = CascadeType.ALL)
    private Set<Comment> comments = new HashSet<>();

}
