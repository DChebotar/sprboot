package by.dchebotar.sprboot.controller;

import by.dchebotar.sprboot.domain.Appeal;
import by.dchebotar.sprboot.domain.Status;
import by.dchebotar.sprboot.domain.User;
import by.dchebotar.sprboot.repository.AppealRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Timestamp;


@Controller
public class AppealController {

    @Autowired
    private AppealRepository appealRepository;

    @GetMapping("/main")
    public String main(@AuthenticationPrincipal User user, Model model){
        Iterable<Appeal> appeals = appealRepository.findAll();
        model.addAttribute("appeals", appeals);
        model.addAttribute("user", user);
        return "main";
    }

    @PostMapping("/main")
    public String add(@AuthenticationPrincipal User user, @RequestParam String text, Model model){
        Appeal appeal = new Appeal(text, user);
        appealRepository.save(appeal);
        Iterable<Appeal> appeals = appealRepository.findAll();
        model.addAttribute("appeals", appeals);
        return "main";
    }

    @PostMapping("/filter")
    public String filterByMyApplications(@AuthenticationPrincipal User user, Model model){
        Iterable<Appeal> appeals = appealRepository.findByAuthor(user);
        model.addAttribute("appeals", appeals);
        return "main";
    }

    @PostMapping("/allapp")
    public String seeAllApp(Model model){
        Iterable<Appeal> appeals = appealRepository.findAll();
        model.addAttribute("appeals", appeals);
        return "main";
    }

    @GetMapping("/admin/appeal/{appeal}")
    public String showAppeal(@PathVariable Appeal appeal, Model model){
        appeal.setStatus(Status.INPROGRESS);
        appealRepository.save(appeal);
        model.addAttribute("text", appeal.getText());
        return "/appeal";
    }

    @PostMapping("/admin/appeal")
    public String getResponse(@RequestParam("id") Appeal appeal,
                              @RequestParam String response,
                              Model model){
        if (response == null || response.isEmpty()){
            return "redirect:/main";
        }
        appeal.setResponse(response);
        appeal.setStatus(Status.DONE);
        appeal.setDonetimestamp(new Timestamp(System.currentTimeMillis()));
        appealRepository.save(appeal);
        return "redirect:/main";
    }

    @PostMapping("/admin/appeal/delete")
    public String delApp(@RequestParam("id") Appeal appeal){
        if (appeal != null) {
            appealRepository.delete(appeal);
        }
        return "redirect:/main";
    }
}
