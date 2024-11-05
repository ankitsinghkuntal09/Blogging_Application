package com.blog.repositories;

import com.blog.entities.Category;
import com.blog.entities.Post;
import com.blog.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface PostRepo extends JpaRepository<Post,Integer> {

    //custom finder method to get all posts by a user
    List<Post> findByUser(User user);

    //custom finder method to get all posts of a specific category
    List<Post> findByCategory(Category category);

    //we could also use hibernate findByTitleContaining(String title) to do same search as searchByTitleContaining
    @Query("select p  from Post p where p.title like :key")
    List<Post> searchByTitleContaining(@Param("key") String keyword);
}
