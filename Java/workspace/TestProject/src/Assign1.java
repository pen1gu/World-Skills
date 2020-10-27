public class Assign1 {
	public static void main(String[] args) {
		int[][] arr = new int[10][1];
		for (int i = 0; i < 10; i++) {
			arr[i][0] = 0;
		}

		for (int i = 0; i < 100; i++) {
			arr[(int) (Math.random() * 10)][0]++;
		}

		for (int i = 1; i <= 10; i++) {
			System.out.print(i + ": ");
			for (int j = 0; j < arr[i-1][0]; j++) {
				System.out.print("=");
			}
			System.out.println();
		}
	}
}
