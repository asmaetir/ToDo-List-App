package ru.maxima.libraryspringbootproject.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;


@Controller
@RequestMapping("/auth")
@PreAuthorize("hasRole('USER')")
public class TestController {

  @GetMapping("/test")
  public String showUserProfile(Model model) {
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      String username = authentication.getName();
      model.addAttribute("username", username);
        return "/auth/test";
    }
}
