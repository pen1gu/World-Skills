
public class StringEx2 {
	public static void main(String[] args) {
		String java = "java";
		String cpp = "c++";
		String cc = new String("c++");

		int res = java.compareTo(cpp);
		if (res == 0) {
			System.out.println("the same");
		} else if (res < 0) {
			System.out.println(java = "<" + cpp);
		} else {
			System.out.println(java + ">" + cpp);
		}
		
		if (java.equals("c++")) {
			System.out.println("the same");
		}else {
			System.out.println("the different");
		}
		
		if (cpp == cc) {
			System.out.println("the same");
		}else {
			System.out.println("the different");
		}

		if (cpp.equals(cc)) {
			System.out.println("the same");
		}else {
			System.out.println("the different");
		}
	}
}
