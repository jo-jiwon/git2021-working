// �������̽� �԰ݿ� �°� ��� Ŭ������ ����
// ���Ŭ���� ���� �۵��� �ƴϰ� ���� �ȳ��� ��¥���� ó���ǰ� ��

// implements: ����
// ����Ŭ������ implements Calculator
public class CalculatorMock implements Calculator {

	@Override
	public int plus(int a, int b) {
		return 10;
	}

	@Override
	public int minus(int a, int b) {
		return 5;
	}

	@Override
	public double areaCircle(int r) {
		return 5 * 5 * 3.14;
	}

}
