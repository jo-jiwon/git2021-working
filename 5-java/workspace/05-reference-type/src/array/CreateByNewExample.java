package array;

public class CreateByNewExample {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		// new Ű���带 �̿��Ͽ� ����
		// ũ�Ⱑ 3�� int�迭 ����
		int[] arrInt = new int[3];
		// 0�ʱ�ȭ �Ǿ�����
		System.out.println(arrInt[0]);
		System.out.println(arrInt[1]);
		System.out.println(arrInt[2]);

		for (int i = 0; i < 3; i++) {
			System.out.println(arrInt[i]);
		}

		// ũ�Ⱑ 3�� string �迭 ����
		String[] arrStr = new String[3];
		// null �ʱ�ȭ �Ǿ�����
		System.out.println(arrStr[0]);
		System.out.println(arrStr[1]);
		System.out.println(arrStr[2]);

		// ���� for-��(enhanced(advanced)for loop)
		// for(��Һ���Ÿ�� ������� : �迭������){}
		for (String str : arrStr) {
			str = "test";
			System.out.println(str);
		}

		// �迭ũ��
		System.out.println(arrStr.length);

		String[] languages = { "java", "typescript", "html", "css" };

	}

}
