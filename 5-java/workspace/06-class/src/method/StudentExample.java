package method;

public class StudentExample {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		// �ڹٿ����� �� ���� �ʴ� ���
		// ��ü�� �����ϰ� �ʵ忡 ���� ����
		Student s1 = new Student();
		s1.name = "ȫ�浿";
		s1.age = 20;
		s1.semester = 1;
		s1.major = "��ǻ�Ͱ���";
		s1.printPersonInfo();

		// �ٸ� ������� ��ü ����
		Student s3 = new Student("������", 29, 3, "�ڹ���");
//		System.out.println(s3.name + " " + s3.age + " " + s3.semester + " " + s3.major);
		s3.printPersonInfo();
		System.out.println(s3.getMajorInfo());
	}

}
