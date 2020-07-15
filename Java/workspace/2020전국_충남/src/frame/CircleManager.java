package frame;

import java.util.Scanner;

public class CircleManager {
	public static void main(String args[]) {
		Scanner scan = new Scanner(System.in);

		System.out.print("반지름 값 >>");

		int radius = scan.nextInt();

		Circle c = new Circle();
		c.setRadius(radius);

		System.out.println("반지름 " + c.getRadius() + "인 원의 넓이는 " + c.Area() + "이다.");
	}
}

class Circle {
	private int radius;

	public Circle() {
	}

	public double Area() { // 원의 넓이 return (원의 넓이 = 반지름*반지름*3.14)
		return radius * radius * 3.14;
	}
	
	public void setRadius(int radius) {
		this.radius = radius;
	}
	
	public int getRadius() {
		return radius;
	}
}