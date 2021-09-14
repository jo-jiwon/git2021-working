package com.git.controller.contact;

import java.util.Collection;
import java.util.Date;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.atomic.AtomicLong;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ContactController {
	public ConcurrentSkipListMap<Long, Contact> contacts = new ConcurrentSkipListMap<Long, Contact>();
	// id값 생성
	public AtomicLong maxId = new AtomicLong();

	// contact 목록조회
	@GetMapping(value = "/contacts")
	public Collection<Contact> getContacts() {
		return contacts.descendingMap().values();
	}

	// contact 1건 추가
	@PostMapping(value = "/contacts")
	public Contact postContact(@RequestBody Contact contact, HttpServletResponse res) {
		// 이름, 연락처, 이메일 값을 넣지 않을 경우 에러처리해라
		if (contact.getName() == null || contact.getName().isEmpty() && contact.getPhone() == null
				|| contact.getPhone().isEmpty() && contact.getEmail() == null || contact.getEmail().isEmpty()) {
			// 값이 빈값 이 경우 클라이언트 오류이므로 4xx (요청값을 잘못 보냄)

			res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return null;
		}

		// id값 생성
		Long currentId = maxId.incrementAndGet();

		// 입력 받은 데이터로 contact객체 생성
		Contact contactItem = Contact.builder().id(currentId).name(contact.getName()).phone(contact.getPhone())
				.email(contact.getEmail()).createdTime(new Date().getTime()).description(contact.getDescription())
				.build();

		// contact 목록객체 추가
		contacts.put(currentId, contactItem);

		// 리소스 생성
		res.setStatus(HttpServletResponse.SC_CREATED);

		return contactItem;

	}
}
