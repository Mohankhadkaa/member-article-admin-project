package com.example.demo.controller;

import com.example.demo.model.Article;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.ArticleService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/articles")
public class ArticleController {

    private final ArticleService articleService;
    private final UserRepository userRepository;

    public ArticleController(ArticleService articleService, UserRepository userRepository) {
        this.articleService = articleService;
        this.userRepository = userRepository;
    }

    @GetMapping
    public String listArticles(Model model, Authentication auth) {
        User currentUser = getCurrentUser(auth);
        List<Article> articles;

        if (currentUser.getRole() == Role.ADMIN) {
            articles = articleService.findAllArticles();
        } else {
            articles = articleService.findArticlesByUserId(currentUser.getId());
        }

        model.addAttribute("articles", articles);
        model.addAttribute("currentUser", currentUser);
        return "articles";
    }

    @PostMapping("/add")
    public String addArticle(@RequestParam String title,
                             @RequestParam String content,
                             Authentication auth,
                             RedirectAttributes redirect) {
        User currentUser = getCurrentUser(auth);
        articleService.createArticle(title, content, currentUser);
        redirect.addFlashAttribute("success", "Article added!");
        return "redirect:/articles";
    }

    @PostMapping("/delete/{id}")
    public String deleteArticle(@PathVariable Long id,
                                Authentication auth,
                                RedirectAttributes redirect) {
        User currentUser = getCurrentUser(auth);
        Article article = articleService.findById(id).orElse(null);

        if (article == null) {
            redirect.addFlashAttribute("error", "Article not found.");
            return "redirect:/articles";
        }

        boolean isAdmin = currentUser.getRole() == Role.ADMIN;
        boolean isOwner = article.getAuthor().getId().equals(currentUser.getId());

        if (isAdmin || isOwner) {
            articleService.deleteArticle(id);
            redirect.addFlashAttribute("success", "Article deleted.");
        } else {
            redirect.addFlashAttribute("error", "You can only delete your own articles.");
        }

        return "redirect:/articles";
    }

    private User getCurrentUser(Authentication auth) {
        String username = auth.getName();
        return userRepository.findByUsername(username).orElseThrow();
    }
}
