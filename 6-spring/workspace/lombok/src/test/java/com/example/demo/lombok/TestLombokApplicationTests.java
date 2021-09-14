package com.example.demo.lombok;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.lombok.Member;

@SpringBootTest
class TestLombokApplicationTests {

	@Test
	void contextLoads() {
		Member member = new Member();
		member.setId(1);
		member.setName("hong");
		
		assertEquals(1, member.getId());
		assertEquals("hong", member.getName());
	}

}
