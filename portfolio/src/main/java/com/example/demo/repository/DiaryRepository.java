package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Diary;

public interface DiaryRepository extends JpaRepository<Diary, Integer> 
{
	 List<Diary> findAllByOrderByIdDesc();
	 
	// タイトル または 本文 にキーワードが含まれる日記を検索（IDの新しい順）
	    List<Diary> findByTitleContainingOrBodyContainingOrderByIdDesc(String titleKeyword, String bodyKeyword);
	    
	    // 特定のユーザーの日記の中から検索する場合
	    List<Diary> findByUsernameAndTitleContainingOrderByIdDesc(String username, String titleKeyword);
	
}
