package com.example.springwebtask.controller;

import com.example.springwebtask.entity.LoginForm;
import com.example.springwebtask.service.PMSService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PMSController {

    @Autowired
    private HttpSession session;

    @Autowired
    private PMSService pmsService;


    @GetMapping("index")
    public String index(@ModelAttribute("loginForm")LoginForm loginForm) {
        return "index";
    }

    @PostMapping("index")
    public String login(@Validated @ModelAttribute("loginForm")LoginForm loginForm, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()) {
            model.addAttribute("error", "IDまたはパスワードが不正です");
            return "index";
        }
        if(pmsService.findAcc(loginForm) != null){
            var user = pmsService.findAcc(loginForm);
            session.setAttribute("user",user);
            return "menu";
        }
        model.addAttribute("error", "IDまたはパスワードが不正です");
        return "index";
    }

    @GetMapping("logout")
    public String loguot(){
        return "logout";
    }

    @GetMapping("search")
    public String search(@RequestParam(name = "keyword") String keyword){
        if(keyword != null){
            var list = pmsService.findByKeyword(keyword);
            return "menu";
        }
        var list = pmsService.findAll();
        return "menu";
    }
}
