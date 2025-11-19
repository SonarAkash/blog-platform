package com.primetrade_ai.assignment.Repository;

import com.primetrade_ai.assignment.Model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    
    List<Post> findByCategoryId(Long categoryId);

    
    List<Post> findByAuthorId(Long authorId);
}
