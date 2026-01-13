package com.example.demo.controller;



import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.form.InquireForm;


@Controller
public class AboutController {
	@GetMapping("/about") 
	public String getCreatePage(Model model) {
	    InquireForm form2 = new InquireForm();
	    model.addAttribute("inquireForm", form2);
	    return "about";
	}
	
	@PostMapping("/about")
    public String create(@ModelAttribute @Validated InquireForm inquireForm, BindingResult result, Model model) {
    if (result.hasErrors()) {
        return "about";
     }
    System.out.println(inquireForm.toString());
     return "redirect:/about";
}

}