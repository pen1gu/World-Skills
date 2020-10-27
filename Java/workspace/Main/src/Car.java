
public class Car extends Thread {
	private String carName; // 자동차 이름
	private int carSpeed; // 자동차 속력
	private int carDistance; // 자동차 이동거리
	private int tire; // 타이어
	private int moveTime; // 이동시간
	private int price; // 비용

	public Car(String carName, int carSpeed, int carDistance, int tire, int moveTime, int price) {
		this.carName = carName;
		this.carSpeed = carSpeed;
		this.carDistance = carDistance;
		this.tire = tire;
		this.moveTime = moveTime;
		this.price = price;
	}

	public void carMove() { // 기본적으로 자동차가 움직이는 동작
		if ((int) (Math.random() * 5 + 1) == 1) {
			carBlocked();
		}

		if (tire <= 0) {
			changeTire((int) (Math.random() * 4 + 1));
		}

		moveTime += 1;
		carDistance += 20;
		tire -= 20;

//		try {
//			Thread.sleep(1000);
//			System.out.println("실행중입니다");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}

	}

	public void carBlocked() { // 길이 막혔을 시
		System.out.println("길이 막혔습니다.");
//		try {
//			Thread.sleep(2000);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		moveTime += 2;
	}

	public void changeTire(int num) { // 랜덤값을 받아서 타이어 변경
		System.out.println("바퀴를 바꿉니다.");
		try {
		} catch (Exception e) {
			e.printStackTrace();
		}

		int cost, distance;
		switch (num) {
		case 1: {
			cost = 30000;
			distance = 40;
			break;
		}
		case 2: {
			cost = 50000;
			distance = 60;
			break;
		}
		case 3: {
			cost = 80000;
			distance = 80;
			break;
		}
		case 4: {
			cost = 120000;
			distance = 100;
			break;
		}
		default:
			throw new IllegalArgumentException("error");
		}

		price += cost;
		tire = distance;
	}

	public String getCarName() {
		return carName;
	}

	public void setCarName(String carName) {
		this.carName = carName;
	}

	public int getCarSpeed() {
		return carSpeed;
	}

	public void setCarSpeed(int carSpeed) {
		this.carSpeed = carSpeed;
	}

	public int getCarDistance() {
		return carDistance;
	}

	public void setCarDistance(int carDistance) {
		this.carDistance = carDistance;
	}

	public int getTire() {
		return tire;
	}

	public void setTire(int tire) {
		this.tire = tire;
	}

	public int getMoveTime() {
		return moveTime;
	}

	public void setMoveTime(int moveTime) {
		this.moveTime = moveTime;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}
}
