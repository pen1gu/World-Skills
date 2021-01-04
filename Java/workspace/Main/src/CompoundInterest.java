import java.util.Scanner;

public class CompoundInterest {
	public static void main(String[] args) {
		Scanner doubleScanner = new Scanner(System.in);

		System.out.print("시작 금액을 입력해주세요: ");
		double price = doubleScanner.nextDouble();

		System.out.print("기간을 입력해주세요(년 단위): ");
		double year_input = doubleScanner.nextDouble();

		System.out.print("이자율을 입력해주세요: ");
		double interest_rate = doubleScanner.nextDouble();
		
		for (int i = 0; i < year_input; i++) {
			price += price * (interest_rate / 100);
		}

		System.out.print(String.format("복리 계산 결과는: %,f원입니다.", price));
		
		doubleScanner.close();
	}
}
