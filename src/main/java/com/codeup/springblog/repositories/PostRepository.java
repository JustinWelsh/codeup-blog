package com.codeup.springblog.repositories;

import com.codeup.springblog.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

//TODO 5. Create an interface for posts named PostRepository that extends JpaRepository.
public interface PostRepository extends JpaRepository<Post, Long> {

    /* @Query Annotation: runs a SQL QUERY then transforms the results to a
    usable object in JAVA */

    // Repository method name matters, JPA queries can be run from the method name

    // Method name is equivalent to SELECT * FROM posts WHERE title LIKE _____
    // %_____%
//    List<Post> getPostsByTitleLike(@Param("name") String name);
}
