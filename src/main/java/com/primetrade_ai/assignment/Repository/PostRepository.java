package com.primetrade_ai.assignment.Repository;

import com.primetrade_ai.assignment.Model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    // Needed for the Homepage: List posts by a specific Category
    List<Post> findByCategoryId(Long categoryId);

    // Needed for the "Manage Posts" Dashboard:
    // "SELECT * FROM posts WHERE author_id = ?"
    List<Post> findByAuthorId(Long authorId);
}
