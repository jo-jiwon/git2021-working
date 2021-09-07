package exercise;

import java.util.Scanner;

public class Exercise07 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		// Scanner 위에 마우스 커서를 올리고 ctrl + 1 -> quick fix
		// java.util.Scanner
		boolean run = true;

		int balance = 0; // 잔고
		int deposit = 0; // 예금
		int withdrow = 0; // 출금

		Scanner scanner = new Scanner(System.in);

		while (run) {
			System.out.println("------------------------------");
			System.out.println("1.예금 | 2.출금 | 3.잔고 | 4.종료");
			System.out.println("------------------------------");
			System.out.println("선택>");

			// 작성 위치
			// 입력값을 받음
			// 입력값에 따라서 예금, 출금 또는 잔고 출력 로직 수행
			int num = scanner.nextInt();

			if (num == 1) {
				System.out.println("예금액>");
				deposit = scanner.nextInt();
				balance += deposit;
				System.out.println(balance);
			} else if (num == 2) {
				System.out.println("출금액>");
				withdrow = scanner.nextInt();
				balance -= withdrow;
				System.out.println(balance);
			} else if (num == 3) {
				System.out.println("잔고>");

				System.out.println(balance);
			} else if (num == 4) {
				// 입력값이 4(종료)일 때는 run false로 나감
				run = false;

			}
		}

//		// 강사님
//		switch (scanner.nextByte()) {
//		// 입력값에 따라서 예금, 출금 또는 잔고 출력 로직 수행
//
//		// 예금일때
//		case 1:
//			System.out.print("예금액>");
//			// 입력받은 값을 잔고에 추가
//			balance += scanner.nextInt();
//			break;
//
//		// 출금일때
//		case 2:
//			System.out.print("출금액>");
//			// 입력받은 값을 잔고에 빼기
//			balance -= scanner.nextInt();
//			break;
//
//		// 잔고
//		case 3:
//			System.out.println("잔고>" + balance);
//			// 입력받은 값을 잔고에 추가
//			break;
//		// 입력값이 4(종료)일 때는 run false로 나감
//		case 4:
//			run = false;
//			break;
//		}
		System.out.println("프로그램 종료");

	}

}
