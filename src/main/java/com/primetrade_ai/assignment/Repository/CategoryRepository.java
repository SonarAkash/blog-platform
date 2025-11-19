package com.primetrade_ai.assignment.Repository;


import com.primetrade_ai.assignment.Model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    
}
