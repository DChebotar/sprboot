package by.dchebotar.sprboot;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthorizationController {

    @GetMapping("/auth")
    public String auth(@RequestParam(name = "name", required = false, defaultValue = "") String name, Model model){
        model.addAttribute("name", name);
        return "auth";
    }

}
