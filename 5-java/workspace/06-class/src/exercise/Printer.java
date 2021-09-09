package exercise;

import java.util.Date;

public class Printer {

	public void println(int i) {
		// TODO Auto-generated method stub
		System.out.println(i);

	}

	public void println(boolean b) {
		// TODO Auto-generated method stub
		System.out.println(b);
	}

	public void println(double d) {
		// TODO Auto-generated method stub
		System.out.println(d);
	}

	@SuppressWarnings("deprecation")
	public void println(String string) {
		// TODO Auto-generated method stub
		System.out.println(new Date().toLocaleString() + ": " + string);
	}

	public void println(String string, int index) {
		// TODO Auto-generated method stub
		System.out.println(index + " " + string);
	}

	// postfix: 뒤쪽에 붙는 글자
	// prefix: 앞쪽에 붙는 글자
	public void println(String string, int index, String postfix) {
		// TODO Auto-generated method stub
		System.out.println(index + " " + string + " " + postfix);
	}
}
