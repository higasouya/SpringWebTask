package com.example.springwebtask.controller;

import com.example.springwebtask.entity.LoginForm;
import com.example.springwebtask.entity.Product;
import com.example.springwebtask.service.PMSService;
import jakarta.servlet.http.HttpSession;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
            model.addAttribute("products", pmsService.findAll());
            return "redirect:/menu";
        }
        model.addAttribute("error", "IDまたはパスワードが不正です");
        return "index";
    }

    @GetMapping("logout")
    public String loguot(){
        return "logout";
    }

    @GetMapping("search")
    public String search(@RequestParam(name = "keyword") String keyword,Model model){

        if(keyword != null){
            model.addAttribute("products", pmsService.findByKeyword(keyword));
            return "menu";
        }
        model.addAttribute("products", pmsService.findAll());
        return "menu";
    }

    @GetMapping("insert")
    public String insert(@ModelAttribute("Product") Product product, Model model){
        model.addAttribute("categories",pmsService.findCategory());
        return "insert";
    }

    @PostMapping("insert")
    public String insertProduct(@Validated @ModelAttribute("Product") Product product,BindingResult bindingResult,Model model){
        if(bindingResult.hasErrors()) {
            model.addAttribute("error", "入力値が不正です");
            return "insert";
        }
        if(pmsService.findById(product.getProduct_id()) != null){
            model.addAttribute("error", "商品IDが重複しています。");
            return "insert";
        }
        var insertProduct = new Product();
        insertProduct.setId(1);
        insertProduct.setProduct_id(product.getProduct_id());
        insertProduct.setName(product.getName());
        insertProduct.setPrice(product.getPrice());
        insertProduct.setCategory_name(product.getCategory_name());
        insertProduct.setDescription(product.getDescription());
        pmsService.insert(insertProduct);
        return "success";
    }

    @GetMapping("menu")
    public String menu(Model model){
        model.addAttribute("products", pmsService.findAll());
        return "menu";
    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable("id")int id,Model model){
        model.addAttribute("product", pmsService.findById(id));
        return "detail";
    }

    @GetMapping("/updateInput")
    public String update(@RequestParam("id")int id,Model model){
        model.addAttribute("Products",pmsService.findById(id));
        model.addAttribute("categories",pmsService.findCategory());
        return "/updateInput";
    }

    @PostMapping("/update")
    public String updateSuc(@Validated @ModelAttribute("Products") Product product,BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()) {
            model.addAttribute("error", "入力値が不正です");
            return "insert";
        }
        if(pmsService.findById(product.getProduct_id()) != null){
            model.addAttribute("error", "商品IDが重複しています。");
            return "insert";
        }
        System.out.println(product);
        System.out.println(pmsService.update(product));
        return "success";
    }

    @GetMapping("/detail/delete/{product_id}")
    public String delete(@PathVariable("product_id")int id){
        pmsService.delete(id);
        return "success";
    }


}
