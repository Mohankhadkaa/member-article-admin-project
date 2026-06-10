package com.example.demo.repository;

import com.example.demo.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    List<Article> findByAuthorId(Long authorId);
    void deleteByAuthorId(Long authorId);
}
