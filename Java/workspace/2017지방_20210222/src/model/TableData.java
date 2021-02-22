package model;

public class TableData {
	private String carPrimary;
	private String carNum;
	private String year;
	private String month;
	private String day;
	private String date;

	public String getCarPrimary() {
		return carPrimary;
	}

	public void setCarPrimary(String carPrimary) {
		this.carPrimary = carPrimary;
	}

	public String getCarNum() {
		return carNum;
	}

	public void setCarNum(String carNum) {
		this.carNum = carNum;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getDate() {
		return date;
	}

	public void formatDate() {
		try {
			date = String.format("%d-%02d-%02d", Integer.parseInt(year.toString().substring(0, 4)),
					Integer.parseInt(subStringDate(month)), Integer.parseInt(subStringDate(day)));
		} catch (Exception e) {
			return;
		}
	}

	public String subStringDate(String value) throws Exception {
		return value.substring(0, 2);
	}
}
