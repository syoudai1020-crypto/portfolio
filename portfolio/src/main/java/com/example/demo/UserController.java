package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.repository.UserRepository;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    // 登録画面を表示
    @GetMapping("/register")
    public String registerForm() {
        return "register";
    }

    // 登録を実行
    @PostMapping("/register")
    public String register(User user) {
        userRepository.save(user); // DBに保存
        return "redirect:/login";  // 終わったらログイン画面へ
    }
}