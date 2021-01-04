package test;

public class ManiTest {
	public static void main(String[] args) {
		String sign = "1234 5978";
		String[] operatorArr = new String[] { "!", "@", "#", "$", "%", "^" };
		StringBuilder result = new StringBuilder();
		char[] ch = new char[3];

		for (int i = 0; i < sign.length(); i++) {
			String addText = " ";
			if (sign.charAt(i) != ' ') {
				addText = operatorArr[(int) (Math.random() * operatorArr.length)];
			}
			result.append(addText);

		}
		System.out.println(result.toString());

//
//		String pattern = "[A-za-z]";
//		Pattern.matches(pattern, sign);
//
//		sign.replace("[A-za-z]", "!!");
//		System.out.println(sign);
	}
}
