package static_;

// Member Ŭ������ ����
public class Member {
	// Member Ŭ���� ��ü���� ��� ����� �� �ִ� ���� ��
	// �������� ��
	final static String SERVICE_NAME = "����� ����";
	// ���� ��
	static int memberCount = 0;

	String name;
	String id;
	String password;
	int age;

	// �����ڸ� ����
	// �̸��� id�� �ʱ�ȭ�� ������
	Member(String name, String id) {
		this.name = name;
		this.id = id;
		memberCount++; // Member��ü�� ������ �� ȸ�� ���� ������Ŵ
	}

	// static �޼���
	// ��ü �������� ȣ���ؼ� ����� �� �ִ� �޼���
	static void printServiceName() {
		// static �������� this ���Ұ�
		// this�� ������ ��ü�� ����Ŵ
		// System.out.println(this.serviceName);

		// static ������ ��ü ������ �ƴ� Ŭ���� ����(�޼��� ����)�� ������
		System.out.println(SERVICE_NAME);
	}

	// static �޼��忡�� non-static �ʵ带 ���� �� ���� this.name X
	static void printNameWithServiceName(String name) {
		System.out.println(SERVICE_NAME + ": " + name);

	}
}
