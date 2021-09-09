package static_;

public class CalculatorExample {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		// 인스턴스(객체) 생성안하고 바로 사용가능
		// 주로 자주쓰는 값이나 기능들을 static으로 정의하여 사용가능
		System.out.println(Calculator.pi);
		System.out.println(Calculator.plus(10, 5));

		System.out.println(Calculator.getAreaCircle(5));

		// 객체를 생성해서 쓴다는게 큰 의미가 없음
		// Calculator calc = new Calculator();

	}

}
