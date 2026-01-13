package com.example.demo.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.Comment;
import com.example.demo.entity.Diary;
import com.example.demo.form.DiaryForm;
import com.example.demo.repository.CommentRepository;
import com.example.demo.repository.DiaryRepository;

@Service
public class DiaryService {
    @Autowired
    DiaryRepository repo;
    @Autowired
    private CommentRepository commentRepository;

    public void addComment(Integer diaryId, String content, String username) {
        Comment comment = new Comment();
        comment.setDiaryId(diaryId);
        comment.setContent(content);
        comment.setUsername(username);
        commentRepository.save(comment);
    }

 // DiaryService.java の saveImage メソッド内
 // DiaryService.java 修正後の saveImage
    public String saveImage(MultipartFile imageFile) {
        if (imageFile == null || imageFile.isEmpty()) return null;
        try {
            // プロジェクトのルートディレクトリを取得
            String projectPath = System.getProperty("user.dir");
            
            // 【修正】src/main/resources/... を通さず、直接「uploaded-images」フォルダを指定
            Path uploadPath = Paths.get(projectPath, "uploaded-images"); 

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String originalName = imageFile.getOriginalFilename();
            String extension = originalName.substring(originalName.lastIndexOf("."));
            String fileName = UUID.randomUUID().toString() + extension;

            Path filePath = uploadPath.resolve(fileName);
            Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            // ブラウザからアクセスするためのURLパスを返す
            return "/uploaded-images/" + fileName;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("画像の保存に失敗しました: " + e.getMessage());
        }
    }

    // ★重要：ここが引数5つの定義です
    public void create(String title, String body, MultipartFile imageFile, String username, String imgPath) {
        Diary diary = new Diary();
        diary.setTitle(title);
        diary.setBody(body);
        diary.setUsername(username);
        diary.setImgPath(imgPath); // ここでパスをセット
        
        repo.save(diary);
    }

    @Transactional
    public void updateDiary(DiaryForm form) {
        Diary diary = repo.findById(form.getId()).orElseThrow();
        if (form.getImageFile() != null && !form.getImageFile().isEmpty()) {
            String imgPath = saveImage(form.getImageFile());
            diary.setImgPath(imgPath);
        }
        diary.setTitle(form.getTitle());
        diary.setBody(form.getBody());
        repo.saveAndFlush(diary);
    }

    public List<Diary> getList() { return repo.findAllByOrderByIdDesc(); }
    public Diary getOne(Integer id) { return repo.findById(id).orElseThrow(); }
    public void delete(Diary diary) { repo.delete(diary); }

	public List<Comment> getComments(Integer id) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}
}