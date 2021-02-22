package frame;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DuplicateCheck {
	public static void main(String[] args) {
		Integer[] arr = new Integer[] { 33, 33, 32423, 4, 3, 2, 3, 9, 32423, 123, 22, 2 };

		Arrays.sort(arr);

		StringBuilder builder = new StringBuilder();

		List<Integer> duplicateList = new ArrayList<Integer>();

		for (int i = 0; i < arr.length; i++) {
			for (int j = i + 1; j < arr.length; j++) {
				if (arr[i].equals(arr[j])) {
					if (duplicateList.contains(arr[j]))
						break;

					builder.append(", ");
					builder.append(arr[j]);
					duplicateList.add(arr[j]);
					break;
				}
			}
		}

		System.out.println(builder.toString().substring(2, builder.length()));
	}
}
