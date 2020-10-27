
public class Assign2 {
	public static void main(String[] args) {
		int[] array = new int[10];

		for (int i = 0; i < 100; i++) {
			array[(int) (Math.random() * 10)]++;
		}

		for (int i = 1; i <= 10; i++) {
			System.out.print(i + ": ");
			for (int j = 0; j < array[i - 1]; j++) {
				System.out.print("=");
			}
			System.out.println();
		}
	}
}
