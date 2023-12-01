package ru.maxima.libraryspringbootproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.maxima.libraryspringbootproject.service.TaskService;

@Controller
@RequestMapping("/auth")
public class IndexController {
    @Autowired
    private TaskService taskService;
    @GetMapping("/index")
    public String getIndex(){
        return "auth/index";
    }

    @GetMapping("/reports")
    public ModelAndView reports() {
        ModelAndView modelAndView = new ModelAndView("reports");
        modelAndView.addObject("progress", taskService.getCompletionProgress());
        return modelAndView;
    }

}
