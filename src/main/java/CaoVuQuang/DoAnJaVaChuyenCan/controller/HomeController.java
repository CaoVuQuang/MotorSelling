package CaoVuQuang.DoAnJaVaChuyenCan.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {
    @GetMapping
    public String home(){
        return "admin";
    }

    @GetMapping("/about")
    public String about(){
        return "home/about";
    }

    @GetMapping("/contact")
    public String contact(){
        return "home/contact";
    }

    @GetMapping("/admin")
    public String admin(){
        return "layoutAdmin";
    }
}
