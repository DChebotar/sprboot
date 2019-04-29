package by.dchebotar.sprboot.controller;

import by.dchebotar.sprboot.domain.Appeal;
import by.dchebotar.sprboot.domain.Status;
import by.dchebotar.sprboot.domain.User;
import by.dchebotar.sprboot.service.AppealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.sql.Timestamp;
import java.util.Map;



@Controller
public class AppealController {

    @Autowired
    private AppealService appealService;

    @GetMapping("/main")
    public String main(@AuthenticationPrincipal User user, Model model){
        Iterable<Appeal> appeals = appealService.findAll();
        model.addAttribute("appeals", appeals);
        model.addAttribute("user", user);
        return "main";
    }

    @PostMapping("/main")
    public String add(@AuthenticationPrincipal User user, @Valid Appeal appeal, BindingResult bindingResult, Model model){
        appeal.setAuthor(user);
        appeal.setStatus(Status.ACCEPTED);
        appeal.setAddtimestamp(new Timestamp(System.currentTimeMillis()));

        if (bindingResult.hasErrors()){
            Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errorsMap);
            model.addAttribute("appeal", appeal);
        }
        else {
            appealService.save(appeal);
            Iterable<Appeal> appeals = appealService.findAll();
            model.addAttribute("appeals", appeals);
        }
        model.addAttribute("appeal", null);
            return "redirect:/main";

    }

    @PostMapping("/filter")
    public String filterByMyApplications(@AuthenticationPrincipal User user, Model model){
        Iterable<Appeal> appeals = appealService.findByAuthor(user);
        model.addAttribute("appeals", appeals);
        return "main";
    }

    @PostMapping("/allapp")
    public String seeAllApp(Model model){
        Iterable<Appeal> appeals = appealService.findAll();
        model.addAttribute("appeals", appeals);
        return "main";
    }

    @GetMapping("/admin/appeal/{appeal}")
    public String showAppeal(@PathVariable Appeal appeal, Model model){
        appeal.setStatus(Status.INPROGRESS);
        appealService.save(appeal);
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
        appealService.save(appeal);
        return "redirect:/main";
    }

    @PostMapping("/admin/appeal/delete")
    public String delApp(@RequestParam("id") Appeal appeal){
        if (appeal != null) {
            appealService.delete(appeal);
        }
        return "redirect:/main";
    }
}
