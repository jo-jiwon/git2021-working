package exercise;

import java.util.Scanner;

public class Exercise07 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		// Scanner ���� ���콺 Ŀ���� �ø��� ctrl + 1 -> quick fix
		// java.util.Scanner
		boolean run = true;

		int balance = 0; // �ܰ�
		int deposit = 0; // ����
		int withdrow = 0; // ���

		Scanner scanner = new Scanner(System.in);

		while (run) {
			System.out.println("------------------------------");
			System.out.println("1.���� | 2.��� | 3.�ܰ� | 4.����");
			System.out.println("------------------------------");
			System.out.println("����>");

			// �ۼ� ��ġ
			// �Է°��� ����
			// �Է°��� ���� ����, ��� �Ǵ� �ܰ� ��� ���� ����
			int num = scanner.nextInt();

			if (num == 1) {
				System.out.println("���ݾ�>");
				deposit = scanner.nextInt();
				balance += deposit;
				System.out.println(balance);
			} else if (num == 2) {
				System.out.println("��ݾ�>");
				withdrow = scanner.nextInt();
				balance -= withdrow;
				System.out.println(balance);
			} else if (num == 3) {
				System.out.println("�ܰ�>");

				System.out.println(balance);
			} else if (num == 4) {
				// �Է°��� 4(����)�� ���� run false�� ����
				run = false;

			}
		}

//		// �����
//		switch (scanner.nextByte()) {
//		// �Է°��� ���� ����, ��� �Ǵ� �ܰ� ��� ���� ����
//
//		// �����϶�
//		case 1:
//			System.out.print("���ݾ�>");
//			// �Է¹��� ���� �ܰ� �߰�
//			balance += scanner.nextInt();
//			break;
//
//		// ����϶�
//		case 2:
//			System.out.print("��ݾ�>");
//			// �Է¹��� ���� �ܰ� ����
//			balance -= scanner.nextInt();
//			break;
//
//		// �ܰ�
//		case 3:
//			System.out.println("�ܰ�>" + balance);
//			// �Է¹��� ���� �ܰ� �߰�
//			break;
//		// �Է°��� 4(����)�� ���� run false�� ����
//		case 4:
//			run = false;
//			break;
//		}
		System.out.println("���α׷� ����");

	}

}
