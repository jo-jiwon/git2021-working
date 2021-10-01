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

	// repository 인터페이스 구조에 맞는 객체를 Spring에 생성하여 넣어줌
	@Autowired
	public ContactController(ContactRepository repo) {
		this.repo = repo;
	}

//	public SortedMap<Long, Contact> contacts = Collections
//			.synchronizedSortedMap(new TreeMap<Long, Contact>(Collections.reverseOrder()));
//	// id값 생성
//	public AtomicLong maxId = new AtomicLong();

//	// contact 목록조회
//	@GetMapping(value = "/contacts")
//	public Collection<Contact> getContacts() {
//		// 맵 값 목록
//		return new ArrayList<Contact>(contacts.values());
//	}

	// contact 목록조회
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

	// contact 1건 추가
	@PostMapping(value = "/contacts")
	public Contact addContact(@RequestBody Contact contact, HttpServletResponse res) throws InterruptedException {
		// 이름, 연락처, (이메일) 값을 넣지 않을 경우 에러처리해라
		if ((contact.getName() == null || contact.getName().isEmpty())
				|| (contact.getPhone() == null || contact.getPhone().isEmpty())
		/* || (contact.getEmail() == null || contact.getEmail().isEmpty()) */) {

			// 값이 빈값 이 경우 클라이언트 오류이므로 4xx (요청값을 잘못 보냄)
			res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return null;
		}

//		// id값 생성
//		Long currentId = maxId.incrementAndGet();

		// 입력 받은 데이터로 contact객체 생성
		Contact contactItem = Contact.builder().name(contact.getName()).phone(contact.getPhone())
				.email(contact.getEmail()).createdTime(new Date().getTime()).memo(contact.getMemo()).build();

//		// contact 목록객체 추가
//		contacts.put(currentId, contactItem);

		// repository.save(entity)
		// insert into contact(...) values(...)
		Contact contactSaved = repo.save(contactItem);

		// 리소스 생성
		res.setStatus(HttpServletResponse.SC_CREATED);

		// 추가된 객체를 반환
		return contactSaved;
	}

	// contact 1건 삭제
	@DeleteMapping(value = "/contacts/{id}")
	// id 값이 path variable
	public boolean removeContact(@PathVariable long id, HttpServletResponse res) throws InterruptedException {

//		// 해당 id의 데이터 1건을 가져옴
//		Contact contact = contacts.get(Long.valueOf(id));

		// id에 해당하는 객체가 없으면
		// Optional null-safe, 자바 1.8 나온 방식
		// repository.findBy(id)
		// select * from contact where id = ?;
		Optional<Contact> contact = repo.findById(id);

		// 해당 id의 데이터가 없을 경우
		if (contact.isEmpty()) {
			// not found(404) : 해당 경로에 리소스 없음
			res.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return false;
		}

//		// 삭제 수행
//		contacts.remove(Long.valueOf(id));
		repo.deleteById(id);
		return true;
	}

	// contact 1건 수정
	@PutMapping(value = "/contacts/{id}")
	// id 값이 path variable
	public Contact modifyContact(@PathVariable long id, @RequestBody Contact contact, HttpServletResponse res)
			throws InterruptedException {

//		// 해당 id의 데이터 1건을 가져옴
//		Contact findItem = contacts.get(Long.valueOf(id));
		Optional<Contact> contactItem = repo.findById(id);

		// 해당 id의 데이터가 없을 경우
		if (contactItem.isEmpty()) {
			// not found(404) : 해당 경로에 리소스 없음
			res.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}

		// 데이터 검증 로직
		// 이름, 연락처, (이메일)이 하나라도 빈값이면 에러처리
		if ((contact.getName() == null || contact.getName().isEmpty())
				|| (contact.getPhone() == null || contact.getPhone().isEmpty())
		/* || (contact.getEmail() == null || contact.getEmail().isEmpty()) */) {
			// bad request(400) : 클라이언트 오류
			// 빈값으로 보냈거나, 요청값을 잘못보냈을 경우
			res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return null;
		}

		Contact contactToSave = contactItem.get();

		contactToSave.setName(contact.getName());
		contactToSave.setPhone(contact.getPhone());
		contactToSave.setEmail(contact.getEmail());
		contactToSave.setMemo(contact.getMemo());

		// repository.save(entity)
		// id가 있으면 update, 없으면 insert
		Contact contactSaved = repo.save(contactToSave);

		return contactSaved;

//		// 데이터값 변경
//		findItem.setName(contact.getName());
//		findItem.setPhone(contact.getPhone());
//		findItem.setEmail(contact.getEmail());
//		findItem.setMemo(contact.getMemo());
//		return findItem;

	}
}
