package default1;

import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		String input;

		input = scanner.nextLine();
		scanner.close();

		int input_length = input.length();

		for (int i = 0; i < input_length; i++) {
			String input_sub = input.substring(i, input_length);
			for (int j = 0; j < input_sub.length(); j++)
				System.out.println(input_sub.substring(0, j + 1));
		}
	}
}
