package exercise;

import java.util.Scanner;

public class Exercise09 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		boolean run = true;
		int studentNum = 0;
		int[] scores = null;

		// system.in(standard input stream, 키보드입력)
		Scanner scanner = new Scanner(System.in);

		while (run) {
			System.out.println("---------------------------------------------");
			System.out.println("1.학생수 | 2. 점수입력 | 3.점수리스트 | 4.분석 | 5.종료");
			System.out.println("---------------------------------------------");
			System.out.println("선택>");

			// 숫자 입력값을 콘솔창에 입력 받음
			int selectNo = scanner.nextInt();

			switch (selectNo) {
			case 1:
				// 학생수를 입력받음
				// 입력한 학생수 만큼 배열크기를 초기화
				System.out.println("학생수>");
				// 학생수 입력받음
				// studentNum = scanner.nextInt();
				// null이었던 값을 입력배열로 만들어줌
				// scores = new int[studentNum];
				scores = new int[scanner.nextInt()];
//				System.out.println(studentNum);
				break;
			case 2:
				// 배열크기만큼 반복해서 점수를 입력 받음
				for (int i = 0; i < scores.length; i++) {
					System.out.print("학생점수입력" + i + " > ");
					// 입출력함수
					scores[i] = scanner.nextInt();
				}
				break;
			case 3:
				// 배열크기만큼 반복해서 점수 목록을 출력
				for (int i = 0; i < scores.length; i++) {
					System.out.println("점수" + i + " : " + scores[i]);
				}
				break;
			case 4:
				// 최고점수와 평균점수 출력
				int sum = 0;
				int max = 0;
				for (int score : scores) {
					sum += score;
					max = score > max ? score : max;

				}
				System.out.println("최고점수 :" + max);
				System.out.println("평균점수 :" + sum * 1.0 / scores.length);
				break;
			case 5:
				// run false
				run = false;
				break;
			}
		}
	}

}
