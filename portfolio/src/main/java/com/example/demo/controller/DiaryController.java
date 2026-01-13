package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Comment;
import com.example.demo.entity.Diary;
import com.example.demo.form.DiaryForm;
import com.example.demo.repository.CommentRepository;
import com.example.demo.repository.DiaryRepository;
import com.example.demo.service.DiaryService;

@Controller
public class DiaryController {
    
    @Autowired
    DiaryService diaryService;
    
    @Autowired
    DiaryRepository diaryRepository;
    
    @Autowired
    private CommentRepository commentRepository;

    @GetMapping("/diary/")
    public String index(Model model, @RequestParam(name = "search", required = false) String searchWord) {
        List<Diary> diaries;
        if (searchWord != null && !searchWord.isEmpty()) {
            diaries = diaryRepository.findByTitleContainingOrBodyContainingOrderByIdDesc(searchWord, searchWord);
        } else {
            diaries = diaryService.getList();
        }
        model.addAttribute("diaries", diaries);
        model.addAttribute("searchWord", searchWord);
        return "list";
    }

    @GetMapping("/diary/{id}")
    public String show(@PathVariable Integer id, Model model, @AuthenticationPrincipal UserDetails user) {
        Diary diary = diaryService.getOne(id);
        model.addAttribute("diary", diary);
        // ログイン中のユーザー名を "loginUser" として渡す
        model.addAttribute("loginUser", user.getUsername());

        List<Comment> comments = diaryService.getComments(id);
        model.addAttribute("comments", comments);
        return "show";
    }

    @GetMapping("/diary/new")
    public String getNewPage(Model model) {
        DiaryForm form = new DiaryForm();
        model.addAttribute("diaryForm", form);
        return "new";
    }
    
    @PostMapping("/diary/new")
    public String create(@ModelAttribute @Validated DiaryForm diaryForm, 
                         BindingResult result, 
                         Model model,
                         @AuthenticationPrincipal UserDetails user) {
        if (result.hasErrors()) {
            return "new";
        }
        
        // 1. 画像を保存してパスを取得
        String savedPath = diaryService.saveImage(diaryForm.getImageFile());
        
        // 2. 5つの引数でServiceを呼び出す
        diaryService.create(
            diaryForm.getTitle(), 
            diaryForm.getBody(), 
            diaryForm.getImageFile(), 
            user.getUsername(),
            savedPath 
        );
        
        return "redirect:/diary/";
    }

    @GetMapping("/diary/{id}/edit")
    public String getEditPage(Model model, @PathVariable Integer id, @AuthenticationPrincipal UserDetails user) {
        Diary diary = diaryService.getOne(id);
        if (!diary.getUsername().equals(user.getUsername())) {
            return "redirect:/diary/";
        }
        DiaryForm form = new DiaryForm();
        form.setId(diary.getId());
        form.setTitle(diary.getTitle());
        form.setBody(diary.getBody());
        model.addAttribute("diaryForm", form);
        return "edit";
    }

    @PostMapping("/diary/update")
    public String update(@ModelAttribute @Validated DiaryForm diaryForm, 
                         BindingResult result, 
                         Model model,
                         @AuthenticationPrincipal UserDetails user) {
        if (result.hasErrors()) {
            return "edit";
        }
        Diary diary = diaryService.getOne(diaryForm.getId());
        if (!diary.getUsername().equals(user.getUsername())) {
            return "redirect:/diary/";
        }
        diaryService.updateDiary(diaryForm);
        return "redirect:/diary/";
    }

    @PostMapping("/diary/{id}/delete")
    public String delete(@PathVariable Integer id, @AuthenticationPrincipal UserDetails user) {
        Diary diary = diaryService.getOne(id);
        if (!diary.getUsername().equals(user.getUsername())) {
            return "redirect:/diary/";
        }
        diaryService.delete(diary);
        return "redirect:/diary/";
    }

    @PostMapping("/diary/{id}/comment")
    public String addComment(@PathVariable Integer id, 
                             @RequestParam String content, 
                             @AuthenticationPrincipal UserDetails user) {
        diaryService.addComment(id, content, user.getUsername());
        return "redirect:/diary/" + id;
    }
}