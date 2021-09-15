package com.git.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;

import com.git.controller.contact.Contact;
import com.git.controller.contact.ContactController;

@SpringBootTest
public class TestContactController {

	// test case: 할 일 추가
	// event flow(처리흐름): 할 일 1건을 추가함
	// pre-condition(사전조건): 없음
	// expected result(예상결과): 할 일 목록에 추가한 데이터 존재
	@Test
	void addContact() {
		// given - 테스트 데이터 및 객체 준비
		ContactController controller = new ContactController();
		// expected 예상값 삽입
		Contact expected = Contact.builder().name("조지원").phone("01091053768").email("jjw3768@naver.com").build();

		// when - test case event flow
		// 가짜 Mock을 넣어줌
		controller.addContact(expected, new MockHttpServletResponse());

		// then - 예상결과와 실제결과를 비교
		// assertEquals - 동치비교

		// 전체목록에 추가한 값을 가져옴
		List<Contact> contacts = (List<Contact>) controller.getContacts();
		// Contact의 실제값 = arrayList.get(인덱스);
		Contact actual = contacts.get(0);

		// 데이터 비교
		assertEquals(1, actual.getId());
		assertEquals(expected.getName(), actual.getName());
		assertEquals(expected.getPhone(), actual.getPhone());
		assertEquals(expected.getEmail(), actual.getEmail());
	}

	// test case: 할 일 삭제
	// event flow(처리흐름): 할 일 1건을 삭제함
	// pre-condition(사전조건): 할 일 데이터 최소 1건 이상 존재
	// expected result(예상결과): 할 일 목록에 삭제한 데이터 없어야함
	@Test
	void removeConatact() {
		// given - 테스트 데이터 및 객체 준비
		// 데이터 1건 추가 되어있어야함
		ContactController controller = new ContactController();
		// Test 데이터값
		Contact testItem = Contact.builder().name("test").phone("12341234123").email("test@naver.com").build();
		// 가짜 Mock을 넣어줌
		controller.addContact(testItem, new MockHttpServletResponse());

		List<Contact> beforeContacts = (List<Contact>) controller.getContacts();
		// 삭제전에는 목록 크기가 1
		assertEquals(1, beforeContacts.size());

		// when - test case event flow
		controller.removeContact(1, new MockHttpServletResponse());

		// then - 예상결과와 실제결과를 비교
		List<Contact> afterContacts = (List<Contact>) controller.getContacts();
		// 삭제 후 목록조회 할 때는 크기가 0
		assertEquals(0, afterContacts.size());
	}

	// test case: 할 일 수정
	// event flow(처리흐름):
//		 basic flow(정상흐름):
//			 1. 할 일 1건에 대해서 메모값을 수정
//		 alternative flow(예외흐름):
//			 1. 목록에 있는 id값으로 삭제를 실행 - 404
//			 2. 메모값을 빈 값 또는 데이터 객체를 안보냄 - 400
	// pre-condition(사전조건): 할 일 데이터 최소 1건 이상 존재
	// expected result(예상결과): 할 일 목록에 삭제한 데이터 없어야함
	@Test
	void modifyConatact() {
		// given - 테스트 데이터 및 객제 준비
		ContactController controller = new ContactController();
		// Test 데이터값
		Contact testItem = Contact.builder().name("test").phone("12341234123").email("test@naver.com").build();
		// 가짜 Mock을 넣어줌
		controller.addContact(testItem, new MockHttpServletResponse());
		// 변경할 테스트 데이터값
		String expectedNameResult = "modify test name";
		String expectedPhoneResult = "modify test 01012341234";
		String expectedEmailResult = "modify test text@daum.net";
		Contact modifyData = Contact.builder().name(expectedNameResult).phone(expectedPhoneResult)
				.email(expectedEmailResult).build();

		HttpServletResponse res = new MockHttpServletResponse();

		// basic flow - 정상적인 상황
		// when - test case event flow
		// id가 1인 contact에 name,phone,email을 수정
		controller.modifyContact(1, modifyData, res);

		// then - 예상결과와 실제결과를 비교
		List<Contact> contacts = (List<Contact>) controller.getContacts();
		assertEquals(expectedNameResult, contacts.get(0).getName());
		assertEquals(expectedPhoneResult, contacts.get(0).getPhone());
		assertEquals(expectedEmailResult, contacts.get(0).getEmail());

		// altanative flow - 1. id값이 없는 경우
		// when - id가 2로 수정
		Contact resultContactId = controller.modifyContact(2, modifyData, res);

		// then - 예상결과와 실제결과를 비교
		assertNull(resultContactId);
		assertEquals(HttpServletResponse.SC_NOT_FOUND, res.getStatus());

		// altanative flow - 2-1. name값이 null 경우
		// when
		Contact resultContactNull = controller.modifyContact(1, new Contact(), res);
//		Contact resultContactNameNull = controller.modifyContact(1, new Contact(), res);
//		Contact resultContactPhoneNull = controller.modifyContact(1, new Contact(), res);
//		Contact resultContactEmailNull = controller.modifyContact(1, new Contact(), res);

		// then - 예상결과와 실제결과를 비교
		assertNull(resultContactNull);
//		assertNull(resultContactNameNull);
//		assertNull(resultContactPhoneNull);
//		assertNull(resultContactEmailNull);
		assertEquals(HttpServletResponse.SC_BAD_REQUEST, res.getStatus());

		// altanative flow - 2-2. name값이 빈값일 경우
		// when
		Contact resultContactEmpty = controller.modifyContact(1, Contact.builder().name("").phone("").email("").build(),
				res);
//		Contact resultContactNameEmpty = controller.modifyContact(1, Contact.builder().name("").build(), res);
//		Contact resultContactPhoneEmpty = controller.modifyContact(1, Contact.builder().phone("").build(), res);
//		Contact resultContactEmailEmpty = controller.modifyContact(1, Contact.builder().email("").build(), res);

		// then - 예상결과와 실제결과를 비교
		assertNull(resultContactEmpty);
//		assertNull(resultContactNameEmpty);
//		assertNull(resultContactPhoneEmpty);
//		assertNull(resultContactEmailEmpty);
		assertEquals(HttpServletResponse.SC_BAD_REQUEST, res.getStatus());

	}
}
