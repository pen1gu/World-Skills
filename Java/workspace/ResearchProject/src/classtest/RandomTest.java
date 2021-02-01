package classtest;

import java.util.Random;
import java.util.stream.IntStream;

public class RandomTest {
	public static void main(String[] args) {
		/*
		 * Random rnRandom = new Random();
		 * 
		 * IntStream stream = rnRandom.ints().distinct(); for (int i = 0; i <
		 * stream.count(); i++) { System.out.print(stream.iterator() + ""); }
		 */

		int[][] b = new int[10][5];
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < args.length; j++) {
				b[i][j] = i * j;
			}
		}

		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 10; j++) {
				System.out.printf("%2d", b[j][i]);
			}
			System.out.println();
		}
	}
}
