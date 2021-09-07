package condition.ifexpr;

public class ifExample {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String empty = "";
		
		// Java 허용 안됨
//		if(empty) {
//			
//		}
		
		int a = 10;
		
		if(a > 9) {
			System.out.println(a);
		}
		// boolean 값의 연산만 if문의 조건식으로 올수 있음
		if(true) {
			System.out.println(a);
		}
	
		// 안됨
//		if(!a) {
//			
//		}
	}

}
