package exercise;

public class Exercise04 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int pencils = 534;
		int student = 30;
		
		// 한학생이 가질수 있는 연필 갯수
		int pencilsPerStudent = pencils/student;
		System.out.println(pencilsPerStudent);
		
		// 나머지 연필 갯수
		int pencilsLeft = pencils%student;
		System.out.println(pencilsLeft);
	}

}
