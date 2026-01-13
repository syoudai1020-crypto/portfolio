package com.example.demo.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

public class DiaryForm {
    private Integer id;

    @NotBlank
    @Size(min=1, max=30)
    private String title;
	
    @NotBlank
    @Size(min=1, max=255)
    private String body;
    
    private MultipartFile imageFile;
    private String currentImgPath;
    private boolean deleteImage;

    // コンストラクタ
    public DiaryForm() {}
	
    public DiaryForm(Integer id, String title, String body, String currentImgPath) {
    	this.id = id;
    	this.title = title;
        this.body = body;
        this.currentImgPath = currentImgPath;
    }

    // --- 手動でGetter/Setterを作成（これが一番大事！） ---
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getBody() { return body; }
    public void setBody(String body) { this.body = body; }

    public MultipartFile getImageFile() { return imageFile; }
    public void setImageFile(MultipartFile imageFile) { this.imageFile = imageFile; }

    public String getCurrentImgPath() { return currentImgPath; }
    public void setCurrentImgPath(String currentImgPath) { this.currentImgPath = currentImgPath; }

    public boolean isDeleteImage() { return deleteImage; }
    public void setDeleteImage(boolean deleteImage) { this.deleteImage = deleteImage; }
}