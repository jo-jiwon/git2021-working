package static_;

// Member 클래스를 선언
public class Member {
	// Member 클래스 객체에서 모두 사용할 수 있는 변수 값
	// 고정적인 값
	final static String SERVICE_NAME = "배달의 민족";
	// 변동 값
	static int memberCount = 0;

	String name;
	String id;
	String password;
	int age;

	// 생성자를 선언
	// 이름과 id를 초기화는 생성자
	Member(String name, String id) {
		this.name = name;
		this.id = id;
		memberCount++; // Member객체를 생성할 때 회원 수를 증가시킴
	}

	// static 메서드
	// 객체 생성없이 호출해서 사용할 수 있는 메서드
	static void printServiceName() {
		// static 변수에는 this 사용불가
		// this는 생성된 객체를 가르킴
		// System.out.println(this.serviceName);

		// static 변수는 객체 공간이 아닌 클래스 공간(메서드 공간)에 생성됨
		System.out.println(SERVICE_NAME);
	}

	// static 메서드에는 non-static 필드를 읽을 수 없음 this.name X
	static void printNameWithServiceName(String name) {
		System.out.println(SERVICE_NAME + ": " + name);

	}
}
