import java.util.Scanner;

public class Main extends Thread {

	int distance;
	int cnt = 0;
	Car[] cars;
	Car currentCar;

	public void run() {
		Scanner numScanner = new Scanner(System.in);
		Scanner strScanner = new Scanner(System.in);

		while (true) {
			settingText();
			switch (numScanner.nextInt()) {
			case 1: {
				System.out.print("자동차 개수를 입력하세요: ");
				cnt = numScanner.nextInt();
				cars = new Car[cnt];
				for (int i = 0; i < cnt; i++) {
					cars[i] = new Car(strScanner.nextLine(), 40, 0, 100, 0, 0);
				}
				break;
			}
			case 2: {
				if (cnt == 0) {
					System.out.println("자동차를 구매해주세요");
					break;
				}
				for (int i = 0; i < cnt; i++) {
					System.out.println(i + 1 + ": " + cars[i].getCarName());
				}

				int carNum = numScanner.nextInt();
				currentCar = cars[carNum - 1];
				break;
			}
			case 3: {
				while (true) {
					System.out.print("거리를 입력해주세요: ");
					try {
						distance = Integer.parseInt(strScanner.nextLine());
					} catch (NumberFormatException e) {
						System.out.println("숫자를 입력해주세요.");
						continue;
					}
					break;
				}
				break;
			}
			case 4: {
				while (currentCar.getCarDistance() < distance) {
					currentCar.carMove();
					System.out.println("도착지점까지 : " + (distance - currentCar.getCarDistance()));
				}
				break;
			}
			case 5: {

				break;
			}
			case 6: {
				System.out.println("끝 ㅅㄱ");
				return;
			}
			default:
				throw new IllegalArgumentException("error");
			}
		}

	}

	public static void main(String[] args) {
		Thread t = new Main();
		t.start();
	}

	public void settingText() {
		System.out.println("--------------");
		System.out.println("1. 자동차 구매하기");
		System.out.println("2. 자동차 선택하기");
		System.out.println("3. 도착 지점 설정하기");
		System.out.println("4. 주행 시작");
		System.out.println("5. 결과보기");
		System.out.println("6. 프로그램 종료");
		System.out.println("--------------");
	}
}
