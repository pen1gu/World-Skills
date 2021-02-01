package classtest;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Date;

public class LocalDateTest {
	public static void main(String[] args) {
//		System.out.println(LocalDate.now().getDayOfMonth());
		
//		System.out.println(LocalDate.now().getDayOfWeek().getValue());
		System.out.println(LocalDate.now().getMonthValue());
		YearMonth ym = YearMonth.from(LocalDate.now()); 
		System.out.println(ym.lengthOfMonth());
		System.out.println();
//		System.out.println(LocalDate.now().get);
	}
}
