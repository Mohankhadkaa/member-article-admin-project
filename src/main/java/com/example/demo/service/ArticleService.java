package com.example.demo.service;

import com.example.demo.model.Article;
import com.example.demo.model.User;
import com.example.demo.repository.ArticleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public Article createArticle(String title, String content, User author) {
        Article article = new Article(title, content, author);
        return articleRepository.save(article);
    }

    public List<Article> findAllArticles() {
        return articleRepository.findAll();
    }

    public List<Article> findArticlesByUserId(Long userId) {
        return articleRepository.findByAuthorId(userId);
    }

    public Optional<Article> findById(Long id) {
        return articleRepository.findById(id);
    }

    public void deleteArticle(Long id) {
        articleRepository.deleteById(id);
    }

    public void deleteArticlesByUserId(Long userId) {
        articleRepository.deleteByAuthorId(userId);
    }
}
