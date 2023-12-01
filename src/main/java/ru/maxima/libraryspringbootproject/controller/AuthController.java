package ru.maxima.libraryspringbootproject.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.maxima.libraryspringbootproject.model.User;
import ru.maxima.libraryspringbootproject.service.RegistrationService;
import ru.maxima.libraryspringbootproject.service.TaskService;
import ru.maxima.libraryspringbootproject.service.UserService;
import ru.maxima.libraryspringbootproject.validate.UserValidator;
import jakarta.validation.Valid;


@Controller
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;
    private TaskService taskService;

    private final UserValidator validator;
    private final RegistrationService registrationService;
    @Autowired
    public AuthController(UserService userService, UserValidator validator, RegistrationService registrationService) {
        this.userService = userService;
        this.validator = validator;
        this.registrationService = registrationService;
    }

    @GetMapping("/login")
    public String loginPage(Model model) {
        return "auth/login";
    }

    @GetMapping("/registration")
    public String registrationPage(@ModelAttribute("user") User user) {

        return "auth/registration";
    }

    @PostMapping("/registration")
    public String performRegistration(@ModelAttribute("user") @Valid User user,
                                      BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "auth/registration";
        }
        registrationService.saveRegisteredUser(user);
        return "redirect:/auth/login";
    }


    @PostMapping("/login")
    public String performlogin(@ModelAttribute("user") @Valid User user,
                                      BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "auth/login";
        }
        registrationService.saveRegisteredUser(user);
        return "redirect:/auth/list";
    }
    @GetMapping("/logout")
    public String logOutPage() {
        return "logout";
    }


}
