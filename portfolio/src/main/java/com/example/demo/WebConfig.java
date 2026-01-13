package com.example.demo;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer; // 重要

@Configuration
@EnableWebSecurity
public class WebConfig implements WebMvcConfigurer { // ★ ここに implements が必要です

    // 画像ファイルを読み込むための設定
	// WebConfig.java 内の該当箇所を修正

	// WebConfig.java (修正ポイント)

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
	    // プロジェクトのルートディレクトリを取得
	    String projectPath = System.getProperty("user.dir");
	    
	    // 【修正】DiaryServiceと同じ「uploaded-images」フォルダを指すようにする
	    Path uploadPath = Paths.get(projectPath, "uploaded-images");
	    String path = uploadPath.toFile().getAbsolutePath();

	    // Macなので "file:" + 絶対パス で指定
	    String resourceLocation = "file:" + path + "/";

	    registry.addResourceHandler("/uploaded-images/**")
	            .addResourceLocations(resourceLocation)
	            .setCachePeriod(0); // 開発中はキャッシュを無効化
	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	    http
	        .csrf(csrf -> csrf.disable())
	        .authorizeHttpRequests(auth -> auth
	            // ★重要：ここにも /uploaded-images/** を追加して、セキュリティ制限を除外します
	            .requestMatchers("/login", "/register", "/css/**", "/js/**", "/error", "/uploaded-images/**").permitAll()
	            .anyRequest().authenticated()
	        )
	        // ...以下略
            .formLogin(login -> login
                .loginPage("/login")
                .defaultSuccessUrl("/diary/", true)
                .usernameParameter("username")
                .passwordParameter("password")
                .permitAll()
            )
            .logout(logout -> logout.permitAll());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}