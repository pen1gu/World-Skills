package algorithmtest;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeSet;

import javax.swing.JOptionPane;

public class Distinct {
	public static void main(String[] args) {

		String[] splitNumArr = { "3", "9", "3", "4", "8", "3", "9", "9" };
		TreeSet<String> set = new TreeSet<String>();
		for (int i = 0; i < splitNumArr.length; i++)
			for (int j = i + 1; j < splitNumArr.length; j++)
				if (splitNumArr[i] == splitNumArr[j]) {
					set.add(splitNumArr[i]);
					continue;
				}

		StringBuilder builder = new StringBuilder();
		Iterator<String> iter = set.iterator();
		builder.append(iter.next());
		while (iter.hasNext()) {
			builder.append(", " + iter.next());
		}

		JOptionPane.showMessageDialog(null, "중복된 좌석은 " + builder + "입니다.");

		/*
		 * ArrayList<String> list = new ArrayList<String>(Arrays.asList(splitNumArr));
		 * 
		 * ArrayList<String> resultList = new ArrayList<String>();
		 * 
		 * for (int i = 0; i < list.size(); i++) { String listValue = list.get(i); if
		 * (list.contains(listValue)) { resultList.add(listValue + ", ");
		 * list.remove(listValue); } }
		 */
//		for (int i = 0; i < resultList.size(); i++) {
//			System.out.print(resultList.get(i));
//		}
//		System.out.print(list.toString());

	}

	@Override
	public String toString() {
		return super.toString();
	}
}
