package com.git.hello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@SpringBootConfiguration: ������ ������ �� �� �ֵ��� ��������
//@EnableAutoConfiguration: ����ϴ� �������� ���� �ڵ����� ȯ���� ������
// -> spring-boot-starter-web: embed Tomcat �������� ������, 8080��Ʈ ������
// -> spring-boot-devtools: �ڵ带 ��ġ�� ������ �ٽ� ��������

//@ComponentScan: ������Ʈ���� �˻��Ͽ�(mainŬ���� ����/���� ��ĳ���鿡��) �̱������� ��ü������ ��
// -> Spring Framework���� ������Ʈ(��-@Controller) ������̼��� �ִ� Ŭ�������� �˻���
// -> Spring Framework���� �̱��� ��ü�� ������

@SpringBootApplication
public class HelloApplication {

	public static void main(String[] args) {
		SpringApplication.run(HelloApplication.class, args);
	}

}