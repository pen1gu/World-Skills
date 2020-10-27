import java.util.StringTokenizer;

public class StringTokenizerEx {
	public static void main(String[] args) {
		String query = "name=kitae&addr=seoul&age=21";
		StringTokenizer stringTokenizer = new StringTokenizer(query,"&");
		
		Integer n = stringTokenizer.countTokens();
		System.out.println("토큰 개수 = "+n);
		
		while(stringTokenizer.hasMoreTokens()) {
			String token = stringTokenizer.nextToken();
			System.out.println(token);
		}
	}
}
