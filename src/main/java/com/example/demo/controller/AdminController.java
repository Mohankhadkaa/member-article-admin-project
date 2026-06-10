package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.ArticleService;
import com.example.demo.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final ArticleService articleService;
    private final UserRepository userRepository;

    public AdminController(UserService userService, ArticleService articleService, UserRepository userRepository) {
        this.userService = userService;
        this.articleService = articleService;
        this.userRepository = userRepository;
    }

    @GetMapping("/members")
    public String listMembers(Model model, Authentication auth) {
        List<User> members = userService.findAllUsers();
        User currentUser = getCurrentUser(auth);
        model.addAttribute("members", members);
        model.addAttribute("currentUser", currentUser);
        return "members";
    }

    @PostMapping("/members/delete/{id}")
    public String deleteMember(@PathVariable Long id,
                               Authentication auth,
                               RedirectAttributes redirect) {
        User currentUser = getCurrentUser(auth);

        if (currentUser.getId().equals(id)) {
            redirect.addFlashAttribute("error", "You cannot delete yourself.");
            return "redirect:/admin/members";
        }

        articleService.deleteArticlesByUserId(id);
        userService.deleteUser(id);
        redirect.addFlashAttribute("success", "Member and their articles deleted.");
        return "redirect:/admin/members";
    }

    private User getCurrentUser(Authentication auth) {
        String username = auth.getName();
        return userRepository.findByUsername(username).orElseThrow();
    }
}
