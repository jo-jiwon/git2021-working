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

	// test case: �� �� �߰�
	// event flow(ó���帧): �� �� 1���� �߰���
	// pre-condition(��������): ����
	// expected result(������): �� �� ��Ͽ� �߰��� ������ ����
	@Test
	void addContact() {
		// given - �׽�Ʈ ������ �� ��ü �غ�
		ContactController controller = new ContactController();
		// expected ���� ����
		Contact expected = Contact.builder().name("������").phone("01091053768").email("jjw3768@naver.com").build();

		// when - test case event flow
		// ��¥ Mock�� �־���
		controller.addContact(expected, new MockHttpServletResponse());

		// then - �������� ��������� ��
		// assertEquals - ��ġ��

		// ��ü��Ͽ� �߰��� ���� ������
		List<Contact> contacts = (List<Contact>) controller.getContacts();
		// Contact�� ������ = arrayList.get(�ε���);
		Contact actual = contacts.get(0);

		// ������ ��
		assertEquals(1, actual.getId());
		assertEquals(expected.getName(), actual.getName());
		assertEquals(expected.getPhone(), actual.getPhone());
		assertEquals(expected.getEmail(), actual.getEmail());
	}

	// test case: �� �� ����
	// event flow(ó���帧): �� �� 1���� ������
	// pre-condition(��������): �� �� ������ �ּ� 1�� �̻� ����
	// expected result(������): �� �� ��Ͽ� ������ ������ �������
	@Test
	void removeConatact() {
		// given - �׽�Ʈ ������ �� ��ü �غ�
		// ������ 1�� �߰� �Ǿ��־����
		ContactController controller = new ContactController();
		// Test �����Ͱ�
		Contact testItem = Contact.builder().name("test").phone("12341234123").email("test@naver.com").build();
		// ��¥ Mock�� �־���
		controller.addContact(testItem, new MockHttpServletResponse());

		List<Contact> beforeContacts = (List<Contact>) controller.getContacts();
		// ���������� ��� ũ�Ⱑ 1
		assertEquals(1, beforeContacts.size());

		// when - test case event flow
		controller.removeContact(1, new MockHttpServletResponse());

		// then - �������� ��������� ��
		List<Contact> afterContacts = (List<Contact>) controller.getContacts();
		// ���� �� �����ȸ �� ���� ũ�Ⱑ 0
		assertEquals(0, afterContacts.size());
	}

	// test case: �� �� ����
	// event flow(ó���帧):
//		 basic flow(�����帧):
//			 1. �� �� 1�ǿ� ���ؼ� �޸��� ����
//		 alternative flow(�����帧):
//			 1. ��Ͽ� �ִ� id������ ������ ���� - 404
//			 2. �޸��� �� �� �Ǵ� ������ ��ü�� �Ⱥ��� - 400
	// pre-condition(��������): �� �� ������ �ּ� 1�� �̻� ����
	// expected result(������): �� �� ��Ͽ� ������ ������ �������
	@Test
	void modifyConatact() {
		// given - �׽�Ʈ ������ �� ���� �غ�
		ContactController controller = new ContactController();
		// Test �����Ͱ�
		Contact testItem = Contact.builder().name("test").phone("12341234123").email("test@naver.com").build();
		// ��¥ Mock�� �־���
		controller.addContact(testItem, new MockHttpServletResponse());
		// ������ �׽�Ʈ �����Ͱ�
		String expectedNameResult = "modify test name";
		String expectedPhoneResult = "modify test 01012341234";
		String expectedEmailResult = "modify test text@daum.net";
		Contact modifyData = Contact.builder().name(expectedNameResult).phone(expectedPhoneResult)
				.email(expectedEmailResult).build();

		HttpServletResponse res = new MockHttpServletResponse();

		// basic flow - �������� ��Ȳ
		// when - test case event flow
		// id�� 1�� contact�� name,phone,email�� ����
		controller.modifyContact(1, modifyData, res);

		// then - �������� ��������� ��
		List<Contact> contacts = (List<Contact>) controller.getContacts();
		assertEquals(expectedNameResult, contacts.get(0).getName());
		assertEquals(expectedPhoneResult, contacts.get(0).getPhone());
		assertEquals(expectedEmailResult, contacts.get(0).getEmail());

		// altanative flow - 1. id���� ���� ���
		// when - id�� 2�� ����
		Contact resultContactId = controller.modifyContact(2, modifyData, res);

		// then - �������� ��������� ��
		assertNull(resultContactId);
		assertEquals(HttpServletResponse.SC_NOT_FOUND, res.getStatus());

		// altanative flow - 2-1. name���� null ���
		// when
		Contact resultContactNull = controller.modifyContact(1, new Contact(), res);
//		Contact resultContactNameNull = controller.modifyContact(1, new Contact(), res);
//		Contact resultContactPhoneNull = controller.modifyContact(1, new Contact(), res);
//		Contact resultContactEmailNull = controller.modifyContact(1, new Contact(), res);

		// then - �������� ��������� ��
		assertNull(resultContactNull);
//		assertNull(resultContactNameNull);
//		assertNull(resultContactPhoneNull);
//		assertNull(resultContactEmailNull);
		assertEquals(HttpServletResponse.SC_BAD_REQUEST, res.getStatus());

		// altanative flow - 2-2. name���� ���� ���
		// when
		Contact resultContactEmpty = controller.modifyContact(1, Contact.builder().name("").phone("").email("").build(),
				res);
//		Contact resultContactNameEmpty = controller.modifyContact(1, Contact.builder().name("").build(), res);
//		Contact resultContactPhoneEmpty = controller.modifyContact(1, Contact.builder().phone("").build(), res);
//		Contact resultContactEmailEmpty = controller.modifyContact(1, Contact.builder().email("").build(), res);

		// then - �������� ��������� ��
		assertNull(resultContactEmpty);
//		assertNull(resultContactNameEmpty);
//		assertNull(resultContactPhoneEmpty);
//		assertNull(resultContactEmailEmpty);
		assertEquals(HttpServletResponse.SC_BAD_REQUEST, res.getStatus());

	}
}
