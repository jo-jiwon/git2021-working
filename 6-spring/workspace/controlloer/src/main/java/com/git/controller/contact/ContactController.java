package com.git.controller.contact;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicLong;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ContactController {
	public SortedMap<Long, Contact> contacts = Collections
			.synchronizedSortedMap(new TreeMap<Long, Contact>(Collections.reverseOrder()));
	// id�� ����
	public AtomicLong maxId = new AtomicLong();

	// contact �����ȸ
	@GetMapping(value = "/contacts")
	public Collection<Contact> getContacts() {
		// �� �� ���
		return new ArrayList<Contact>(contacts.values());
	}

	// contact 1�� �߰�
	@PostMapping(value = "/contacts")
	public Contact addContact(@RequestBody Contact contact, HttpServletResponse res) {
		// �̸�, ����ó, (�̸���) ���� ���� ���� ��� ����ó���ض�
		if ((contact.getName() == null || contact.getName().isEmpty())
				|| (contact.getPhone() == null || contact.getPhone().isEmpty())
		/* || (contact.getEmail() == null || contact.getEmail().isEmpty()) */) {
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

		// �߰��� ��ü�� ��ȯ
		return contactItem;
	}

	// contact 1�� ����
	@DeleteMapping(value = "/contacts/{id}")
	// id ���� path variable
	public boolean removeContact(@PathVariable long id, HttpServletResponse res) {

		// �ش� id�� ������ 1���� ������
		Contact contact = contacts.get(Long.valueOf(id));

		// �ش� id�� �����Ͱ� ���� ���
		if (contact == null) {
			// not found(404) : �ش� ��ο� ���ҽ� ����
			res.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return false;
		}

		// ���� ����
		contacts.remove(Long.valueOf(id));
		return true;
	}

	// contact 1�� ����
	@PutMapping(value = "/contacts/{id}")
	// id ���� path variable
	public Contact modifyContact(@PathVariable long id, @RequestBody Contact contact, HttpServletResponse res) {
		// �ش� id�� ������ 1���� ������
		Contact findItem = contacts.get(Long.valueOf(id));

		// �ش� id�� �����Ͱ� ���� ���
		if (findItem == null) {
			// not found(404) : �ش� ��ο� ���ҽ� ����
			res.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}

		// ������ ���� ����
		// �̸�, ����ó, (�̸���)�� �ϳ��� ���̸� ����ó��
		if ((contact.getName() == null || contact.getName().isEmpty())
				|| (contact.getPhone() == null || contact.getPhone().isEmpty())
		/* || (contact.getEmail() == null || contact.getEmail().isEmpty()) */) {
			// bad request(400) : Ŭ���̾�Ʈ ����
			// ������ ���°ų�, ��û���� �߸������� ���
			res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return null;
		}

		// �����Ͱ� ����
		findItem.setName(contact.getName());
		findItem.setPhone(contact.getPhone());
		findItem.setEmail(contact.getEmail());
		findItem.setDescription(contact.getDescription());
		return findItem;

	}
}
