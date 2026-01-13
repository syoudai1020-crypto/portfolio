package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    // 日記IDに紐づくコメントを古い順（投稿順）に取得する
    List<Comment> findByDiaryIdOrderByIdAsc(Integer diaryId);
}