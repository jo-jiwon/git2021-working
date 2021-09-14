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
	// id�� ����
	public AtomicLong maxId = new AtomicLong();

	// contact �����ȸ
	@GetMapping(value = "/contacts")
	public Collection<Contact> getContacts() {
		return contacts.descendingMap().values();
	}

	// contact 1�� �߰�
	@PostMapping(value = "/contacts")
	public Contact postContact(@RequestBody Contact contact, HttpServletResponse res) {
		// �̸�, ����ó, �̸��� ���� ���� ���� ��� ����ó���ض�
		if (contact.getName() == null || contact.getName().isEmpty() && contact.getPhone() == null
				|| contact.getPhone().isEmpty() && contact.getEmail() == null || contact.getEmail().isEmpty()) {
			// ���� �� �� ��� Ŭ���̾�Ʈ �����̹Ƿ� 4xx (��û���� �߸� ����)

			res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return null;
		}

		// id�� ����
		Long currentId = maxId.incrementAndGet();

		// �Է� ���� �����ͷ� contact��ü ����
		Contact contactItem = Contact.builder().id(currentId).name(contact.getName()).phone(contact.getPhone())
				.email(contact.getEmail()).createdTime(new Date().getTime()).description(contact.getDescription())
				.build();

		// contact ��ϰ�ü �߰�
		contacts.put(currentId, contactItem);

		// ���ҽ� ����
		res.setStatus(HttpServletResponse.SC_CREATED);

		return contactItem;

	}
}
