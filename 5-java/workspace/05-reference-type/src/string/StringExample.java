package string;

public class StringExample {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		// �ڹٿ����� ���ڿ� �ֵ���ǥ
		String name1 = "������";
		String name2 = "������";
		// String ��ġ�񱳿��� �� ����� ���� �� ��
		System.out.println(name1 == name2);
		// String �� ��� ��ġ �񱳿� equal�Լ��� ���
		System.out.println(name1.equals(name2));

		String name3 = new String("������");
		String name4 = new String("������");
		// String ��ġ�񱳿��� �� ����� ���� �� ��
		System.out.println(name3 == name4);
		// String �� ��� ��ġ �񱳿� equal�Լ��� ���
		System.out.println(name3.equals(name4));

		// ���� ���� ������
//		if(name3 == "������") {
//			
//		}

		// equals ���
//		if(name3.equals("������")) {
//			
//		}
	}

}
