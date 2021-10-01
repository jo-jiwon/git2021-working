package com.git.myworkspace.contact;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ContactController {

	private ContactRepository repo;

	// repository �������̽� ������ �´� ��ü�� Spring�� �����Ͽ� �־���
	@Autowired
	public ContactController(ContactRepository repo) {
		this.repo = repo;
	}

//	public SortedMap<Long, Contact> contacts = Collections
//			.synchronizedSortedMap(new TreeMap<Long, Contact>(Collections.reverseOrder()));
//	// id�� ����
//	public AtomicLong maxId = new AtomicLong();

//	// contact �����ȸ
//	@GetMapping(value = "/contacts")
//	public Collection<Contact> getContacts() {
//		// �� �� ���
//		return new ArrayList<Contact>(contacts.values());
//	}

	// contact �����ȸ
	@GetMapping(value = "/contacts")
	public List<Contact> getContacts() throws InterruptedException {
		// repository.findAll();
		// SELECT * FROM contact;
		return repo.findAll(Sort.by("id").descending());
	}

	// get /contacts/paging?page=0&size=2
	@GetMapping("/contacts/paging")
	public Page<Contact> getContactPaging(@RequestParam int page, @RequestParam int size) {
		// findAll(Pageable page)
		return repo.findAll(PageRequest.of(page, size, Sort.by("id").descending()));
	}

	// contact 1�� �߰�
	@PostMapping(value = "/contacts")
	public Contact addContact(@RequestBody Contact contact, HttpServletResponse res) throws InterruptedException {
		// �̸�, ����ó, (�̸���) ���� ���� ���� ��� ����ó���ض�
		if ((contact.getName() == null || contact.getName().isEmpty())
				|| (contact.getPhone() == null || contact.getPhone().isEmpty())
		/* || (contact.getEmail() == null || contact.getEmail().isEmpty()) */) {

			// ���� �� �� ��� Ŭ���̾�Ʈ �����̹Ƿ� 4xx (��û���� �߸� ����)
			res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return null;
		}

//		// id�� ����
//		Long currentId = maxId.incrementAndGet();

		// �Է� ���� �����ͷ� contact��ü ����
		Contact contactItem = Contact.builder().name(contact.getName()).phone(contact.getPhone())
				.email(contact.getEmail()).createdTime(new Date().getTime()).memo(contact.getMemo()).build();

//		// contact ��ϰ�ü �߰�
//		contacts.put(currentId, contactItem);

		// repository.save(entity)
		// insert into contact(...) values(...)
		Contact contactSaved = repo.save(contactItem);

		// ���ҽ� ����
		res.setStatus(HttpServletResponse.SC_CREATED);

		// �߰��� ��ü�� ��ȯ
		return contactSaved;
	}

	// contact 1�� ����
	@DeleteMapping(value = "/contacts/{id}")
	// id ���� path variable
	public boolean removeContact(@PathVariable long id, HttpServletResponse res) throws InterruptedException {

//		// �ش� id�� ������ 1���� ������
//		Contact contact = contacts.get(Long.valueOf(id));

		// id�� �ش��ϴ� ��ü�� ������
		// Optional null-safe, �ڹ� 1.8 ���� ���
		// repository.findBy(id)
		// select * from contact where id = ?;
		Optional<Contact> contact = repo.findById(id);

		// �ش� id�� �����Ͱ� ���� ���
		if (contact.isEmpty()) {
			// not found(404) : �ش� ��ο� ���ҽ� ����
			res.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return false;
		}

//		// ���� ����
//		contacts.remove(Long.valueOf(id));
		repo.deleteById(id);
		return true;
	}

	// contact 1�� ����
	@PutMapping(value = "/contacts/{id}")
	// id ���� path variable
	public Contact modifyContact(@PathVariable long id, @RequestBody Contact contact, HttpServletResponse res)
			throws InterruptedException {

//		// �ش� id�� ������ 1���� ������
//		Contact findItem = contacts.get(Long.valueOf(id));
		Optional<Contact> contactItem = repo.findById(id);

		// �ش� id�� �����Ͱ� ���� ���
		if (contactItem.isEmpty()) {
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

		Contact contactToSave = contactItem.get();

		contactToSave.setName(contact.getName());
		contactToSave.setPhone(contact.getPhone());
		contactToSave.setEmail(contact.getEmail());
		contactToSave.setMemo(contact.getMemo());

		// repository.save(entity)
		// id�� ������ update, ������ insert
		Contact contactSaved = repo.save(contactToSave);

		return contactSaved;

//		// �����Ͱ� ����
//		findItem.setName(contact.getName());
//		findItem.setPhone(contact.getPhone());
//		findItem.setEmail(contact.getEmail());
//		findItem.setMemo(contact.getMemo());
//		return findItem;

	}
}
