package com.example.demo.entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;


@Entity
@Data
public class Diary {
	 public Diary() {}
	
	 public Diary(String title, String body, String imgPath) {
		    this.title = title;
		    this.body = body;
            this.imgPath = imgPath;
	 }
	
	 

		@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column
	    private Integer id;
		
	    @Column
	    private String title;
		
	    @Column
	    private String body;
	    
	    @Column
	    private String userId;
	    
	    @Column
	    private String username;
	    
	    @Column(name = "img_path") // DBのカラム名に合わせて指定
	    private String imgPath;
		
	    @Column(updatable = false, nullable = false)
	    @CreationTimestamp
	    private LocalDateTime createdAt;
		
	    @Column(nullable = false)
	    @UpdateTimestamp
	    private LocalDateTime updatedAt;
	    
	    public String getFormattedCreatedAt() {
	        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
	        return this.createdAt.format(format);
	    }

	    @Override
	    public boolean equals(Object o) {
	        if (this == o) return true;
	        if (!(o instanceof Diary)) return false;
	        Diary diary = (Diary) o;
	        if (id == null || diary.id == null) {
	            return false;
	        }
	        return id.equals(diary.id);
	    }

	    @Override
	    public int hashCode() {
	    	return id != null ? id.hashCode() : super.hashCode();
	    }
	    
}

