package by.dchebotar.sprboot.controller;

import by.dchebotar.sprboot.domain.Application;
import by.dchebotar.sprboot.domain.User;
import by.dchebotar.sprboot.repository.ApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class ApplicationController {

    @Autowired
    private ApplicationRepository applicationRepository;

    @GetMapping("/main")
    public String main(Model model){
        Iterable<Application> applications = applicationRepository.findAll();
        model.addAttribute("applications", applications);
        return "main";
    }

    @PostMapping("/main")
    public String add(@AuthenticationPrincipal User user, @RequestParam String text, Model model){
        Application application = new Application(text, user);
        applicationRepository.save(application);
        Iterable<Application> applications = applicationRepository.findAll();
        model.addAttribute("applications", applications);
        return "main";
    }

    @PostMapping("/filter")
    public String filterByMyApplications(@AuthenticationPrincipal User user, Model model){
        Iterable<Application> applications = applicationRepository.findByAuthor(user);
        model.addAttribute("applications", applications);
        return "main";
    }

    @PostMapping("/allapp")
    public String seeAllApp(Model model){
        Iterable<Application> applications = applicationRepository.findAll();
        model.addAttribute("applications", applications);
        return "main";
    }

}
