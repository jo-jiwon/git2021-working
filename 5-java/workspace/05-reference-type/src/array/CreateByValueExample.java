package array;

public class CreateByValueExample {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// ������ �迭 ���� �� �ʱ�ȭ
		// �迭Ÿ��[] �迭������ = {.. �ʱ�ȭ ���}
		int[] scores = { 83, 90, 87 };

		// !!Java�� �迭�� ó�� ������� ũ�Ⱑ ������
		System.out.println(scores[0]);
		System.out.println(scores[1]);
		System.out.println(scores[2]);

		// �迭 ����� �߰�, ���� �Ұ�����
		System.out.println("-------������-------");
		scores[0] = 100;
		System.out.println(scores[0]);
		System.out.println(scores[1]);
		System.out.println(scores[2]);
		// �⺻�� ������ Ÿ���� nulló���� �Ұ���
		scores[0] = 0;
	}

}
