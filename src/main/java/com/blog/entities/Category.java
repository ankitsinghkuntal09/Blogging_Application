package com.blog.entities;

import com.fasterxml.jackson.databind.annotation.JsonTypeResolver;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="categories")
@Getter
@Setter
@NoArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer categoryId;

    @Column(name = "title",length = 100,nullable = false)
    private String categoryTitle;

    @Column(name = "description")
    private String categoryDescription;

    //it means 1 category can have many posts.
    //lazy means parent will be loaded and child will not be loaded
    //mappedBy="category", this category we will find in Post Class.
    @OneToMany(mappedBy = "category",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Post> posts = new ArrayList<>();



}
