package com.git.helloclient1;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

//서비스 컴포넌트
//1. 외부 시스템 통신
//2. 데이터 트랜잭션 처리
@Service
public class HelloClientService {

	@RabbitListener(queues = "test.hello.1")
	public void receiveMessage(String message) {
		System.out.println("-- test.hello.1 --");
		System.out.println(message);
	}
}
