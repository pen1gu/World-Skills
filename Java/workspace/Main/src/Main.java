import java.util.Scanner;

public class Main implements ExceptionCheck {

	int cnt = 0;
	Car[] cars;
	Car currentCar;

	public Main() {
		try (Scanner strScanner = new Scanner(System.in)) { // scanner closable 처리
			while (true) {
				settingText(); // 기본 문구
				switch (strScanner.nextLine()) {
				case "1": { // 자동차 개수 설정
					System.out.print("자동차 개수를 입력하세요(3보다 크게 입력할 시 3대): ");

					int checkCnt = checkNumberFormatting(strScanner.nextLine());
					cnt = checkCnt > 3 ? 3 : checkCnt;
					cars = new Car[cnt]; // 자동차 개수
					for (int i = 0; i < cnt; i++) {
						System.out.print("자동차 이름을 입력해주세요: ");
						cars[i] = new Car(strScanner.nextLine());
					}
					break;
				}
				case "2": { // 자동차 구매
					if (cnt == 0) {
						System.out.println("자동차를 구매해주세요");
						break;
					}
					for (int i = 0; i < cnt; i++) {
						System.out.println(i + 1 + ": " + cars[i].getCarName());
					}

					int carNum = checkNumberFormatting(strScanner.nextLine());
					currentCar = cars[carNum - 1];
					break;
				}
				case "3": { // 이동할 거리 설정
					while (true) {
						System.out.print("거리를 입력해주세요: ");
						try {
							currentCar.setCarDistance(checkNumberFormatting(strScanner.nextLine()));
						} catch (NullPointerException e) {
							System.out.println("자동차를 선택해주세요.");
						}
						break;
					}
					break;
				}
				case "4": { // 이동 시물레이션
					try {
						while (currentCar.getCarDistance() > 0) {
							currentCar.carMove();
						}
					} catch (NullPointerException e) {
						System.out.println("자동차를 선택해주세요.");
					}
					System.out.println("도착하였습니다.");
					break;
				}
				case "5": { // 이동 결과 출력
					for (int i = 0; i < cars.length; i++) {
						if (cars[i].getCarDistance() == 0) {
							continue;
						}
						// 자동차 이름
						System.out.println("자동차 이름: " + cars[i].getCarName());
						// 총 주행 시간
						System.out.println("총 움직인 시간: " + cars[i].getMoveTime() + "시간");
						// 막힌 횟수
						System.out.println("길이 막힌 횟수: " + cars[i].getTotalBreakCount() + "번");
						// 소모 비용
						System.out.println(String.format("%02d원", cars[i].getTotalPrice()));
						// 현재 장착 타이어
						System.out.println("현재 장착 중인 타이어: " + cars[i].getTireName());
						// 바퀴 교체 횟수
						System.out.println("바퀴를 교체한 횟수: " + cars[i].getTotalChangeCount() + "번\n");
					}
					break;
				}
				case "6": { // 종료
					System.out.println("프로그램이 종료되었습니다.");
					return;
				}
				default: // 예외
					System.out.println("숫자 또는 사용가능한 번호를 입력해주세요.");
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		new Main();
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

	@Override
	public Integer checkNumberFormatting(String checkFor) {
		try {
			return Integer.parseInt(checkFor);
		} catch (NumberFormatException e) {
			System.out.println("숫자를 입력해주세요.");
		}
		return 0;
	}
}
