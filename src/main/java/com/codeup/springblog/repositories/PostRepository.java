package com.codeup.springblog.repositories;

import com.codeup.springblog.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;

//TODO 5. Create an interface for posts named PostRepository that extends JpaRepository.
public interface PostRepository extends JpaRepository<Post, Long> {

}
