
public class Car extends CarEssentialFunction {
	final static int CAR_SPEED = 20;

	private String carName; // 자동차 이름 param
	private int carDistance; // 자동차 이동거리
	private int tireCapacity; // 타이어
	private int moveTime; // 이동시간
	private int totalPrice; // 비용
	private int totalChangeCount;// 최종 변경 횟수
	private String tireName;// 타이어 이름
	private int totalBreakCount;// 최종정지 횟수

	public Car(String carName) {
		this.carName = carName;
		moveTime = 0;
		tireCapacity = 100;
		carDistance = 0;
		totalPrice = 0;
		totalChangeCount = 0;
		totalBreakCount = 0;
	}

	@Override
	public void carMove() {
		if ((int) (Math.random() * 5 + 1) == 1) {
			carBlocked();
			return;
		}

		if (tireCapacity <= 0) {
			changeTire((int) (Math.random() * 4 + 1));
			return;
		}

		if (carDistance <= CAR_SPEED) {
			carDistance = 0;
		}

		System.out.println("현재 남은 이동거리: " + carDistance);
		moveTime++;
		carDistance -= CAR_SPEED;
		tireCapacity -= CAR_SPEED;

//			try {
//				Thread.sleep(1000);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}

	}

	public void carBlocked() { // 길이 막혔을 시
		System.out.println("자동차가 너무 많습니다. 도착 시간까지 지연됩니다.");
//		try {
//			Thread.sleep(2000);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		moveTime += 2;
		totalBreakCount++;
	}

	public void changeTire(int num) { // 랜덤값을 받아서 타이어 변경
		System.out.println("바퀴를 바꿉니다.");
		
//		try {
//			Thread.sleep(5000);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		
		try {
		} catch (Exception e) {
			e.printStackTrace();
		}

		int cost, distance;
		switch (num) {
		case 1: {
			cost = 30000;
			distance = 40;
			tireName = "미쉐린 타이어";
			break;
		}
		case 2: {
			cost = 50000;
			distance = 60;
			tireName = "콘티넨탈 타이어";
			break;
		}
		case 3: {
			cost = 80000;
			distance = 80;
			tireName = "금호 타이어";
			break;
		}
		case 4: {
			cost = 120000;
			distance = 100;
			tireName = "브릿지스톤 타이어";
			break;
		}
		default:
			throw new IllegalArgumentException("error");
		}
		System.out.println(tireName + "으로(로) 변경되었습니다.");
		totalPrice += cost;
		tireCapacity = distance;
		totalChangeCount++;
		moveTime += 5;
	}

	public String getCarName() {
		return carName;
	}

	public void setCarName(String carName) {
		this.carName = carName;
	}

	public int getCarDistance() {
		return carDistance;
	}

	public void setCarDistance(int carDistance) {
		this.carDistance = carDistance;
	}

	public int getTireCapacity() {
		return tireCapacity;
	}

	public void setTire(int tireCapacity) {
		this.tireCapacity = tireCapacity;
	}

	public int getMoveTime() {
		return moveTime;
	}

	public void setMoveTime(int moveTime) {
		this.moveTime = moveTime;
	}

	public int getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
	}

	public int getTotalChangeCount() {
		return totalChangeCount;
	}

	public void setTotalChangeCount(int totalChangeCount) {
		this.totalChangeCount = totalChangeCount;
	}

	public String getTireName() {
		return tireName;
	}

	public void setTireName(String tireName) {
		this.tireName = tireName;
	}

	public int getTotalBreakCount() {
		return totalBreakCount;
	}

	public void setTotalBreakCount(int totalBreakCount) {
		this.totalBreakCount = totalBreakCount;
	}

}
