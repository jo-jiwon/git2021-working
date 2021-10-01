package com.git.controller.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {
	// CORS(cross origin resource sharing)�� ����
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry
				// ������å�� ������ ���ҽ�
				.addMapping("/**") // ��ü���ҽ��� ���(/todos, /contacts...)
				// ������å�� ����� ������ ���
				// origin(��õ): HTML������ ������ ������ �ּ�
				// HTML�������� ��� ������ �޾ƿԴ����� ����ϰ� ����
				// ���� ����� ������ -> ����
				.allowedOrigins("http://localhost:3000", "http://127.0.0.1:5500/",
						"http://ec2-3-35-139-190.ap-northeast-2.compute.amazonaws.com")
				// ������å���� ����� HTTP�޼���
				.allowedMethods("*"); // ��ü�޼��带 ���(GET, POST, PUT...)
	}
}
