package com.primetrade_ai.assignment.Controller;


import com.primetrade_ai.assignment.Model.Category;
import com.primetrade_ai.assignment.Model.Post;
import com.primetrade_ai.assignment.Model.User;
import com.primetrade_ai.assignment.Payload.Request.PostRequest;
import com.primetrade_ai.assignment.Payload.Response.MessageResponse;
import com.primetrade_ai.assignment.Repository.CategoryRepository;
import com.primetrade_ai.assignment.Repository.PostRepository;
import com.primetrade_ai.assignment.Repository.UserRepository;
import com.primetrade_ai.assignment.Security.Services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
@CrossOrigin(origins = "*")
public class PostController {

    @Autowired
    PostRepository postRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    UserRepository userRepository;

    
    @GetMapping
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    
    @PostMapping
    public ResponseEntity<?> createPost(@RequestBody PostRequest postRequest) {
       
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();

        User author = userRepository.findById(userDetails.getId())
                .orElseThrow(() -> new RuntimeException("Error: User not found."));

        Category category = categoryRepository.findById(postRequest.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Error: Category not found."));

        Post post = new Post();
        post.setTitle(postRequest.getTitle());
        post.setBody(postRequest.getBody());
        post.setThumbnail(postRequest.getThumbnail());
        post.setIsFeatured(postRequest.getIsFeatured() != null && postRequest.getIsFeatured());
        post.setDateTime(LocalDateTime.now());
        post.setAuthor(author);
        post.setCategory(category);

        postRepository.save(post);

        return ResponseEntity.ok(new MessageResponse("Post created successfully!"));
    }

   
    @GetMapping("/{id}")
    public ResponseEntity<?> getPostById(@PathVariable Long id) {
        return postRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

  
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePost(@PathVariable Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();

        return postRepository.findById(id).map(post -> {
            
            if (post.getAuthor().getId().equals(userDetails.getId()) || userDetails.getIsAdmin()) {
                postRepository.delete(post);
                return ResponseEntity.ok(new MessageResponse("Post deleted successfully!"));
            } else {
                return ResponseEntity.status(403).body(new MessageResponse("You are not authorized to delete this post."));
            }
        }).orElse(ResponseEntity.notFound().build());
    }
}
