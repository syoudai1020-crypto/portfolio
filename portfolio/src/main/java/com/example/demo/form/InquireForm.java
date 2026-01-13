package com.example.demo.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import lombok.Data;

@Data
public class InquireForm {
    public InquireForm() {};
	
    public InquireForm(String email, String name, String body) {
        this.email= email;
        this.name = name;
        this.body = body;
    }
	
    @NotBlank
    @Size(min=1, max=100)
    private String email;
	
    @NotBlank
    @Size(min=1, max=20)
    private String name;
    
    @NotBlank
    @Size(min=1, max=500)
    private String body;

	
}


