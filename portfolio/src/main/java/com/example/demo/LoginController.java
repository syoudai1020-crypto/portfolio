package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login(HttpServletResponse response) {
        // ブラウザに「これはHTMLです」とはっきり伝えるための設定（念のため）
        response.setContentType("text/html;charset=UTF-8");
        return "login";
    }
}