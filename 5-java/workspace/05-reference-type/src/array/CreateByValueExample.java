package array;

public class CreateByValueExample {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// 정수형 배열 선언 및 초기화
		// 배열타입[] 배열변수명 = {.. 초기화 목록}
		int[] scores = { 83, 90, 87 };

		// !!Java의 배열은 처음 만들어진 크기가 고정됨
		System.out.println(scores[0]);
		System.out.println(scores[1]);
		System.out.println(scores[2]);

		// 배열 요소의 추가, 삭제 불가능함
		System.out.println("-------갑변경-------");
		scores[0] = 100;
		System.out.println(scores[0]);
		System.out.println(scores[1]);
		System.out.println(scores[2]);
		// 기본형 데이터 타잉은 null처리가 불가능
		scores[0] = 0;
	}

}
