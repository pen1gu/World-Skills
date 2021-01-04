import java.util.Scanner;

public class QuickCalculate {
	public static void main(String[] args) throws Exception {
		Scanner scanner = new Scanner(System.in);
		int n1 = scanner.nextInt();
		int n2 = scanner.nextInt();

		double startTime = System.currentTimeMillis();
		int divN1 = n1 % 10;
		int divN2 = n2 % 10;

		int resultN1 = n1 - divN1;
		int resultN2 = n2 - divN2;

		System.out.println((resultN1 * resultN2) + (resultN1 * divN2) + (resultN2 * divN1) + (divN1 * divN2));
		System.out.println(System.currentTimeMillis() - startTime);

		scanner.close();
	}
}
